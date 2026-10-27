# Third-party software and attribution / Üçüncü taraf yazılımlar

**Status / Durum:** Preliminary inventory; **not** a complete license-compliance audit. This document is not legal advice. / Ön envanterdir; tam lisans uyumluluk denetimi değildir.

The project includes references to third-party libraries and bundles their code and/or resources into Android APKs. Redistribution obligations apply to the **APK** as well as the source repository. / Proje üçüncü taraf kütüphaneleri kullanır; dağıtım koşulları yalnızca GitHub kaynak koduna değil **APK'ya** da uygulanır.

| Component / Bileşen | Observed use / Gözlenen kullanım | Upstream / Kaynak | License / Lisans |
|---|---|---|---|
| Apache POI (including OOXML modules), 5.4.1 in local build notes | Excel, Word, PowerPoint reading and editing APIs | https://poi.apache.org/ | Apache License 2.0 |
| Apache XMLBeans, 5.3.0 in local build notes | OOXML schemas and XML resource loading; repository contains `org/apache/xmlbeans/...` Java files | https://xmlbeans.apache.org/ | Apache License 2.0 |
| PdfBox-Android (Tom Roush), exact version **unverified** | `com.tom_roush.pdfbox` Java imports and bundled `com/tom_roush/fontbox/resources/cmap` assets | https://github.com/TomRoush/PdfBox-Android | Apache License 2.0 (verify bundled dependencies and resources) |
| Apache Commons Codec, 1.17.0 in local build notes | Local Java build dependency | https://commons.apache.org/proper/commons-codec/ | Apache License 2.0 |
| Android SDK / Android framework | APK compilation, PDF rendering, UI | https://developer.android.com/ | Various Android SDK and AOSP terms; inspect actual artifacts |
| Other transitive JARs | Local build script includes additional `office-engines/*.jar` files | **Not yet inventoried** | **Not yet verified** |

## Required work before declaring license compliance / Lisans uyumluluğu için yapılacaklar

1. **Inventory the exact distributed APK.** Identify all embedded libraries, versions, assets, fonts and other transitive dependencies. / APK içindeki tüm kütüphaneleri, sürümleri, varlıkları ve dolaylı bağımlılıkları belirleyin.
2. **Obtain authoritative LICENSE and NOTICE files** from the exact distributed versions. Preserve required copyright and attribution statements, including applicable upstream NOTICE entries. / Kullanılan tam sürümlerin LICENSE ve NOTICE dosyalarını alın; gerekli telif ve atıf bildirimlerini koruyun.
3. **Audit vendored sources and resources** under `app/src/org/apache/xmlbeans` and `app/assets/com/tom_roush`, including any modified upstream code. / Depoya kopyalanmış kaynakları, kaynak dosyalarını ve değişiklikleri inceleyin.
4. **Check images and branding.** The BYBLACK icon and screenshot must be original or used with permission; do not assume an image is free of copyright merely because it is available online. / Simge ve ekran görüntüsünün haklarını kontrol edin.
5. **Review the release APK itself.** A Markdown acknowledgment alone does not automatically fulfill all license requirements for redistributed binaries. / Yalnızca Markdown atfı, dağıtılan APK için tüm lisans yükümlülüklerini otomatik karşılamaz.
6. **Choose a license for BYBLACK's own original code** after separating upstream code and checking compatibility. Until then, the repository does not grant a general reuse license. / Projenin özgün kodu için uyumlu bir lisans belirleyin.

## Apache License 2.0

Official text: https://www.apache.org/licenses/LICENSE-2.0

This page links to the official license; it does **not** replace any license/NOTICE files that must accompany a distribution. / Bu bağlantı, dağıtıma eklenmesi gereken LICENSE/NOTICE dosyalarının yerine geçmez.

## Current risk / Mevcut risk

**Not yet cleared for compliance.** The public V5.3 Beta APK was published before a complete third-party inventory and license/NOTICE packaging audit. Review and remedy the distribution promptly; if required notices cannot be verified, consider temporarily unpublishing the binary while auditing. / V5.3 Beta APK, eksiksiz üçüncü taraf envanteri ve lisans/NOTICE denetimi tamamlanmadan yayımlandı. İncelemeyi hızla tamamlayın; zorunlu bildirimler doğrulanamıyorsa inceleme sırasında APK'yı geçici olarak yayından kaldırmayı değerlendirin.

## Verified upstream references for missing embedded notices / Eksik gömülü bildirimler için kaynaklar

| Local file | Upstream metadata | Remaining action |
|---|---|---|
| `SparseBitSet-1.3.jar` | [Maven Central com.zaxxer:SparseBitSet:1.3](https://central.sonatype.com/artifact/com.zaxxer/SparseBitSet/1.3), Apache-2.0 | Obtain the matching distribution's complete LICENSE/NOTICE obligations; compare JAR hashes. |
| `curvesapi-1.08.jar` | [Maven Central com.github.virtuald:curvesapi:1.08](https://central.sonatype.com/artifact/com.github.virtuald/curvesapi), BSD 3-Clause reported | Obtain exact upstream copyright and BSD license text, preserve redistribution notices, compare JAR hashes. |
| `pdfbox-classes.jar` | [TomRoush/PdfBox-Android](https://github.com/TomRoush/PdfBox-Android), Apache-2.0 for main code | **Local repackaged JAR: provenance/version/content not yet established.** Inspect manifest, classes, bundled assets and upstream notices before claiming clearance. |

**Do not equate missing embedded notices with license-free software. Do not treat these upstream references as proof that the local JARs are byte-identical to published artifacts.**

**TR:** Bu kayıtlar kaynak tespitidir; APK'nın tüm telif koşullarına uyduğuna dair onay değildir. Eksik lisans metinleri doğrulanıp APK'ya dahil edilmelidir.

## Local artifact fingerprints / Yerel dosya parmak izleri (2026-10-27)

These hashes were provided from the Termux development environment; they have **not** been independently compared to official published artifacts.

| Local JAR | SHA-256 |
|---|---|
| `SparseBitSet-1.3.jar` | `f76b85adb0c00721ae267b7cfde4da7f71d3121cc2160c9fc00c0c89f8c53c8a` |
| `curvesapi-1.08.jar` | `ad95b08b8bbf9d7d17e5e00814898fa23324f32bc5b62f1a37801e6a56ce0079` |
| `pdfbox-classes.jar` | `35f592ed840ec0903546b086e6181589796edcf0a142e2a5d5f3a3cf1e74739c` |

The local PDFBox JAR contains `com/tom_roush/pdfbox`, `com/tom_roush/fontbox` and `com/tom_roush/harmony` packages. Its manifest did not reveal a version. **License coverage for all bundled classes, including Harmony-derived classes, requires verification.**

**TR:** SHA-256 değerleri yerel Termux çıktısından alınmıştır; resmî dağıtımlarla henüz eşleştirilmedi. PDFBox paketinde Harmony sınıfları da bulunduğundan tüm bileşenlerin lisans bildirimleri ayrıca incelenmelidir.

## Additional artifact identification / Ek dosya doğrulaması

- **SparseBitSet 1.3:** The locally reported SHA-256 `f76b85adb0c00721ae267b7cfde4da7f71d3121cc2160c9fc00c0c89f8c53c8a` also appears in an independent published dependency-check report for `com.zaxxer:SparseBitSet:1.3`. [Independent checksum evidence](https://download.grif.totalenergies.com/COM/GRIF2026/GRIF2026.4/dependency-check-report.html). This strengthens artifact identification but does not substitute for license text.
- **CurvesAPI 1.08:** The upstream project describes a BSD-licensed main library and an Apache-2.0-derived `Crossing.java` from Apache Harmony. **Both** attribution sets must be considered. [Upstream project information](https://explore.market.dev/ecosystems/java/projects/curvesapi).
- **PdfBox-Android:** The upstream repository includes `LICENSE.txt` and `NOTICE.txt`, but the local `pdfbox-classes.jar` version remains unidentified. [Upstream repository](https://github.com/TomRoush/PdfBox-Android).

**Release gate / Yayın koşulu:** Do not mark the license review complete or publish a new cleared release until required full license/copyright/NOTICE texts have been collected, reviewed and included for every distributed component, including repackaged sources and assets.
