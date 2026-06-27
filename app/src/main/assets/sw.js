// حسن ادا کورس — Service Worker
// VERSION: 1.0.0  ← bump this number on every release to trigger auto-update
const VERSION = '1.0.0';
const CACHE   = 'husn-ada-v' + VERSION;

const ASSETS = [
  './',
  './index.html',
  './manifest.json',
  './Ayesh-Quran-Academy-LoGo.png',
  './Jameel Noori Nastaleeq Kasheeda.ttf',
  './icons/icon-192.png',
  './icons/icon-512.png',
  'https://fonts.googleapis.com/css2?family=Amiri:ital,wght@0,400;0,700;1,400&display=swap'
];

// ── INSTALL: cache all assets ──────────────────────────────────
self.addEventListener('install', e => {
  console.log('[SW] Installing version', VERSION);
  e.waitUntil(
    caches.open(CACHE).then(c => c.addAll(ASSETS).catch(err => {
      console.warn('[SW] Some assets failed to cache:', err);
    }))
  );
  // Do NOT skipWaiting here — wait for user confirmation via toast
});

// ── ACTIVATE: delete old caches ────────────────────────────────
self.addEventListener('activate', e => {
  console.log('[SW] Activating version', VERSION);
  e.waitUntil(
    caches.keys().then(keys =>
      Promise.all(
        keys.filter(k => k !== CACHE).map(k => {
          console.log('[SW] Deleting old cache:', k);
          return caches.delete(k);
        })
      )
    ).then(() => self.clients.claim())
  );
});

// ── MESSAGE: handle skipWaiting from update toast ───────────────
self.addEventListener('message', e => {
  if (e.data && e.data.type === 'SKIP_WAITING') {
    console.log('[SW] Skipping wait — activating new version');
    self.skipWaiting();
  }
});

// ── FETCH: stale-while-revalidate for HTML, cache-first for assets
self.addEventListener('fetch', e => {
  if (e.request.method !== 'GET') return;

  const url = new URL(e.request.url);

  // For navigation (HTML pages) — network first so updates show immediately
  if (e.request.mode === 'navigate') {
    e.respondWith(
      fetch(e.request)
        .then(res => {
          const clone = res.clone();
          caches.open(CACHE).then(c => c.put(e.request, clone));
          return res;
        })
        .catch(() => caches.match('./index.html'))
    );
    return;
  }

  // For fonts, images, JS, CSS — cache first (fast), update in background
  e.respondWith(
    caches.match(e.request).then(cached => {
      const fetchPromise = fetch(e.request).then(res => {
        if (res && res.status === 200 && res.type !== 'opaque') {
          const clone = res.clone();
          caches.open(CACHE).then(c => c.put(e.request, clone));
        }
        return res;
      }).catch(() => null);

      return cached || fetchPromise;
    })
  );
});
