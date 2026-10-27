#!/data/data/com.termux/files/usr/bin/bash
# BYBLACK Office V5.3 — Termux ARM64 local-dependency build
# Requires local dependencies from the original development environment.
set -euo pipefail
ROOT="$(cd "$(dirname "${BASH_SOURCE[0]}")/.." && pwd)"
BASE="$HOME/ByblackOffice"
LIB="$BASE/office-engines"
BT="$HOME/android-sdk/build-tools/34.0.0"
ANDROID_JAR="$HOME/android-sdk/platforms/android-34/android.jar"
PDF="$BASE/pdf-engine-test/pdfbox-classes.jar"
CODEC="$BASE/codec-version-test/commons-codec-1.17.0.jar"
XMLBEANS="$BASE/V5.3-Pro/build-fix7/xmlbeans-android.jar"
OUT="$ROOT/build-local"
AAPT="$(command -v aapt2 || true)"
for f in "$ANDROID_JAR" "$BT/lib/d8.jar" "$BT/apksigner" "$PDF" "$CODEC" "$XMLBEANS" "$LIB/xmlbeans-5.3.0.jar" "$LIB/poi-ooxml-lite-5.4.1.jar" "$ROOT/app/AndroidManifest.xml"; do
  [ -f "$f" ] || { echo "Eksik bagimlilik: $f"; exit 1; }
done
[ -n "$AAPT" ] || { echo "Termux aapt2 kur: pkg install aapt2"; exit 1; }
case "$(file -b "$AAPT" 2>/dev/null || true)" in
  *x86-64*|*x86_64*) echo "ARM64 aapt2 gerekli"; exit 1;;
esac
command -v javac >/dev/null || { echo "javac gerekli"; exit 1; }
command -v python >/dev/null || { echo "python gerekli"; exit 1; }
mkdir -p "$OUT/flat" "$OUT/classes" "$OUT/dex"
# Manifest references the icon, which must exist in resources.
[ -d "$ROOT/app/res" ] || { echo "app/res eksik; ikon kaynaklarini GitHub'a eklemek gerekiyor."; exit 1; }
"$AAPT" compile --dir "$ROOT/app/res" -o "$OUT/flat"
find "$OUT/flat" -name '*.flat' > "$OUT/flat-list.txt"
"$AAPT" link -I "$ANDROID_JAR" --manifest "$ROOT/app/AndroidManifest.xml" --auto-add-overlay -o "$OUT/resources.apk" @"$OUT/flat-list.txt"
CP="$ANDROID_JAR:$PDF:$CODEC:$XMLBEANS"
set -- "$PDF" "$CODEC" "$XMLBEANS"
for j in "$LIB"/*.jar; do
  case "$j" in */xmlbeans-5.3.0.jar|*/commons-codec-*) ;; *) CP="$CP:$j"; set -- "$@" "$j";; esac
done
find "$ROOT/app/src" -name '*.java' > "$OUT/sources.txt"
javac -source 8 -target 8 -cp "$CP" -d "$OUT/classes" @"$OUT/sources.txt"
find "$OUT/classes" -name '*.class' > "$OUT/classes.txt"
java -Xmx2g -cp "$BT/lib/d8.jar" com.android.tools.r8.D8 --min-api 26 --lib "$ANDROID_JAR" --output "$OUT/dex" "$@" @"$OUT/classes.txt"
bash "$ROOT/scripts/collect-licenses.sh" "$OUT/third-party-licenses"
# Include only separately downloaded legal documents after local inspection.
if [ -d "$ROOT/third-party-licenses" ]; then
  find "$ROOT/third-party-licenses" -maxdepth 1 -type f \
    \( -iname "*LICENSE*" -o -iname "*NOTICE*" -o -iname "*COPYING*" \) \
    -exec cp {} "$OUT/third-party-licenses/" \;
fi
python - "$OUT" "$LIB" "$ROOT/app/assets" "$OUT/third-party-licenses" <<'PY'
import sys,zipfile,glob,os
out,lib,assets,legal=sys.argv[1:]
seen=set()
with zipfile.ZipFile(out+'/resources.apk') as src,zipfile.ZipFile(out+'/unsigned.apk','w') as dst:
 for item in src.infolist():
  dst.writestr(item,src.read(item.filename));seen.add(item.filename)
 for dex in sorted(glob.glob(out+'/dex/classes*.dex')):
  name=os.path.basename(dex);dst.write(dex,name);seen.add(name)
 for jar in [lib+'/xmlbeans-5.3.0.jar',lib+'/poi-ooxml-lite-5.4.1.jar']:
  with zipfile.ZipFile(jar) as z:
   for name in z.namelist():
    if not name.endswith(('.xsb','.properties')):continue
    target='assets/xmlbeans/'+name
    if target not in seen:dst.writestr(target,z.read(name));seen.add(target)
 for path in glob.glob(assets+'/**/*',recursive=True):
  if not os.path.isfile(path):continue
  name='assets/'+os.path.relpath(path,assets).replace(os.sep,'/')
  if name not in seen:dst.write(path,name);seen.add(name)
 for path in glob.glob(legal+'/**/*',recursive=True):
  if not os.path.isfile(path):continue
  name='assets/third-party-licenses/'+os.path.relpath(path,legal).replace(os.sep,'/')
  if name not in seen:dst.write(path,name);seen.add(name)
PY
KEY="$HOME/.android/debug.keystore"
[ -f "$KEY" ] || { echo "Debug keystore eksik: $KEY"; exit 1; }
"$BT/apksigner" sign --ks "$KEY" --ks-key-alias androiddebugkey --ks-pass pass:android --key-pass pass:android --out "$OUT/BYBLACK-Office-V5.3-local.apk" "$OUT/unsigned.apk"
"$BT/apksigner" verify "$OUT/BYBLACK-Office-V5.3-local.apk"
echo "APK: $OUT/BYBLACK-Office-V5.3-local.apk"
