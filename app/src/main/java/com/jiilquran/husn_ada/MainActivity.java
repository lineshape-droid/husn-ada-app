package com.jiilquran.husn_ada;

import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.webkit.WebSettings;
import android.webkit.WebChromeClient;
import android.view.KeyEvent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.content.Context;
import android.widget.Toast;

public class MainActivity extends Activity {

    private WebView webView;
    private static final String LOCAL_URL  = "file:///android_asset/index.html";
    private static final String REMOTE_URL = "https://lineshape-droid.github.io/husn-ada-course/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        webView = findViewById(R.id.webView);

        // ── WebView settings ───────────────────────────────────
        WebSettings s = webView.getSettings();
        s.setJavaScriptEnabled(true);
        s.setDomStorageEnabled(true);
        s.setAllowFileAccessFromFileURLs(true);
        s.setAllowUniversalAccessFromFileURLs(true);
        s.setDatabaseEnabled(true);
        s.setLoadWithOverviewMode(true);
        s.setUseWideViewPort(true);
        s.setCacheMode(WebSettings.LOAD_DEFAULT);
        s.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        s.setMediaPlaybackRequiresUserGesture(false);
        s.setBuiltInZoomControls(false);
        s.setDisplayZoomControls(false);

        webView.setWebChromeClient(new WebChromeClient());
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onReceivedError(WebView view, int errorCode,
                    String description, String failingUrl) {
                // If remote fails fall back to local bundled version
                if (failingUrl != null && failingUrl.startsWith("https://")) {
                    view.loadUrl(LOCAL_URL);
                    Toast.makeText(MainActivity.this,
                        "آفلائن موڈ — لوکل ورژن لوڈ ہو رہی ہے",
                        Toast.LENGTH_SHORT).show();
                }
            }
        });

        // Load remote if online (gets latest updates), else local
        if (isOnline()) {
            webView.loadUrl(REMOTE_URL);
        } else {
            webView.loadUrl(LOCAL_URL);
        }
    }

    private boolean isOnline() {
        ConnectivityManager cm =
            (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo ni = cm.getActiveNetworkInfo();
        return ni != null && ni.isConnected();
    }

    // Handle back button inside WebView
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && webView.canGoBack()) {
            webView.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onResume() {
        super.onResume();
        webView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        webView.onPause();
    }
}
