# حسن ادا کورس — Android App 📖

**Jil Quran Academy — Tajweed & Recitation**  
Native Android WebView App — 13 Chapter Urdu Course

---

## 📥 Download APK

Go to [**Releases**](../../releases) → Download the latest `app-release.apk`

---

## ✨ Features
- 🖼️ Native splash screen (3 seconds)
- 📡 Online: loads latest content from GitHub Pages (auto-updates!)
- 📴 Offline: bundled HTML fallback works without internet
- 🔙 Android back button navigation
- 🚀 No browser UI — full screen native feel
- 📱 Portrait locked, hardware accelerated

## 🔄 How Auto-Update Works
1. App opens → checks if online
2. If **online** → loads `https://lineshape-droid.github.io/husn-ada-course/` (always latest)
3. If **offline** → loads bundled `assets/index.html`
4. No new APK needed for content updates!

## 🏗️ Build Locally
```bash
git clone https://github.com/lineshape-droid/husn-ada-app.git
cd husn-ada-app
./gradlew assembleRelease
# APK: app/build/outputs/apk/release/app-release-unsigned.apk
```

---
© Jil Quran Academy. All rights reserved.
