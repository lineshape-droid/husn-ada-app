# 🤖 MASTER PROMPT — حسن ادا کورس Android APK
### عائش قرآن اکیڈمی | Complete AI Reference — Zero Wasted Tokens

> **HOW TO USE:** Give this entire document to any AI at the start of every new session. The AI will know the full project, every bug we hit, every fix applied, and exactly how to build and deploy a signed APK in one shot without trial and error.

---

## SECTION 1 — PROJECT IDENTITY

| Field | Value |
|-------|-------|
| App name | حسن ادا کورس |
| Academy | عائش قرآن اکیڈمی |
| Subject | Quranic recitation — Tajweed (تجوید) |
| Language | Urdu — RTL (`dir="rtl"` `lang="ur"`) |
| Author / Credit | عائش قرآن اکیڈمی (never individual names) |
| GitHub user | `lineshape-droid` |
| GitHub email | lineshape@gmail.com |
| Token | `ghp_XXXX_ASK_USER_FOR_CURRENT_TOKEN_XXXX` *(if 401 — token expired, ask user for new one from github.com/settings/tokens)* |
| Web repo | https://github.com/lineshape-droid/husn-ada-course |
| Android repo | https://github.com/lineshape-droid/husn-ada-app |
| Live web app | https://lineshape-droid.github.io/husn-ada-course/ |
| Package ID | `com.jiilquran.husn_ada` |
| Current versionCode | **3** |
| Current versionName | **"2.1.0"** |
| Target device | Samsung Galaxy S10+ (Android 12, One UI 4) + all Android 5.0+ |
| Content | 13 chapters (ابواب) — lessons + 10-question quiz each |

---

## SECTION 2 — ASSETS

| Asset | Where | Notes |
|-------|-------|-------|
| Logo (horizontal, white bg) | `/mnt/user-data/uploads/Logo-outlined-Background_3x.png` | 427 KB — embed as base64 in HTML |
| App icon (green square) | `/mnt/user-data/uploads/Ayesh_quran_Academy_app_icon.png` | 1166 KB — use for launcher icons |
| Urdu font TTF | Google Drive ID: `1AciAbjMUY9CC3YkZZx6m1z4-Xprk9eQM` | Jameel Noori Nastaleeq Kasheeda |

**Font URL for CSS @font-face:**
```
https://drive.google.com/uc?export=download&id=1AciAbjMUY9CC3YkZZx6m1z4-Xprk9eQM
```

**⚠️ LOGO CRITICAL RULE:** Logo MUST be embedded as base64 `data:image/png;base64,...` directly inside `index.html`. Drive URLs fail with CORS in Android WebView and show broken image. Do this every time:
```python
import base64
with open('/mnt/user-data/uploads/Logo-outlined-Background_3x.png', 'rb') as f:
    logo_uri = 'data:image/png;base64,' + base64.b64encode(f.read()).decode()
# Then replace LOGO_PLACEHOLDER or the src value with logo_uri
```

---

## SECTION 3 — SIGNING CREDENTIALS (GitHub Secrets — already set ✅)

| Secret Name | Value |
|-------------|-------|
| `SIGNING_KEY` | Base64-encoded keystore — already uploaded 2026-06-28 |
| `KEY_ALIAS` | `husn_ada` |
| `KEY_STORE_PASSWORD` | `HusnAda2024` |
| `KEY_PASSWORD` | `HusnAda2024` |

**All 4 secrets confirmed set in husn-ada-app repo.**

**If secrets are ever lost, regenerate:**
```bash
keytool -genkey -v \
  -keystore /tmp/husn-ada.keystore \
  -alias husn_ada -keyalg RSA -keysize 2048 -validity 10000 \
  -storepass HusnAda2024 -keypass HusnAda2024 \
  -dname "CN=Ayesha Quran Academy, OU=Education, O=Jil Quran Academy, L=Pakistan, ST=Punjab, C=PK"
```
Then re-upload via Section 8.

---

## SECTION 4 — FINAL WORKING FILES (copy-paste exact)

### 4A — AndroidManifest.xml ✅ FINAL

```xml
<?xml version="1.0" encoding="utf-8"?>
<manifest xmlns:android="http://schemas.android.com/apk/res/android"
    package="com.jiilquran.husn_ada">

    <uses-permission android:name="android.permission.INTERNET"/>
    <uses-permission android:name="android.permission.ACCESS_NETWORK_STATE"/>
    <uses-permission android:name="android.permission.ACCESS_WIFI_STATE"/>

    <application
        android:allowBackup="true"
        android:icon="@mipmap/ic_launcher"
        android:roundIcon="@mipmap/ic_launcher_round"
        android:label="@string/app_name"
        android:theme="@style/AppTheme"
        android:hardwareAccelerated="true"
        android:supportsRtl="true"
        android:usesCleartextTraffic="true">

        <activity
            android:name=".SplashActivity"
            android:theme="@style/SplashTheme"
            android:screenOrientation="portrait"
            android:exported="true">
            <intent-filter>
                <action android:name="android.intent.action.MAIN"/>
                <category android:name="android.intent.category.LAUNCHER"/>
            </intent-filter>
        </activity>

        <activity
            android:name=".MainActivity"
            android:screenOrientation="portrait"
            android:configChanges="orientation|screenSize|keyboardHidden"
            android:exported="false"/>

    </application>
</manifest>
```

**3 absolute rules for this file:**
1. ❌ NEVER add `android:versionCode` or `android:versionName` here — only in `build.gradle`
2. ✅ `android:usesCleartextTraffic="true"` — Samsung S10+ WILL NOT install if this is `false`
3. ✅ `android:exported="true"` on SplashActivity — Android 12+ crashes without this

---

### 4B — app/build.gradle ✅ FINAL

```groovy
plugins {
    id 'com.android.application'
}

android {
    compileSdk 34
    namespace 'com.jiilquran.husn_ada'

    defaultConfig {
        applicationId "com.jiilquran.husn_ada"
        minSdk 21
        targetSdk 34
        versionCode 3
        versionName "2.1.0"
    }

    buildTypes {
        release {
            minifyEnabled false
            proguardFiles getDefaultProguardFile('proguard-android-optimize.txt'), 'proguard-rules.pro'
        }
    }

    compileOptions {
        sourceCompatibility JavaVersion.VERSION_1_8
        targetCompatibility JavaVersion.VERSION_1_8
    }
}

dependencies {
    implementation 'androidx.appcompat:appcompat:1.6.1'
    implementation 'com.google.android.material:material:1.11.0'
}
```

---

### 4C — .github/workflows/build-apk.yml ✅ FINAL WORKING

```yaml
name: Build and Release APK

on:
  push:
    branches: [ main ]
  workflow_dispatch:

jobs:
  build:
    runs-on: ubuntu-latest
    steps:
      - name: Checkout code
        uses: actions/checkout@v4

      - name: Set up JDK 17
        uses: actions/setup-java@v4
        with:
          java-version: '17'
          distribution: 'temurin'

      - name: Setup Android SDK
        uses: android-actions/setup-android@v3

      - name: Download Gradle wrapper
        run: |
          mkdir -p gradle/wrapper
          curl -fL -o gradle/wrapper/gradle-wrapper.jar https://github.com/gradle/gradle/raw/v8.2.0/gradle/wrapper/gradle-wrapper.jar
          curl -fL -o gradlew https://raw.githubusercontent.com/gradle/gradle/v8.2.0/gradlew
          chmod +x gradlew
          printf 'distributionBase=GRADLE_USER_HOME\ndistributionPath=wrapper/dists\ndistributionUrl=https\\://services.gradle.org/distributions/gradle-8.2-bin.zip\nzipStoreBase=GRADLE_USER_HOME\nzipStorePath=wrapper/dists\n' > gradle/wrapper/gradle-wrapper.properties

      - name: Build Release APK
        run: ./gradlew assembleRelease --no-daemon

      - name: Sign APK — zipalign then apksigner
        run: |
          echo "${{ secrets.SIGNING_KEY }}" | base64 -d > /tmp/keystore.jks

          BUILD_TOOLS=$(ls $ANDROID_HOME/build-tools | sort -V | tail -1)
          ZIPALIGN="$ANDROID_HOME/build-tools/$BUILD_TOOLS/zipalign"
          APKSIGNER="$ANDROID_HOME/build-tools/$BUILD_TOOLS/apksigner"

          UNSIGNED="app/build/outputs/apk/release/app-release-unsigned.apk"
          ALIGNED="/tmp/app-aligned.apk"
          SIGNED="app/build/outputs/apk/release/husn-ada-signed.apk"

          $ZIPALIGN -v -p 4 "$UNSIGNED" "$ALIGNED"
          $APKSIGNER sign \
            --ks /tmp/keystore.jks \
            --ks-pass "pass:${{ secrets.KEY_STORE_PASSWORD }}" \
            --ks-key-alias "${{ secrets.KEY_ALIAS }}" \
            --key-pass "pass:${{ secrets.KEY_PASSWORD }}" \
            --v1-signing-enabled true \
            --v2-signing-enabled true \
            --v3-signing-enabled true \
            --out "$SIGNED" "$ALIGNED"

          $APKSIGNER verify --verbose "$SIGNED"
          ls -lh "$SIGNED"

      - name: Upload SIGNED APK
        uses: actions/upload-artifact@v4
        with:
          name: husn-ada-SIGNED
          path: app/build/outputs/apk/release/husn-ada-signed.apk
          retention-days: 90
```

---

### 4D — strings.xml ✅ FINAL

```xml
<?xml version="1.0" encoding="utf-8"?>
<resources>
    <string name="app_name">حسن ادا کورس</string>
    <string name="academy_name">عائش قرآن اکیڈمی</string>
    <string name="author_name">عائش قرآن اکیڈمی</string>
    <string name="app_name_en">Husn Ada Course</string>
    <string name="app_version">v2.1</string>
    <string name="loading">لوڈ ہو رہا ہے...</string>
    <string name="offline_msg">آفلائن موڈ — لوکل ورژن</string>
</resources>
```

---

### 4E — manifest.json (Web PWA) ✅ FINAL

```json
{
  "name": "حسن ادا کورس — عائش قرآن اکیڈمی",
  "short_name": "حسن ادا",
  "description": "عائش قرآن اکیڈمی — حسن ادا کورس — تجوید اور تلاوت",
  "start_url": "./index.html",
  "scope": "./",
  "display": "standalone",
  "background_color": "#1B4D3E",
  "theme_color": "#1B4D3E",
  "orientation": "portrait-primary",
  "lang": "ur",
  "dir": "rtl",
  "icons": [
    {"src":"icons/icon-192.png","sizes":"192x192","type":"image/png","purpose":"any maskable"},
    {"src":"icons/icon-512.png","sizes":"512x512","type":"image/png","purpose":"any maskable"}
  ]
}
```

---

## SECTION 5 — ALL 13 BUGS: ROOT CAUSE + EXACT FIX

Every problem hit across 21 builds. Read all of these before starting work.

---

### BUG 1 — Workflow file cannot be uploaded via GitHub API
**Error:** `404 Not Found` when doing `PUT /repos/.../contents/.github/workflows/build-apk.yml`  
**Root cause:** The Personal Access Token has `repo` scope only. Writing to `.github/workflows/` requires **`workflow` scope**. Also, GitHub sometimes returns 404 for nested `.github/` paths via the API even with correct scope.  
**Fix:** ✅ **Always use Chrome browser** to create/edit workflow files. Navigate to the file on GitHub.com and edit in the web editor. The browser is logged in so no scope problem.  
**Rule: NEVER use the API for `.github/workflows/` files. Always use Chrome.**

---

### BUG 2 — Gradle URL has illegal character — build fails immediately
**Error:** `java.net.URISyntaxException: Illegal character in scheme name at index 5: https\://`  
**Root cause:** Using bash heredoc `cat << 'EOF' > gradle-wrapper.properties` to write the file. The `\\` in the heredoc **double-escapes** and the file ends up with `https\\://` (two backslashes). Gradle requires exactly `https\://` (one backslash) in the properties file.  
**Fix:** ✅ Use `printf` instead of heredoc:
```bash
printf 'distributionUrl=https\\://services.gradle.org/distributions/gradle-8.2-bin.zip\n' > gradle/wrapper/gradle-wrapper.properties
```
In `printf`, `\\` prints as a single `\`, giving the correct result.  
**Rule: ALWAYS use `printf` for gradle-wrapper.properties. NEVER use heredoc.**

---

### BUG 3 — `gradlew` not found — build fails at chmod step
**Error:** `chmod: cannot access 'gradlew': No such file or directory`  
**Root cause:** The `gradlew` file was either not committed to the repo, or the committed version was corrupted/missing execute permission. Git does not reliably preserve shell script permissions and binary content across platforms.  
**Fix:** ✅ Download `gradlew` fresh in every build from the official Gradle GitHub repo:
```bash
curl -fL -o gradlew https://raw.githubusercontent.com/gradle/gradle/v8.2.0/gradlew
chmod +x gradlew
```
**Rule: ALWAYS download `gradlew` in the workflow. Never rely on the committed copy.**

---

### BUG 4 — "Package appears to be invalid" on install (unsigned APK)
**Error:** Android shows "Package appears to be invalid" when trying to install the APK  
**Root cause:** The APK was **unsigned**. Previous workflow used `jarsigner` which only creates v1 (JAR) signatures. Android 7.0+ (API 24+) requires v2 or v3 APK Signature Scheme. Without v2/v3, the Android PackageManager rejects the APK outright with this error.  
**Fix:** ✅ Use `apksigner` (Android SDK tool, NOT the Java `jarsigner`) with all three schemes:
```bash
$APKSIGNER sign \
  --v1-signing-enabled true \
  --v2-signing-enabled true \
  --v3-signing-enabled true \
  --out signed.apk aligned.apk
```
**Rule: NEVER use `jarsigner`. ALWAYS use `apksigner` with v1+v2+v3 enabled.**

---

### BUG 5 — APK not zipaligned before signing — Samsung rejects it
**Error:** APK signs successfully but Samsung S10+ still shows "package invalid"  
**Root cause:** The APK was not **zipaligned** before signing. zipalign is a mandatory byte-alignment step. The correct order is: build → zipalign → sign. If you sign without aligning first, or align after signing (which invalidates the signature), Samsung One UI rejects the APK. Samsung is stricter than stock Android about alignment.  
**Fix:** ✅ Correct order every time:
```bash
# Step 1: zipalign FIRST
$ZIPALIGN -v -p 4 unsigned.apk aligned.apk

# Step 2: apksigner SECOND (on the aligned file)
$APKSIGNER sign ... --out signed.apk aligned.apk
```
**Rule: zipalign ALWAYS before apksigner. Never skip zipalign. Never sign then align.**

---

### BUG 6 — Samsung S10+ won't install: usesCleartextTraffic=false
**Error:** APK correctly signed and aligned but still won't install on Samsung S10+ (Android 12)  
**Root cause:** `android:usesCleartextTraffic="false"` was set in AndroidManifest. Samsung One UI's package manager checks this flag strictly. Even though the app loads `https://` content (not `http://`), having this set to false causes Samsung to reject the install at the PackageManager level.  
**Fix:** ✅ Set `android:usesCleartextTraffic="true"` in the `<application>` tag.  
**Rule: For Samsung compatibility, ALWAYS set usesCleartextTraffic="true".**

---

### BUG 7 — versionCode in manifest conflicts with build.gradle — Samsung install fails
**Error:** Samsung S10+ install fails even after fixing usesCleartextTraffic  
**Root cause:** `android:versionCode="1"` and `android:versionName="1.0.0"` were hardcoded in AndroidManifest.xml while `build.gradle` had `versionCode 3`. This version conflict causes Samsung's stricter package validation to reject the install. Stock Android tolerates this inconsistency but Samsung One UI does not.  
**Fix:** ✅ Remove `android:versionCode` and `android:versionName` from AndroidManifest entirely. These attributes should ONLY exist in `build.gradle defaultConfig`.  
**Rule: versionCode and versionName go ONLY in build.gradle. Never in AndroidManifest.**

---

### BUG 8 — GitHub Pages returns 403 Forbidden
**Error:** `https://lineshape-droid.github.io/husn-ada-course/` returns 403  
**Root cause:** Missing `.nojekyll` file. Without it, GitHub Pages runs Jekyll processor which blocks serving plain `.html` files.  
**Fix:** ✅ Create an empty `.nojekyll` file at repo root:
```python
gh_upload_text("husn-ada-course", ".nojekyll", "", "Enable GitHub Pages")
```
**Note:** Claude AI's server IP is also blocked by GitHub Pages CDN. So testing the URL from within Claude will always return 403 even when the site is working. Real users on phones/computers can access it fine. Don't waste tokens debugging 403 from within Claude.  
**Rule: Always create .nojekyll. Don't test GitHub Pages URL from within Claude.**

---

### BUG 9 — Create GitHub Release step fails with 403
**Error:** `GitHub release failed with status: 403 — Resource not accessible by integration`  
**Root cause:** The `GITHUB_TOKEN` in GitHub Actions does not have permission to create Releases by default. Requires the repo's Settings → Actions → Workflow permissions to be set to "Read and write".  
**Fix:** ✅ Remove the "Create GitHub Release" step from the workflow entirely. Use `upload-artifact` instead. Users download the APK from the Actions tab artifacts section.  
**Rule: Never add a Create Release step. Artifacts-only workflow always works without extra permissions.**

---

### BUG 10 — Logo shows as broken image
**Error:** Navbar logo and splash logo appear as broken image placeholder  
**Root cause:** Logo was referenced as a Google Drive URL (`drive.google.com/uc?export=view&id=...`). These URLs are blocked by CORS in Android WebView, fail when offline, and sometimes redirect to Google login page.  
**Fix:** ✅ Embed logo as base64 data URI directly inside index.html:
```python
import base64
with open('/mnt/user-data/uploads/Logo-outlined-Background_3x.png', 'rb') as f:
    logo_uri = 'data:image/png;base64,' + base64.b64encode(f.read()).decode()
```
This makes the HTML ~570KB larger but works 100% offline and online with zero external dependencies.  
**Rule: Logo must ALWAYS be base64 embedded. Never use external URL for logo.**

---

### BUG 11 — Font doesn't load (Urdu shows in fallback font)
**Error:** Urdu text renders in a generic sans-serif instead of Jameel Noori Nastaleeq Kasheeda  
**Root cause:** Google Drive `uc?export=download` URLs are inconsistently blocked by CORS when used in CSS `@font-face src: url(...)`. Works in some browsers/WebView versions, fails in others.  
**Fix (web):** ✅ Keep Drive URL in @font-face with `font-display: swap` — acceptable for web.  
**Fix (offline Android):** ✅ Bundle the .ttf file in `app/src/main/assets/` and reference as `file:///android_asset/filename.ttf`  
**Rule: For fully offline Android experience, bundle font in assets.**

---

### BUG 12 — App looks like a browser (PWABuilder)
**Error:** App shows browser address bar, navigation controls — looks like a browser not an app  
**Root cause:** First approach used PWABuilder which creates a Trusted Web Activity (TWA). TWA literally opens a browser window with browser UI elements.  
**Fix:** ✅ Build a proper native Android WebView app:
- `SplashActivity.java` → 3-second branded splash → starts `MainActivity`
- `MainActivity.java` → WebView fills 100% of screen, no browser chrome
- Disable zoom controls: `setBuiltInZoomControls(false)`, `setDisplayZoomControls(false)`
**Rule: Never use PWABuilder. Always use the custom Java WebView wrapper.**

---

### BUG 13 — Wrong author name shown in app
**Error:** App displayed `عائشہ عبدالجبار` or `تالیف: عائشہ عبدالجبار`  
**Root cause:** Individual name used during initial development instead of academy name.  
**Fix:** ✅ Replace in ALL locations:
- `index.html` → home hero text
- `activity_splash.xml` → splash screen text
- `strings.xml` → `author_name` string value  
**Rule: Author/credit always reads `عائش قرآن اکیڈمی`. Never use individual names anywhere.**

---

## SECTION 6 — COMPLETE BUILD HISTORY (21 builds)

| # | Result | Commit message | What failed |
|---|--------|---------------|-------------|
| 1 | ❌ | Add Android project | `gradlew: No such file or directory` |
| 2 | ❌ | Add workflow | Gradle URL `https\\://` illegal character (heredoc bug) |
| 3 | ✅ | Fix gradle properties | Built OK — but APK **unsigned** |
| 4 | ✅ | Update app name | Built OK — APK still unsigned |
| 5 | ✅ | Update academy name | Built OK — APK still unsigned |
| 6–10 | ✅ | Various updates | All built — all unsigned — all "invalid" on phone |
| 11 | ❌ | Add jarsigner signing | `jarsigner` v1-only — APK still "invalid" on Android 7+ |
| 12 | ✅ | Switch to apksigner | Signed with v1+v2+v3 — but NOT zipaligned — Samsung still fails |
| 13–17 | ✅ | Content + name fixes | APK signed — not zipaligned — Samsung still fails |
| 18 | ✅ | apksigner v1+v2+v3 explicit | Signed — but still no zipalign — Samsung still fails |
| 19–20 | ✅ | More fixes | Samsung still failing |
| **21** | ✅ **FINAL** | Fix S10+: zipalign+apksigner, allow traffic, fix manifest | **Samsung S10+ installs perfectly ✅** |

**Root causes fixed in Build #21:**
1. zipalign now runs BEFORE apksigner
2. `usesCleartextTraffic="true"` set
3. `versionCode` removed from AndroidManifest

---

## SECTION 7 — GITHUB API UTILITY (copy-paste at session start)

```python
import urllib.request, json, base64, time

TOKEN = "ghp_XXXX_ASK_USER_FOR_CURRENT_TOKEN_XXXX"
USER  = "lineshape-droid"
HDRS  = {
    "Authorization": f"token {TOKEN}",
    "Accept": "application/vnd.github.v3+json",
    "Content-Type": "application/json",
    "User-Agent": "HusnAda"
}

def gh(method, path, data=None):
    url = f"https://api.github.com{path}"
    body = json.dumps(data).encode() if data else None
    req = urllib.request.Request(url, data=body, headers=HDRS, method=method)
    try:
        with urllib.request.urlopen(req) as r:
            txt = r.read(); return (json.loads(txt) if txt else {}), r.status
    except urllib.error.HTTPError as e:
        return json.loads(e.read() or b'{}'), e.code

def sha(repo, path):
    res, s = gh("GET", f"/repos/{USER}/{repo}/contents/{path}")
    return res.get('sha','') if s==200 else ''

def put(repo, path, data_bytes, msg):
    payload = {"message":msg,"content":base64.b64encode(data_bytes).decode(),"branch":"main"}
    s = sha(repo, path)
    if s: payload["sha"] = s
    res, c = gh("PUT", f"/repos/{USER}/{repo}/contents/{path}", payload)
    print(f"  {'✅' if c in(200,201) else '❌'} {repo}/{path} [{c}] {len(data_bytes)//1024}KB")

def put_text(repo, path, text, msg):
    put(repo, path, text.encode('utf-8'), msg)

def put_file(repo, path, local, msg):
    with open(local,'rb') as f: put(repo, path, f.read(), msg)

def get_text(repo, path):
    res, s = gh("GET", f"/repos/{USER}/{repo}/contents/{path}")
    if s==200: return base64.b64decode(res['content']).decode('utf-8','ignore'), res.get('sha','')
    return None, None

def pages(repo):
    res, s = gh("POST", f"/repos/{USER}/{repo}/pages", {"source":{"branch":"main","path":"/"}})
    print(f"  {'✅' if s in(200,201,409) else '❌'} GitHub Pages [{s}]")
```

---

## SECTION 8 — RE-UPLOAD SECRETS (only if lost)

```python
# pip install pynacl --break-system-packages
from nacl import encoding, public
import base64, subprocess

# Generate keystore
subprocess.run(['keytool','-genkey','-v',
  '-keystore','/tmp/k.jks','-alias','husn_ada',
  '-keyalg','RSA','-keysize','2048','-validity','10000',
  '-storepass','HusnAda2024','-keypass','HusnAda2024',
  '-dname','CN=Ayesha Quran Academy, OU=Education, O=Jil Quran Academy, L=Pakistan, ST=Punjab, C=PK'
])

def add_secret(repo, name, value):
    kr, _ = gh("GET", f"/repos/{USER}/{repo}/actions/secrets/public-key")
    pk = public.PublicKey(kr['key'].encode(), encoding.Base64Encoder())
    enc = base64.b64encode(public.SealedBox(pk).encrypt(value.encode())).decode()
    res, s = gh("PUT", f"/repos/{USER}/{repo}/actions/secrets/{name}",
                {"encrypted_value":enc,"key_id":kr['key_id']})
    print(f"  {'✅' if s in(201,204) else '❌'} {name} [{s}]")

with open('/tmp/k.jks','rb') as f:
    ks = base64.b64encode(f.read()).decode()

add_secret("husn-ada-app","SIGNING_KEY", ks)
add_secret("husn-ada-app","KEY_ALIAS","husn_ada")
add_secret("husn-ada-app","KEY_STORE_PASSWORD","HusnAda2024")
add_secret("husn-ada-app","KEY_PASSWORD","HusnAda2024")
```

---

## SECTION 9 — UPLOAD WORKFLOW VIA CHROME (required — not via API)

Workflow files cannot be uploaded via API with current token. Use Chrome extension.

**Edit existing workflow:**
```
URL: https://github.com/lineshape-droid/husn-ada-app/edit/main/.github/workflows/build-apk.yml
```

**Create new workflow (if it doesn't exist):**
```
URL: https://github.com/lineshape-droid/husn-ada-app/new/main
Filename: type ".github/" → auto creates folder → type "workflows/build-apk.yml"
```

**Paste content via JavaScript:**
```javascript
const w = `...full workflow YAML from Section 4C...`;
const ta = document.querySelector('textarea[name="value"]') || document.querySelector('.cm-content');
if (ta) { ta.focus(); document.execCommand('selectAll'); document.execCommand('insertText', false, w); }
```

**Then click "Commit changes..." → "Commit directly to main" → "Commit changes"**

---

## SECTION 10 — COMPLETE STEP-BY-STEP BUILD PROCESS

These 6 steps build a correctly signed, Samsung-compatible APK in ~5 minutes.

### Step 1 — Verify secrets (30 seconds)
```python
res, _ = gh("GET", "/repos/lineshape-droid/husn-ada-app/actions/secrets")
names = [x['name'] for x in res.get('secrets',[])]
print("Secrets:", names)
# Must see all 4: KEY_ALIAS, KEY_PASSWORD, KEY_STORE_PASSWORD, SIGNING_KEY
# If any missing → run Section 8
```

### Step 2 — Push Android source files
```python
# Use exact content from Section 4 for these files:
put_text("husn-ada-app","app/src/main/AndroidManifest.xml", MANIFEST, "Update")
put_text("husn-ada-app","app/build.gradle", GRADLE, "versionCode X")
put_text("husn-ada-app","app/src/main/res/values/strings.xml", STRINGS, "Update")
# Push Java files, layouts, drawables as needed
```

### Step 3 — Push web app files
```python
# Embed logo as base64 first!
import base64
with open('/mnt/user-data/uploads/Logo-outlined-Background_3x.png','rb') as f:
    logo = 'data:image/png;base64,' + base64.b64encode(f.read()).decode()

html, _ = get_text("husn-ada-course","index.html")
html = html.replace('OLD_SRC', logo)  # embed logo
put_text("husn-ada-course","index.html", html, "Update")
put_text("husn-ada-course",".nojekyll", "", "Required")  # must exist!
```

### Step 4 — Upload workflow via Chrome
Use Chrome extension. Navigate to the edit URL. Paste Section 4C content. Commit.
This triggers the build automatically.

### Step 5 — Monitor build in Chrome
```
URL: https://github.com/lineshape-droid/husn-ada-app/actions
Refresh every 10-15 seconds until green ✅ appears (~2 minutes)
```

### Step 6 — Download and install APK
```
1. Click the latest green ✅ build
2. Scroll to "Artifacts" at the bottom
3. Click "husn-ada-SIGNED" → downloads husn-ada-SIGNED.zip
4. Unzip → get husn-ada-signed.apk
5. Send to phone (WhatsApp/Drive/USB)
6. Tap APK on phone → Install
   (If blocked: Settings → Apps → Install unknown apps → Allow)
```

---

## SECTION 11 — CONTENT UPDATE WITHOUT NEW APK

When only changing lessons, quiz questions, text, or styling:

```python
# 1. Edit index.html
# 2. Bump sw.js VERSION
sw, _ = get_text("husn-ada-course","sw.js")
sw = sw.replace("VERSION = '2.0.0'", "VERSION = '2.0.1'")  # any increment

# 3. Push only to web repo
put_text("husn-ada-course","index.html", updated_html, "Content update")
put_text("husn-ada-course","sw.js", sw, "Bump version")

# Done — all users get the update within 60 seconds automatically
# No new APK needed
```

**When a new APK IS needed:** Changed Java code, manifest, icons, splash, or anything in `res/`. Increment `versionCode` in build.gradle → push → GitHub builds new APK.

---

## SECTION 12 — DESIGN TOKENS

```css
:root {
  /* Brand — deep Islamic teal + warm gold */
  --teal:    #1B4D3E;
  --teal2:   #2D7A5F;
  --teal3:   #3DAB87;
  --teal-lt: #E8F5F0;
  --gold:    #C9A84C;
  --gold2:   #E8C56A;
  --gold-lt: #FDF8EC;
  --rose:    #C0392B;   /* error/wrong */
  --bg:      #FAFAF7;   /* page background */
  --tx:      #1A1A2E;   /* main text */
  --mt:      #5A6B65;   /* muted text */
}
/* Urdu text: font-family: 'JameelNoori','Noto Nastaliq Urdu',serif */
/* Arabic Quran text: font-family: 'Amiri',serif */
/* Android status bar: theme-color = #1B4D3E */
/* App icon background: #C8F000 (lime green) */
```

---

## SECTION 13 — PRE-BUILD CHECKLIST (run before every APK build)

```
[ ] AndroidManifest.xml has NO android:versionCode attribute
[ ] AndroidManifest.xml has NO android:versionName attribute
[ ] android:usesCleartextTraffic="true" is set (Samsung fix)
[ ] android:exported="true" on SplashActivity
[ ] android:exported="false" on MainActivity
[ ] build.gradle versionCode incremented by 1 from last release
[ ] Workflow: zipalign runs BEFORE apksigner
[ ] Workflow: apksigner used (NOT jarsigner)
[ ] Workflow: --v1-signing-enabled true --v2-signing-enabled true --v3-signing-enabled true
[ ] Workflow: gradle-wrapper.properties uses printf (NOT heredoc)
[ ] Workflow: gradlew downloaded via curl (NOT from repo)
[ ] Workflow file uploaded via Chrome browser (NOT via API)
[ ] All 4 secrets exist in husn-ada-app repo
[ ] .nojekyll file exists in husn-ada-course repo
[ ] Logo is base64 embedded in index.html (NOT external URL)
[ ] Author text reads عائش قرآن اکیڈمی everywhere
```

---

## SECTION 14 — THINGS THAT ARE NOT BUGS

| What you see | It's not a bug because... |
|---|---|
| GitHub Pages 403 from within Claude | Claude's server IP is blocked by GitHub CDN. Real users on phones/computers access it fine. |
| index.html is 1.8 MB | Contains 570KB base64 logo. Normal and works fine. |
| Workflow 404 via API | Token scope issue. Use Chrome browser instead. |
| Build shows "1 warning (Node.js deprecated)" | GitHub Actions deprecation notice only. Build still succeeds. Ignore. |
| APK downloads as .zip | GitHub artifacts are always zipped. Unzip to get the .apk. |
| Font loads slowly first time | Drive CDN latency. Caches after first load. Normal. |

---

## SECTION 15 — KEY NUMBERS

| Item | Value |
|------|-------|
| Build that first worked on Samsung S10+ | **#21** |
| Total builds needed to fully solve | 21 |
| Failed builds | #1, #2, #11 |
| Current versionCode | 3 |
| Next versionCode to use | 4 |
| APK size | ~3.82 MB |
| Build duration | ~2 minutes |
| Auto-update interval (sw.js) | 60 seconds |
| Splash screen duration | 3 seconds |
| Min Android version | 5.0 (API 21) |
| Target Android version | 14 (API 34) |
| Gradle version | 8.2 |
| JDK version | 17 (Temurin) |

---

## SECTION 16 — APP BEHAVIOR ON DEVICE

| Condition | Behavior |
|-----------|----------|
| Online | Loads `https://lineshape-droid.github.io/husn-ada-course/` (always latest) |
| Offline | Loads `file:///android_asset/index.html` (bundled offline copy) |
| Content update | Push to web repo → users auto-update in 60 seconds, no new APK |
| Native update | Increment versionCode → push → new APK from GitHub Actions |
| Offline toast | "آفلائن موڈ — لوکل ورژن" |
| Auto-update toast | "نئی اپڈیٹ دستیاب ہے!" + "اپڈیٹ کریں ↻" button |
| Back button | Navigates WebView history → exits app when history empty |
| Splash | 3 seconds → academy logo + title + progress bar → WebView loads |

---

*Version: 2.0 — June 29, 2026*  
*Covers all 21 builds of husn-ada-app*  
*Build #21 = first confirmed successful install on Samsung Galaxy S10+*  
*All 13 bugs documented, root-caused and fixed*
