#!/usr/bin/env python3
"""V2: offline Android manifest/resource integrity tests; not an APK build."""
from pathlib import Path
import re
import unittest
import xml.etree.ElementTree as ET

ROOT = Path(__file__).resolve().parents[1]
ANDROID = "{http://schemas.android.com/apk/res/android}"

class AndroidIntegrityTests(unittest.TestCase):
    @classmethod
    def setUpClass(cls):
        cls.manifest = ET.parse(ROOT / "app/AndroidManifest.xml").getroot()
        cls.application = cls.manifest.find("application")

    def test_manifest_identity_and_sdk(self):
        self.assertEqual(self.manifest.tag, "manifest")
        self.assertTrue(self.manifest.get("package"))
        sdk = self.manifest.find("uses-sdk")
        self.assertIsNotNone(sdk)
        self.assertGreaterEqual(int(sdk.get(ANDROID + "minSdkVersion")), 1)

    def test_launcher_activity_has_source(self):
        activities = self.application.findall("activity")
        launchers = []
        for activity in activities:
            for intent in activity.findall("intent-filter"):
                actions = {x.get(ANDROID + "name") for x in intent.findall("action")}
                categories = {x.get(ANDROID + "name") for x in intent.findall("category")}
                if "android.intent.action.MAIN" in actions and "android.intent.category.LAUNCHER" in categories:
                    launchers.append(activity)
        self.assertEqual(len(launchers), 1, "Exactly one launcher activity expected")
        activity = launchers[0]
        self.assertEqual(activity.get(ANDROID + "exported"), "true")
        name = activity.get(ANDROID + "name")
        self.assertTrue(name)
        package = self.manifest.get("package")
        if name.startswith("."):
            name = package + name
        elif "." not in name:
            name = package + "." + name
        source = ROOT / "app/src" / (name.replace(".", "/") + ".java")
        self.assertTrue(source.is_file(), f"Missing launcher source: {source}")

    def test_icon_reference_resolves(self):
        icon = self.application.get(ANDROID + "icon")
        self.assertTrue(icon and icon.startswith("@drawable/"), f"Unexpected icon: {icon}")
        stem = icon.split("/", 1)[1]
        candidates = list((ROOT / "app/res").glob(f"drawable*/{stem}.*"))
        self.assertTrue(candidates, f"Missing drawable resource: {stem}")

    def test_android_resource_xml_parses(self):
        files = list((ROOT / "app/res").rglob("*.xml"))
        self.assertTrue(files, "No XML resources")
        for file in files:
            with self.subTest(file=str(file.relative_to(ROOT))):
                ET.parse(file)

    def test_app_java_packages_match_paths(self):
        sources = list((ROOT / "app/src/com/byblack").rglob("*.java"))
        self.assertTrue(sources, "No BYBLACK Java source files")
        for source in sources:
            with self.subTest(file=str(source.relative_to(ROOT))):
                text = source.read_text(encoding="utf-8")
                match = re.search(r"(?m)^\s*package\s+([\w.]+)\s*;", text)
                self.assertIsNotNone(match, "Missing package declaration")
                expected = Path(*match.group(1).split(".")) / source.name
                self.assertEqual(source.relative_to(ROOT / "app/src"), expected)

if __name__ == "__main__":
    unittest.main(verbosity=2)
