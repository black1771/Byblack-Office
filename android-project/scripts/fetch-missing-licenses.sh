#!/data/data/com.termux/files/usr/bin/bash
set -euo pipefail
ROOT="$(cd "$(dirname "$0")/.." && pwd)"
DEST="$ROOT/third-party-licenses"
mkdir -p "$DEST"
python - "$DEST" <<'PY'
import urllib.request,os,sys,zipfile,io,hashlib
dest=sys.argv[1]
base="https://repo.maven.apache.org/maven2"
artifacts=[
 ("SparseBitSet-1.3","com/zaxxer/SparseBitSet/1.3/SparseBitSet-1.3.jar"),
 ("curvesapi-1.08","com/github/virtuald/curvesapi/1.08/curvesapi-1.08.jar"),
]
for name,path in artifacts:
 url=f"{base}/{path}"
 req=urllib.request.Request(url,headers={"User-Agent":"BYBLACK-Office-license-audit/1"})
 with urllib.request.urlopen(req,timeout=30) as response: blob=response.read()
 with zipfile.ZipFile(io.BytesIO(blob)) as z:
  names=[n for n in z.namelist() if any(word in n.upper() for word in ("LICENSE","NOTICE","COPYING")) and not n.endswith("/")]
  print(name,"SHA256",hashlib.sha256(blob).hexdigest(),"embedded notices",names)
  for n in names:
   if len(z.read(n))>1000000:continue
   safe=n.replace("/","__")
   with open(os.path.join(dest,name+"__"+safe),"wb") as f:f.write(z.read(n))
PY
echo "Official PDFBox v2.0.27.0 legal files:"
for name in LICENSE.txt NOTICE.txt; do
  curl --fail --location --retry 2 --silent --show-error \
   "https://raw.githubusercontent.com/TomRoush/PdfBox-Android/v2.0.27.0/$name" \
   -o "$DEST/PdfBox-Android-2.0.27.0__$name"
  test -s "$DEST/PdfBox-Android-2.0.27.0__$name"
done

echo "Fetching upstream license texts:"
curl --fail --location --retry 2 --silent --show-error \
 "https://raw.githubusercontent.com/brettwooldridge/SparseBitSet/master/LICENSE" \
 -o "$DEST/SparseBitSet-1.3__LICENSE"
curl --fail --location --retry 2 --silent --show-error \
 "https://raw.githubusercontent.com/virtuald/curvesapi/master/license.txt" \
 -o "$DEST/curvesapi-1.08__LICENSE.txt"
test -s "$DEST/SparseBitSet-1.3__LICENSE"
test -s "$DEST/curvesapi-1.08__LICENSE.txt"
echo "Note: CurvesAPI includes Apache Harmony-derived Crossing.java; inspect its copyright header separately."

echo "Downloaded legal materials into $DEST"
echo "Review the full licenses, copyright notices and transitive dependencies before publishing."
