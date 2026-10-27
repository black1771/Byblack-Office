#!/data/data/com.termux/files/usr/bin/bash
set -euo pipefail
REPO="black1771/Byblack-Office"
TAG="v5.3-beta"
OUT="/storage/emulated/0/Download"
mkdir -p "$OUT"
if ! command -v gh >/dev/null 2>&1; then
  echo "GitHub CLI gerekiyor: pkg install gh"
  exit 1
fi
gh release download "$TAG" --repo "$REPO" --pattern 'BYBLACK-Office-V5.3.apk' --dir "$OUT" --clobber
echo "APK indirildi: $OUT/BYBLACK-Office-V5.3.apk"
echo "Dosyayi Android Dosyalar/Indirilenler'den acip kurulumu onaylayin."
