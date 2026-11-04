#!/usr/bin/env python3
"""Dependency-free repository integrity checks; does not build or execute app code."""
from pathlib import Path
import sys
import unittest
import xml.etree.ElementTree as ET

ROOT = Path(__file__).resolve().parents[1]
ANDROID_NS = "{http://schemas.android.com/apk/res/android}"

class RepositoryChecks(unittest.TestCase):
    def test_android_manifest(self):
        manifest = ROOT / "app/AndroidManifest.xml"
        self.assertTrue(manifest.is_file())
        root = ET.parse(manifest).getroot()
        self.assertEqual(root.tag, "manifest")
        self.assertIsNotNone(root.find("application"))
        self.assertTrue(root.get("package") or root.find("application") is not None)

    def test_app_sources_present(self):
        self.assertTrue(list((ROOT / "app/src").rglob("*.java")), "No Java sources")
        self.assertTrue((ROOT / "app/res").is_dir(), "Android resources missing")

    def test_documentation_present(self):
        for name in ("README.md", "README_EN.md", "INSTALL.md",
                     "THIRD_PARTY_NOTICES.md", "CONTRIBUTING.md"):
            with self.subTest(file=name):
                path = ROOT / name
                self.assertTrue(path.is_file(), f"Missing {name}")
                self.assertTrue(path.read_text(encoding="utf-8").strip())

    def test_no_committed_sensitive_extensions(self):
        forbidden = {".keystore", ".jks", ".pem", ".p12", ".pfx"}
        for p in ROOT.rglob("*"):
            if p.is_file() and ".git" not in p.parts:
                with self.subTest(file=str(p.relative_to(ROOT))):
                    self.assertNotIn(p.suffix.lower(), forbidden)

    def test_termux_build_is_not_ci_build(self):
        content = (ROOT / "scripts/build-termux-local.sh").read_text(encoding="utf-8")
        self.assertIn("ByblackOffice", content)
        self.assertIn("android-sdk", content)

if __name__ == "__main__":
    unittest.main(verbosity=2)
