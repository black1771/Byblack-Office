#!/data/data/com.termux/files/usr/bin/bash
# Collect embedded legal notices from local JAR files; no downloads or guesses.
set -euo pipefail
BASE="$HOME/ByblackOffice"
DEST="${1:-$PWD/build-local/third-party-licenses}"
mkdir -p "$DEST"
python - "$BASE" "$DEST" <<'PY'
import sys,glob,os,zipfile,re,hashlib
base,dest=sys.argv[1:]
jars=sorted(set(glob.glob(base+'/office-engines/*.jar')+
 [base+'/pdf-engine-test/pdfbox-classes.jar',
  base+'/codec-version-test/commons-codec-1.17.0.jar',
  base+'/V5.3-Pro/build-fix7/xmlbeans-android.jar']))
rows=['# BYBLACK Office local JAR inventory','',
      'Generated from JARs present on the development device. Not a full compliance clearance.','']
missing=[]
for jar in jars:
 if not os.path.isfile(jar):continue
 stem=os.path.basename(jar)
 sha=hashlib.sha256(open(jar,'rb').read()).hexdigest()
 rows.append('* '+stem+' — SHA256 '+sha)
 try:
  with zipfile.ZipFile(jar) as z:
   candidates=[n for n in z.namelist() if re.search(r'(^|/)(LICENSE|NOTICE)([._-][^/]*)?$',n,re.I) and not n.endswith('/')]
   if not candidates:missing.append(stem);continue
   for n in candidates:
    raw=z.read(n)
    if len(raw)>2_000_000:continue
    safe=re.sub(r'[^A-Za-z0-9_.-]','_',stem+'__'+n)
    with open(os.path.join(dest,safe),'wb') as out:out.write(raw)
 except (zipfile.BadZipFile,OSError) as e:missing.append(stem+' ('+str(e)+')')
rows+=['','## Missing embedded LICENSE/NOTICE metadata']
rows+=['* '+n for n in missing] if missing else ['None detected (manual review still required).']
with open(os.path.join(dest,'INVENTORY.md'),'w') as f:f.write('\n'.join(rows)+'\n')
print('License inventory:',dest)
print('JARs without embedded notices:',len(missing))
PY
