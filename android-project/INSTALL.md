# BYBLACK Office — Kurulum ve Derleme

## Hazır APK kurulumu (önerilen)

1. [V5.3 Beta sürümünü aç](https://github.com/black1771/Byblack-Office/releases/tag/v5.3-beta).
2. **Assets** altından `BYBLACK-Office-V5.3.apk` dosyasını indir.
3. Android'de İndirilenler klasöründen APK'ya dokun ve kurulumu onayla.

> Android, tarayıcı/dosya yöneticisi için bilinmeyen uygulama yükleme izni isteyebilir. Yalnızca güvendiğiniz APK'ları yükleyin.

### Termux ile indirme

```bash
pkg install gh
gh release download v5.3-beta -R black1771/Byblack-Office --pattern 'BYBLACK-Office-V5.3.apk' --dir /storage/emulated/0/Download
```

Alternatif olarak bu depodaki `scripts/download-apk.sh` betiği kullanılabilir.

## Kaynaktan derleme

Bu depo Android Java kaynaklarını içerir ancak henüz **tam, sıfırdan tekrarlanabilir bir derleme paketi değildir**. Derleme için Android SDK 34, ARM64 uyumlu aapt2, Java, D8, apksigner, Apache POI ve PDFBox bağımlılıkları ile XMLBeans Android uyarlaması gereklidir.

Özellikle masaüstü SDK içindeki x86_64 `aapt2` ikilisi ARM64 Termux'ta çalışmaz. Termux paketindeki `aapt2` kullanılmalıdır.

V5.3'ün cihazda kullanılan tam bağımlılık listesi ve derleme betiği doğrulandıktan sonra bu bölüm güncellenecektir. Şimdilik hazır APK kurulumu önerilir.

## Durum

V5.3 Beta. PDF düzenleme/kaydetme ve Excel kaydetme farklı belgelerle ayrıca doğrulanmalıdır.

## Doğrulanan yerel derleme (27 Ekim 2026)

Samsung ARM64 üzerinde, daha önce hazırlanmış Android SDK ve Office bağımlılıklarıyla `scripts/build-termux-local.sh` çalıştırıldı. Kullanıcı tarafından bildirilen sonuç: `build-local/BYBLACK-Office-V5.3-local.apk` (yaklaşık 31 MB), `apksigner` uyarıları dışında hata yok. Bu, APK'nin cihazda kurulup işlevlerinin test edildiği veya sıfırdan temiz Termux ortamında derlendiği anlamına gelmez.

```bash
git clone https://github.com/black1771/Byblack-Office.git
cd Byblack-Office
pkg install -y aapt2
bash scripts/build-termux-local.sh
```

Derleme betiği bağımlılıkları `$HOME/ByblackOffice` ve `$HOME/android-sdk` altında bekler. Bu klasörler ve gerekli JAR'lar olmadan komut çalışmaz. Üretilen APK, yerel debug keystore ile imzalanır; GitHub Releases APK'sıyla farklı imzalıysa Android doğrudan güncellemeye izin vermez. Böyle bir durumda uygulama verilerini yedeklemeden mevcut sürümü kaldırmayın.
