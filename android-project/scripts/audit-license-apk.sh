#!/data/data/com.termux/files/usr/bin/bash
set -euo pipefail
ROOT="$(cd "$(dirname "$0")/.." && pwd)"
APK="$ROOT/build-local/BYBLACK-Office-V5.3-local.apk"
python - "$ROOT" "$APK" <<'PY'
import os,sys,zipfile,re
root,apk=sys.argv[1:]
if not os.path.isfile(apk):raise SystemExit("APK bulunamadi. Once build-termux-local.sh calistirin.")
with zipfile.ZipFile(apk) as z:
 files=set(z.namelist())
 required=[
 "PdfBox-Android-2.0.27.0__LICENSE.txt",
 "PdfBox-Android-2.0.27.0__NOTICE.txt",
 "SparseBitSet-1.3__LICENSE",
 "curvesapi-1.08__LICENSE.txt",
 ]
 missing=[n for n in required if "assets/third-party-licenses/"+n not in files]
 print("===== BYBLACK OFFICE LICENSE GATE =====")
 for n in required:print(("FAIL" if n in missing else "PASS"),n)
 notice=open(os.path.join(root,"THIRD_PARTY_NOTICES.md"),encoding="utf-8").read()
 for term in ("Harmony","Crossing","Apache"):
  print(("PASS" if term.lower() in notice.lower() else "WARN"),"documentation mentions",term)
 if missing:raise SystemExit("FAIL: mandatory legal files missing")
 print("PASS: required four documents are bundled.")
 print("REVIEW REQUIRED: full dependency provenance, embedded copyright notices, and UI accessibility are not verified by this gate.")
PY
