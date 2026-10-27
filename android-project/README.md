# BYBLACK Office — Android Office Projesi

**🇹🇷 [Türkçe](README.md) | 🇬🇧 [English](README_EN.md)**

BYBLACK Office, doğrudan Android telefon üzerinde **Termux**, Java ve Android SDK kullanılarak geliştirilen deneysel bir belge uygulamasıdır. Amacımız, topluluk katkısıyla kullanışlı bir belge görüntüleyici ve düzenleyici geliştirmek.

**Güncel sürüm: V5.3 Beta.** Bu proje geliştirme aşamasındadır; yerleşik Office uygulamalarının tüm özelliklerini sunduğu iddia edilmemektedir.

## 📥 İndir ve kur

**[BYBLACK Office V5.3 Beta APK indir](https://github.com/black1771/Byblack-Office/releases/tag/v5.3-beta)**

GitHub sürüm sayfasında **Assets** bölümünden APK'yı indirip Android telefonunda aç. Android, ilgili uygulama için yükleme izni isteyebilir. Yalnızca güvendiğin yazılımları kur; önemli belgelerini test etmeden önce yedekle.

## 📱 Ekran görüntüsü

![BYBLACK Office ana ekranı](docs/screenshots/Screenshot_20261027_170222.jpg)

## 🛠️ Baştan sona neler yaptık?

1. **Telefon üzerinde geliştirme:** Termux içinde Java, Android SDK ve APK paketleme araçlarıyla Gradle kullanmadan derleme altyapısı oluşturduk.
2. **Belge motorları:** Excel için Apache POI; PDF için PDFBox ve Android'in PDF görüntüleme altyapısını kullandık.
3. **Excel açma sorunları:** Android'de XMLBeans kaynaklarının yüklenmesiyle ilgili hataları araştırıp gerekli kaynakları APK içine ekledik.
4. **Excel düzenleme:** Tablo görünümü, hücre seçme ve hücre değerini değiştirme alanı ekledik. Kaydetme ve yeniden açma işlemleri için daha kapsamlı test gerekiyor.
5. **Arayüz:** Word, Excel, PowerPoint ve PDF bölümleri, son dosyalar, Türkçe/İngilizce seçimi ve özel BYBLACK simgesi üzerinde çalıştık.
6. **PDF V5.3:** Gerçek PDF sayfa görüntüleme, üzerine yazı ekleme ve yeni PDF olarak kaydetme kodlarını ekledik. Farklı PDF'lerle uçtan uca testler henüz tamamlanmadı.
7. **ARM64 hatasının çözümü:** Masaüstü x86_64 `aapt2` aracının ARM64 telefonda çalışmaması sorununu Termux uyumlu `aapt2` kullanarak çözdük.
8. **GitHub yayını:** Java kaynaklarını, manifesti, ikon kaynağını, ekran görüntüsünü, kurulum belgelerini ve V5.3 Beta APK'sını yayımladık.
9. **GitHub kodundan APK:** Depoyu telefona klonlayıp mevcut bağımlılıklarla yaklaşık **31 MB** APK oluşturduk ve uygulamanın açıldığını doğruladık.

## 🧪 Özellik ve test durumu

| Özellik | Durum |
|---|---|
| Termux ARM64 yerel APK derleme | Geliştirme telefonunda başarılı |
| Derlenen APK'nın açılması | Doğrulandı |
| Excel hücre değerini ekranda değiştirme | Önceki sürümde gözlemlendi |
| Excel dosyasını kaydedip yeniden açma | Ek test gerekiyor |
| PDF sayfalarını görüntüleme | Kod eklendi; kapsamlı test gerekiyor |
| PDF üzerine yazı ekleyip kaydetme | Kod eklendi; uçtan uca test gerekiyor |
| Word / PowerPoint tam düzenleme | Doğrulanmadı / vaat edilmiyor |
| Temiz Termux ortamında sıfırdan derleme | Henüz doğrulanmadı |

**Beta uyarısı:** PDF kaydetme sırasında sayfalar görüntüye dönüştürülebilir; seçilebilir metinler, bağlantılar, formlar ve dijital imzalar kaybolabilir. Önemli belgelerinin tek kopyası üzerinde deneme yapma.

## 🤝 BYBLACK Office'i birlikte geliştirelim!

**Uygulamayı indiren kullanıcıların ve dünyanın her yerinden geliştiricilerin katkısını bekliyoruz.**

- APK'yı farklı Android telefonlarda kurup test edin.
- PDF ve Excel açma, düzenleme, kaydetme ve yeniden açma hatalarını bildirin.
- Performans, tasarım, erişilebilirlik ve çeviri önerilerinizi paylaşın.
- Java/Android, Termux, PDF veya Excel alanlarında bilgi sahibiyseniz sorunlara çözüm ve pull request gönderin.
- Hata bildirirken cihaz modeli, Android sürümü, uygulama sürümü, yeniden oluşturma adımları, beklenen ve gerçekleşen sonucu yazın. Özel bilgileri ekran görüntülerinden çıkarın.

**[Hata bildir veya geliştirme öner](https://github.com/black1771/Byblack-Office/issues/new)**

## 🔧 Kaynak koddan derleme

[Kurulum ve derleme rehberi](INSTALL.md) · [Termux yerel derleme betiği](scripts/build-termux-local.sh)

Mevcut betik, geliştirme telefonunda önceden kurulmuş Android SDK ve JAR bağımlılıklarını kullanır. **Henüz temiz bir Termux ortamında tek komutla derleme sağlamaz.** Bunu tamamlamak katkıya açık önceliklerimizden biridir.

## 🗺️ Yol haritası

- PDF üzerine yazı ekleme, kaydetme ve yeniden açmayı doğrulama
- Excel biçimlendirme ve güvenilir kaydetme
- Word / PowerPoint desteğini geliştirme
- Arayüz ve çevirileri iyileştirme
- Bağımlılık kurulumunu otomatikleştirme ve regresyon testleri
- Temiz Termux kurulumundan tekrarlanabilir APK derleme

## 📜 Lisans ve katkılar

Proje kodunun ve üçüncü taraf bağımlılıkların lisans değerlendirmesi henüz tamamlanmadı. **Şu anda açık kaynak lisansı verilmiş değildir.** Kodun GitHub'da görünmesi tek başına yeniden kullanım veya dağıtım izni sağlamaz. Kapsamlı katkılardan önce lisans konusunu bir issue üzerinden görüşebilirsiniz.

---

**BYBLACK Office — Android üzerinde Termux ile geliştirildi.**

Geliştirici: [@black1771](https://github.com/black1771). Test, öneri ve katkılara açığız.
