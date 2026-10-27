# BYBLACK Office — English

[🇹🇷 Türkçe](README.md) | **🇬🇧 English**

BYBLACK Office is an experimental Android document application developed directly on an Android phone using **Termux**, Java and the Android SDK. Our goal is to build a practical document viewer and editor with help from the community.

**Current version: V5.3 Beta.** This is a work in progress, not a fully featured replacement for established Office suites.

## Download and install

**[Download BYBLACK Office V5.3 Beta](https://github.com/black1771/Byblack-Office/releases/tag/v5.3-beta)**

Open the release, download the APK under **Assets**, then open it on your Android device to install. Android may ask for permission to install apps from that source. Only install software you trust, and back up important documents before testing.

## Screenshot

![BYBLACK Office home screen](docs/screenshots/Screenshot_20261027_170222.jpg)

## Our development journey

1. **Android-only development:** We set up Java, the Android SDK and APK packaging tools in Termux, using a build process without Gradle.
2. **Document engines:** We integrated Apache POI for Excel processing and explored PDFBox and Android PDF rendering capabilities.
3. **Excel loading fixes:** We investigated XMLBeans resource-loading problems on Android and bundled the resources required by the workbook reader.
4. **Spreadsheet UI:** We added a grid, cell selection and cell-value editing. Saving and reopening edited workbooks still need broader testing.
5. **Interface improvements:** We built Word, Excel, PowerPoint and PDF entry points, a recent-file area, Turkish/English language switching and a custom launcher icon.
6. **PDF in V5.3:** We added code for page rendering, text annotations and saving a new PDF. End-to-end testing on different PDFs is still pending.
7. **ARM64 build fix:** We resolved an architecture mismatch caused by attempting to execute a desktop x86_64 `aapt2` binary on an ARM64 Android device.
8. **GitHub release:** We published Java sources, manifest, icon resources, screenshot, installation documentation and the V5.3 Beta APK.
9. **Build from repository:** The repository was cloned on the development phone. A locally signed APK of approximately **31 MB** was built and successfully opened using previously installed dependencies.

## Feature and test status

| Feature | Status |
|---|---|
| Local Termux ARM64 APK build | Successful on development device |
| Launching the locally built APK | Confirmed |
| Editing an Excel cell value in the UI | Observed in an earlier version |
| Saving and reopening edited Excel files | Further testing required |
| Rendering PDF pages | Code added; broader testing required |
| Adding text to a PDF and saving it | Code added; end-to-end testing required |
| Full Word and PowerPoint editing | Not verified / not promised |
| Building from a clean Termux installation | Not yet verified |

**Beta limitations:** PDF export may rasterize pages, potentially losing selectable text, links, forms and digital signatures. Do not test with your only copy of an important document.

## Help us improve BYBLACK Office!

We welcome **Android users, testers and developers worldwide**.

- Download and test the APK on different Android devices.
- Report reproducible PDF and Excel issues, especially editing, saving and reopening.
- Suggest improvements to performance, interface, accessibility and translations.
- Contribute Java/Android, Termux, PDF or spreadsheet expertise through issues and pull requests.
- When reporting bugs, include your device model, Android version, app version, steps to reproduce, expected behavior and actual behavior. Remove private data from screenshots and sample files.

**[Report a bug or request a feature](https://github.com/black1771/Byblack-Office/issues/new)**

## Building from source

See the [installation and build guide](INSTALL.md) and the [local Termux build script](scripts/build-termux-local.sh).

**Important:** The current build script relies on Android SDK files and JAR dependencies previously installed in the original development environment. It is **not yet a one-command clean-install build**. Making it reproducible is a priority for contributors.

## Roadmap

- Validate PDF annotation, export and reopening
- Improve Excel formatting and reliable saving
- Extend Word and PowerPoint capabilities
- Refine the interface and translations
- Automate dependency setup and add regression tests
- Make clean Termux builds reproducible

## Licensing and contributions

Third-party dependency licenses and the project's own license are still under review. **No open-source license has been granted yet.** Public source visibility alone does not grant permission to redistribute or reuse the code. Please raise an issue to discuss licensing before submitting substantial contributions.

---

**BYBLACK Office — Developed on Android with Termux.**

Created by [@black1771](https://github.com/black1771). We welcome feedback and collaboration.
