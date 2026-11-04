# Contributing to BYBLACK Office

Thank you for helping improve BYBLACK Office, an experimental Android document application developed with Termux and Java.

## Before contributing

- Check existing issues or open a new issue describing the proposed change.
- **Licensing is still under review.** The repository currently does not grant a general open-source reuse license. Before submitting substantial code or assets, discuss licensing and provenance with the maintainer in an issue.
- Do not copy third-party source code, images, fonts, JARs or other assets into the repository without documenting their origin and applicable license obligations.
- See [THIRD_PARTY_NOTICES.md](THIRD_PARTY_NOTICES.md) for the incomplete third-party inventory.

## Reporting a bug

Include:
1. BYBLACK Office version and installation source.
2. Device model and Android version.
3. Clear steps to reproduce.
4. Expected and actual behavior.
5. Relevant logs or screenshots, **with personal information and document contents removed**.

For PDF or Office file bugs, prefer a small synthetic sample document that you have permission to share.

## Proposing a change

- Open an issue first for major features, dependency changes or changes to document-writing behavior.
- Keep pull requests focused; explain what changed, why, and how you tested it.
- Include the exact device, Android version, toolchain, and test commands used.
- Distinguish between tests you actually ran and tests you could not run.
- Never claim full Word, Excel, PowerPoint or PDF editing support without reproducible end-to-end tests.
- Avoid committing signing keys, credentials, personal documents, build outputs or proprietary dependencies.

## Building and testing

Read [INSTALL.md](INSTALL.md) and [README.md](README.md). The local Termux build currently depends on a preconfigured Android SDK and third-party JAR files; a clean one-command build is **not yet verified**.

When testing document changes, check open, edit, save, close and reopen on a copy of the file. PDF transformations may flatten pages and lose interactive features or digital signatures.

## Security issues

Do not publish exploit details, private documents or credentials in a public issue. Contact the maintainer privately through an appropriate GitHub channel first, if available.

## License status

Contributions are subject to an unresolved project licensing decision. Please coordinate with the maintainer before submitting substantial original code or third-party material.
