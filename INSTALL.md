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
