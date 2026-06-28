# 🤖 AI Agent Documentation — حسن ادا کورس
### Ayesha Quran Academy (عائش قرآن اکیڈمی)

> This document gives an AI agent complete context to understand, maintain, update, debug, and extend this application without any prior knowledge.

---

## 1. PROJECT OVERVIEW

| Field | Value |
|-------|-------|
| **App Name (Urdu)** | حسن ادا کورس |
| **App Name (English)** | Husn Ada Course |
| **Academy** | عائش قرآن اکیڈمی (Ayesha Quran Academy) |
| **Subject** | Quranic Recitation (Tajweed & Husn-e-Ada) |
| **Language** | Urdu (RTL — Right to Left) |
| **Author** | عائشہ عبدالجبار |
| **Target Users** | Urdu-speaking Muslims learning Quran recitation |
| **Platform** | Android APK + Progressive Web App (PWA) |
| **Architecture** | WebView-based native Android wrapping a PWA |

---

## 2. WHAT THE APP DOES

This is a **13-chapter Islamic education course** teaching beautiful Quran recitation (حسن ادا). Users read each chapter's lesson, then take a 10-question quiz (MCQ + fill-in-the-blank). The app works offline using a bundled HTML file, and auto-updates content when online.

### Content Structure
Each of the 13 chapters (ابواب) follows this flow:
```
Chapter Card (Home) → Lesson Sections → Quiz (10 questions) → Individual Result Page
```

### The 13 Chapters:
1. حسنِ ادا کا تعارف — Introduction to Husn-e-Ada
2. آدابِ تلاوت اور اصلاحِ نیت — Etiquette & Intention
3. تیاری صوت و لہجہ — Voice & Tone Preparation
4. مخارج و صفاتِ حروف — Articulation Points
5. روانی تلاوت اور ادائیگی — Fluent Recitation
6. تنفّس کی مشقیں اور قوتِ سانس — Breathing Exercises
7. اصولِ وقف و ابتداء — Pause & Resumption Rules
8. فنِ مدّات اور جمالِ ادائیگی — Art of Prolongation
9. نغمگی تلاوت اور صوتی لطافت — Melodic Recitation
10. تجویدی مہارت اور صوتی ہم آہنگی — Tajweed Mastery
11. تنوعِ لہجہ اور صوتی انداز — Variety of Style
12. تاثیرِ تلاوت اور جذبہ قرأت — Impact of Recitation
13. عملی مشقیں اور تطبیقی تربیت — Practical Exercises

---

## 3. REPOSITORY STRUCTURE

### Repo 1: `husn-ada-course` (Web / PWA)
**URL:** https://github.com/lineshape-droid/husn-ada-course
**Live:** https://lineshape-droid.github.io/husn-ada-course/

```
husn-ada-course/
├── index.html                    ← MAIN APP (single-file PWA, 1200+ lines)
├── sw.js                         ← Service Worker (offline + auto-update)
├── manifest.json                 ← PWA manifest (icons, name, theme)
├── .nojekyll                     ← Bypasses Jekyll on GitHub Pages
├── README.md
├── Ayesh-Quran-Academy-LoGo.png  ← Horizontal logo (white background)
└── icons/
    ├── icon-72.png  through icon-512.png  ← 9 sizes for all devices
    └── icon-original.png                  ← 1254×1254 source icon
```

### Repo 2: `husn-ada-app` (Android Native)
**URL:** https://github.com/lineshape-droid/husn-ada-app

```
husn-ada-app/
├── .github/workflows/
│   └── build-apk.yml             ← CI/CD: auto-builds & signs APK on push
├── app/
│   ├── build.gradle              ← compileSdk 34, minSdk 21, versionCode 1
│   └── src/main/
│       ├── AndroidManifest.xml   ← package: com.jiilquran.husn_ada
│       ├── assets/               ← Bundled offline HTML files
│       │   ├── index.html        ← Same as web PWA (offline fallback)
│       │   ├── sw.js
│       │   ├── manifest.json
│       │   └── Ayesh-Quran-Academy-LoGo.png
│       ├── java/com/jiilquran/husn_ada/
│       │   ├── SplashActivity.java   ← 3-second splash screen
│       │   └── MainActivity.java     ← WebView loader
│       └── res/
│           ├── drawable/
│           │   ├── splash_bg.xml     ← Gradient background (lavender→pink→mint)
│           │   └── splash_logo.png   ← Academy logo for splash
│           ├── layout/
│           │   ├── activity_splash.xml
│           │   └── activity_main.xml
│           ├── mipmap-*/             ← Launcher icons (mdpi→xxxhdpi)
│           └── values/
│               ├── strings.xml       ← App name, academy name
│               ├── colors.xml        ← Brand colors
│               └── styles.xml        ← AppTheme, SplashTheme
├── build.gradle
├── settings.gradle
├── gradle.properties
├── gradlew / gradlew.bat
└── gradle/wrapper/
    ├── gradle-wrapper.jar
    └── gradle-wrapper.properties     ← Gradle 8.2
```

---

## 4. TECHNICAL STACK

### Android App
| Component | Detail |
|-----------|--------|
| Language | Java |
| Package ID | `com.jiilquran.husn_ada` |
| Min SDK | 21 (Android 5.0 Lollipop) |
| Target SDK | 34 (Android 14) |
| Architecture | Single Activity WebView |
| Signing | jarsigner with RSA 2048-bit keystore |
| Keystore alias | `husn_ada` |
| Build tool | Gradle 8.2 |
| CI/CD | GitHub Actions (ubuntu-latest) |

### Web / PWA
| Component | Detail |
|-----------|--------|
| Framework | Vanilla HTML/CSS/JavaScript (no framework) |
| Fonts | Jameel Noori Nastaleeq Kasheeda (Google Drive TTF) + Amiri (Arabic ayah text) |
| Direction | RTL (`dir="rtl"`, `lang="ur"`) |
| Offline | Service Worker with cache-first strategy |
| Update | Auto-update every 60s + "نئی اپڈیٹ دستیاب ہے!" toast |
| Theme color | `#c8f000` (lime green — matches app icon) |
| PWA Display | standalone |

---

## 5. APP FLOW (USER JOURNEY)

```
App Launch
    │
    ▼
SplashActivity (3 seconds)
  • Gradient background: lavender → pink → mint
  • Academy logo (600×600px)
  • App title: "حسن ادا کورس"
  • Academy: "عائش قرآن اکیڈمی"
  • Animated progress bar
    │
    ▼
MainActivity (WebView)
  • IF online  → loads https://lineshape-droid.github.io/husn-ada-course/
  • IF offline → loads file:///android_asset/index.html
    │
    ▼
HTML App Loads (index.html)
  • 3-second in-app splash (logo + loading bar animation)
  • Sticky navbar with hamburger menu → 13 chapter links
  • Reading progress bar in navbar
    │
    ▼
HOME SCREEN
  • 13 chapter cards in vertical list
  • Each card: coloured banner + chapter icon + number + title + subtitle
  • Completed chapters show score progress bar
  • Tap any card → opens lesson
    │
    ▼
LESSON PAGE (per chapter)
  • Back button → Home
  • Chapter hero banner (unique gradient per chapter)
  • Sections displayed in order:
      1. تعارف (Introduction paragraph)
      2. آیت قرآنی / حدیث (Quran verse or Hadith — in styled box)
      3. اہم نکات (Key points — coloured list items)
      4. درست نیتیں / غلط نیتیں (green/red coded lists where applicable)
      5. تلاوت کے آداب (Etiquette lists)
      6. عملی اصول (Practical rules)
      7. خلاصہ (Summary box — purple/pink gradient)
  • "کوئز شروع کریں" button at bottom
    │
    ▼
QUIZ (10 questions per chapter)
  • One question at a time
  • Dot tracker (green/red dots) at top
  • Question types:
      - MCQ: 4 options (الف ب ج د), tap to answer
      - Fill-in-blank: inline text inputs embedded in sentence
  • Immediate feedback after each answer:
      - Correct: green highlight + ✅ feedback message
      - Wrong: red highlight + shows correct answer + ❌ message
  • "اگلا سوال ➜" button appears after answering
  • "📖 سبق" button always available to go back to lesson
    │
    ▼
RESULT PAGE (individual per chapter)
  • Trophy emoji based on score: 🏆 (100%) 🥇 (≥80%) 🥈 (≥60%) 📚 (<60%)
  • Score percentage in glowing circle
  • Stat chips: درست / غلط / کل سوالات
  • Encouraging message in Urdu
  • Full answer review list (every question + correct answer shown)
  • Buttons: دوبارہ کوئز | سبق دوبارہ | اگلا باب | فہرست
```

---

## 6. UI / UX DESIGN SYSTEM

### Color Palette
```css
/* Primary brand */
--pk:  #FFB3C6   /* Pink light */
--pk2: #FF85A1   /* Pink medium */
--lv:  #C9B8FF   /* Lavender light */
--lv2: #A389E0   /* Lavender medium (primary brand) */
--mn:  #B5EAD7   /* Mint light */
--mn2: #7DCFB6   /* Mint medium */
--pc:  #FFDAB9   /* Peach */
--pc2: #FFAD7D   /* Peach medium */
--sk:  #AEE4FF   /* Sky blue */
--sk2: #72C5F5   /* Sky medium */
--yw:  #FFF3A3   /* Yellow */
--yw2: #FFE066   /* Yellow medium */
--bg:  #FFF9F2   /* Warm cream background */
--text:#3D2B5C   /* Deep purple text */
```

### Chapter Banner Gradients (each chapter has unique color)
| Chapter | Gradient |
|---------|----------|
| 1 | `#FFCCD8 → #FFB3C6` (Pink) |
| 2 | `#B8EDD5 → #C8F5E0` (Mint) |
| 3 | `#CCC0FF → #D8D0FF` (Lavender) |
| 4 | `#FFE880 → #FFF5B0` (Yellow) |
| 5 | `#A8DCFF → #CCF0FF` (Sky) |
| 6 | `#FFCCC0 → #FFD0C8` (Coral) |
| 7 | `#B8F5CC → #D0FFE0` (Green) |
| 8 | `#FFE888 → #FFF4B0` (Gold) |
| 9 | `#F0C0FF → #F8D8FF` (Purple) |
| 10 | `#A8D8FF → #C8ECFF` (Blue) |
| 11 | `#C0F0D8 → #D8FAE8` (Teal) |
| 12 | `#FFBCC0 → #FFD4D8` (Rose) |
| 13 | `#D0C8FF → #E4DCFF` (Violet) |

### Typography
| Element | Font | Size | Weight |
|---------|------|------|--------|
| Body text | Jameel Noori Nastaleeq Kasheeda | 17px | 400 |
| Arabic verses | Amiri | 1.5–1.6rem | 400/700 |
| Section headings | Jameel Noori | 1.12rem | 700 |
| Chapter titles | Jameel Noori | 1.3–1.85rem | 700 |
| Navbar | Jameel Noori | 1.05rem | 700 |
| List items | Jameel Noori | 0.97rem | 400 |

### Line Heights
- Body: 2.6
- Paragraphs: 3.0
- List items: 2.6
- Info boxes: 3.0
- Arabic text: 3.4–3.6
- Hadith text: 3.2

### Border Radius
- Cards / major containers: 28px
- Section blocks: 20px
- List items: 14px
- Buttons: 50px (pill shape)
- Navbar: 10px for icons

### Spacing
- Page padding: 0 14px 60px (mobile) / 0 20px 80px (desktop)
- Card gap: 22px
- Section gap inside lesson: 14–28px
- List item padding: 15px 18px

### Animations
| Element | Animation |
|---------|-----------|
| Chapter cards | Fade up + scale from 0.98 on scroll (IntersectionObserver) |
| List items | Stagger fade up with 60ms delay per item |
| Splash logo | `splashPop` — scale from 0.5 to 1 with overshoot |
| Splash bar | `splashProgress` — fills from 0% to 100% over 2.6s |
| Cover logo | `logoFloat` — gentle 4s float up/down |
| Navbar scroll | Shadow deepens on scroll |
| Hamburger | 3 lines animate to × on open |
| Quiz cards | `qIn` — scale + translateY on appear |
| Feedback | `fbIn` — scale from 0.95 to 1 |
| Scroll-to-top | Fades in after 320px scroll |
| Emoji burst | Pops up and fades when answering questions |

### Responsive Breakpoints
- Mobile: ≤ 600px (reduced padding, stacked table rows)
- Desktop: max-width 900px centered
- Touch targets: minimum 44px (Android standard)
- Safe area: `env(safe-area-inset-*)` for notch phones

---

## 7. COMPONENT LIBRARY (HTML)

### Section Block Types Inside Lessons
```html
<!-- Introduction paragraph -->
<div class="sec-block ib-lav">
  <h3><span class="sec-icon">📌</span>Heading</h3>
  <p>Body text...</p>
</div>

<!-- Quran Ayah box (purple gradient) -->
<div class="ayah-block">
  <div class="ayah-ar">Arabic text</div>
  <div class="ayah-ref">Reference</div>
  <div class="ayah-tr">Urdu translation</div>
</div>

<!-- Hadith box (yellow gradient) -->
<div class="hadith-block">
  <div class="had-text">Arabic hadith</div>
  <div class="had-ref">Source</div>
  <div class="had-tr">Urdu translation</div>
</div>

<!-- Point list — variations -->
<ul class="pt-list list-ok">   <!-- green = correct/good things -->
<ul class="pt-list list-no">   <!-- red = wrong/bad things -->
<ul class="pt-list list-steps"><!-- blue = sequential steps -->
<ul class="pt-list list-warn"> <!-- yellow = warnings/cautions -->
<ul class="pt-list list-key">  <!-- lavender = key concepts -->

<!-- Info box types -->
<div class="info-box ib-lav">  <!-- lavender border-right -->
<div class="info-box ib-mint"> <!-- mint border-right -->
<div class="info-box ib-yw">   <!-- yellow border-right -->
<div class="info-box ib-pk">   <!-- pink border-right -->
<div class="info-box ib-sk">   <!-- sky border-right -->

<!-- Two-column definition table -->
<div class="md-table">
  <div class="mdt-row">
    <div class="mdt-label">Term</div>
    <div class="mdt-val">Definition</div>
  </div>
</div>

<!-- Summary box -->
<div class="khalasa">
  <div class="khalasa-tag">📌 خلاصہ</div>
  <p>Summary text...</p>
</div>
```

### Quiz Question Types
```javascript
// MCQ question object
{
  type: 'mcq',
  q: 'Question text in Urdu',
  opts: ['Option A', 'Option B', 'Option C', 'Option D'],
  ans: 1,           // index of correct answer (0-3)
  fb: 'Feedback message shown after answering'
}

// Fill-in-the-blank question object
{
  type: 'fill',
  q: 'Sentence with ___ blanks ___ here',
  blanks: ['first answer', 'second answer'],
  fb: 'Feedback message shown after checking'
}
```

---

## 8. AUTO-UPDATE SYSTEM

### How It Works
1. Service Worker (`sw.js`) checks for updates every **60 seconds**
2. When a new version is detected, a toast appears at bottom of screen:
   - **"نئی اپڈیٹ دستیاب ہے!"** with **"اپڈیٹ کریں ↻"** button
3. If user ignores it, auto-updates after **8 seconds**
4. Page reloads to apply the new content

### To Release an Update
```
1. Make changes to index.html / sw.js
2. Increment VERSION in sw.js: '1.0.0' → '1.0.1'
3. Push to GitHub
4. GitHub Pages auto-deploys (1-2 min)
5. All users get the update within 60 seconds of opening app
6. Android APK users get content update automatically (no new APK needed)
7. For native Android changes → push to husn-ada-app → GitHub Actions builds new APK
```

### Service Worker Cache Strategy
- **HTML pages** (navigation): Network-first → cache fallback
- **Assets** (images, fonts, JS): Cache-first → network update in background
- **Offline fallback**: Returns `./index.html` from cache

---

## 9. BUILD & DEPLOYMENT

### Web App (GitHub Pages)
```bash
# Automatic on every push to main branch
git push origin main
# → GitHub Pages deploys automatically
# → Live at: https://lineshape-droid.github.io/husn-ada-course/
```

### Android APK (GitHub Actions)
```
Every push to husn-ada-app/main triggers:
  1. Checkout code
  2. JDK 17 (Temurin)
  3. Android SDK setup
  4. Download Gradle wrapper (v8.2)
  5. ./gradlew assembleRelease
  6. jarsigner signs APK with stored keystore
  7. Upload artifact: husn-ada-SIGNED

Download APK:
  GitHub → husn-ada-app → Actions → Latest run → husn-ada-SIGNED
```

### GitHub Secrets (signing)
| Secret | Value |
|--------|-------|
| `SIGNING_KEY` | Base64-encoded keystore file |
| `KEY_ALIAS` | `husn_ada` |
| `KEY_STORE_PASSWORD` | `HusnAda2024` |
| `KEY_PASSWORD` | `HusnAda2024` |

---

## 10. ANDROID APP DETAILS

### SplashActivity
- Launched as MAIN/LAUNCHER activity
- Full screen, no action bar
- Background: gradient drawable (lavender → pink → mint)
- Shows: logo (240dp) + app title + academy name + indeterminate progress bar
- Duration: 3000ms then starts MainActivity
- Theme: `SplashTheme` (windowFullscreen = true)

### MainActivity
- Loads WebView full-screen
- **Online behavior**: loads remote URL `https://lineshape-droid.github.io/husn-ada-course/`
  - Always gets latest content automatically
  - No new APK needed for content updates
- **Offline behavior**: loads `file:///android_asset/index.html`
  - Shows toast: "آفلائن موڈ — لوکل ورژن"
- Back button: `webView.goBack()` if history exists
- WebView settings: JavaScript ✅, DOM Storage ✅, Hardware Accelerated ✅
- RTL support: `android:supportsRtl="true"`

### App Properties
```
applicationId:  com.jiilquran.husn_ada
versionCode:    1
versionName:    1.0.0
minSdk:         21  (Android 5.0+, covers ~97% of devices)
targetSdk:      34  (Android 14)
Orientation:    Portrait only (locked)
```

---

## 11. BRANDING ASSETS

| Asset | Location | Size | Usage |
|-------|----------|------|-------|
| Horizontal logo (white BG) | `Ayesh-Quran-Academy-LoGo.png` | 427 KB | Cover page, navbar |
| App icon (green rounded square) | `icons/icon-*.png` | 9 sizes | Launcher, PWA |
| Original icon source | `icons/icon-original.png` | 1166 KB | Source file |
| Splash logo | `res/drawable/splash_logo.png` | 69 KB | Android splash |

### Logo Colors
- Primary: Dark navy/slate `#2C3E50`
- Accent: Lime green `#C8F000`
- Background: White (horizontal logo) / Lime green (icon)

---

## 12. NAVBAR STRUCTURE

```
[📖 Logo] [حسن ادا]              [☰ Hamburger]
─────────────────── progress bar ───────────────────

Drawer (slides from right):
  ┌─────────────────────────────┐
  │ ابواب کی فہرست          [✕] │
  │─────────────────────────────│
  │ [1] حسنِ ادا کا تعارف      │
  │ [2] آدابِ تلاوت            │
  │ [3] تیاری صوت و لہجہ       │
  │ [4] مخارج و صفاتِ حروف     │
  │ [5] روانی تلاوت            │
  │ [6] تنفّس کی مشقیں         │
  │ [7] اصولِ وقف و ابتداء     │
  │ [8] فنِ مدّات              │
  │ [9] نغمگی تلاوت            │
  │ [10] تجویدی مہارت          │
  │ [11] تنوعِ لہجہ            │
  │ [12] تاثیرِ تلاوت          │
  │ [13] عملی مشقیں            │
  └─────────────────────────────┘
```

---

## 13. GLOBAL STATE (JavaScript)

```javascript
// Active chapter (null = home screen)
let curCh = null;        // chapter index 0-12

// Quiz state
let curQ = 0;            // current question index
let answered = false;    // has user answered current question
let qResults = [];       // array of booleans [true, false, ...]

// Scores
let chScores = {};       // {chId: {ok, no, pct}} — persists per session
let gOk = 0;             // global correct count
let gNo = 0;             // global wrong count
let gTt = 0;             // global total questions answered

// Current question reference (avoids Urdu in onclick attributes)
let _Q = null;           // set before rendering each question
```

---

## 14. KEY FUNCTIONS (JavaScript)

| Function | Purpose |
|----------|---------|
| `buildHome()` | Renders chapter card list on home screen |
| `openLesson(id)` | Opens lesson page for chapter `id` (0-12) |
| `buildSection(s, si, hdrG)` | Renders a single lesson section block |
| `startQuiz()` | Resets quiz state and shows first question |
| `showQ()` | Renders current question (sets `_Q` global) |
| `buildMCQ(q)` | Renders 4-option MCQ buttons |
| `buildFill(q)` | Renders fill-in-the-blank input fields |
| `checkMCQ(chosen)` | Validates MCQ answer, shows feedback |
| `checkFill()` | Validates fill answers, shows feedback |
| `showFB(ok, fb)` | Shows green/red feedback box |
| `recordResult(ok)` | Updates scores, triggers emoji burst |
| `addNextBtn()` | Appends "next question" button after 350ms |
| `showResult()` | Renders per-chapter result page |
| `goHome()` | Returns to chapter list, rebuilds home |
| `setProg(done, total, lbl)` | Updates navbar progress bar |
| `burst(emoji)` | Shows floating emoji animation |
| `toggleMenu()` | Opens/closes navbar drawer |
| `onScroll()` | Handles scroll events (progress, back-to-top) |

---

## 15. HOW TO ADD A NEW CHAPTER

1. Open `index.html` in the `husn-ada-course` repo
2. Find the `CHS` array in the `<script>` tag
3. Add a new chapter object following this template:
```javascript
{
  id: 13,            // next available id (0-indexed)
  icon: '🌙',        // chapter icon emoji
  num: 'باب ۱۴',    // Urdu chapter number
  title: 'Chapter Title in Urdu',
  sub: 'Short subtitle describing the chapter',
  side: 's1',        // CSS class for banner gradient (s1-s7, reuse)
  hdrG: 'linear-gradient(135deg,#FFCCD8,#FFB3C6)', // hero gradient
  sections: [
    { type:'intro', icon:'📌', color:'#FF85A1',
      heading:'Section Heading',
      text:'Paragraph text...'
    },
    { type:'ayah',
      ar:'Arabic text',
      ref:'Surah: Ayah ref',
      tr:'Urdu translation'
    },
    { type:'list', icon:'✅', color:'#7DCFB6', bdr:'#B5EAD7',
      heading:'List Heading',
      items:[
        {i:'✅', t:'Item text', cls:'right-item'},
      ]
    },
  ],
  quiz: [
    { type:'mcq',
      q:'Question text',
      opts:['A','B','C','D'],
      ans:1,           // correct index
      fb:'Feedback text'
    },
    { type:'fill',
      q:'Sentence with ___ blank',
      blanks:['answer'],
      fb:'Feedback text'
    },
    // ... 10 total
  ]
}
```
4. Also add a new navbar link in `build-apk.yml`'s HTML:
```html
<a href="#bab-14" class="nb-link nb-c1" onclick="toggleMenu()">
  <span class="nb-lnum">14</span><span>Chapter Name</span>
</a>
```
5. Push to GitHub → auto-deploys

---

## 16. HOW TO UPDATE CONTENT

### Update Text/Content (No New APK Needed)
1. Edit `index.html` in `husn-ada-course` repo
2. Bump `VERSION` in `sw.js` (e.g. `'1.0.0'` → `'1.0.1'`)
3. Push to `husn-ada-course` main branch
4. GitHub Pages deploys in ~2 minutes
5. All users auto-update within 60 seconds

### Update Native Android UI (New APK Needed)
1. Edit Java/XML files in `husn-ada-app` repo
2. Push to main branch
3. GitHub Actions auto-builds signed APK
4. Download from Actions → Artifacts → `husn-ada-SIGNED`

---

## 17. COMMON TASKS FOR AI AGENT

### Add a quiz question
→ Edit the `quiz` array in the relevant chapter in `CHS[]` in `index.html`

### Change a chapter's color theme
→ Edit `side`, `hdrG` fields in the chapter object

### Fix a typo in lesson text
→ Edit the `sections[].text` or `sections[].items[].t` in the chapter object

### Change app name
→ Edit `strings.xml` in Android repo + `manifest.json` title in web repo

### Add new section type to a lesson
→ Add to `sections[]` array, implement rendering in `buildSection()` function

### Change quiz pass percentage
→ Edit thresholds in `showResult()` function: `pct>=80`, `pct>=60` etc.

### Update academy logo
→ Replace `Ayesh-Quran-Academy-LoGo.png` in web repo + rebuild base64 embed in `index.html` + push

### Fix broken font
→ Font is loaded via `@font-face` from Google Drive URL in `index.html` CSS

---

## 18. KNOWN ISSUES & NOTES

| Issue | Status | Note |
|-------|--------|------|
| Font from Google Drive | Active | TTF loaded via Drive URL; may be slow on first load |
| Logo in HTML | Fixed | Embedded as base64 data URI — no external dependency |
| Unsigned APK | Fixed | jarsigner signs APK in CI/CD pipeline |
| GitHub Pages 403 from CI | Known | Only affects automated tests; real users access fine |
| `.nojekyll` required | Applied | Without it, GitHub Pages returns 403 on HTML files |

---

## 19. CONTACT & OWNERSHIP

| Field | Value |
|-------|-------|
| GitHub Account | lineshape-droid |
| Email | lineshape@gmail.com |
| Academy | عائش قرآن اکیڈمی |
| Author | عائشہ عبدالجبار |
| Web Repo | https://github.com/lineshape-droid/husn-ada-course |
| Android Repo | https://github.com/lineshape-droid/husn-ada-app |
| Live Web App | https://lineshape-droid.github.io/husn-ada-course/ |

---

*This document was generated for AI agent context. Last updated: June 2026.*
