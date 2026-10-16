package com.raz.smoketracker;

import android.Manifest;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class MainActivity extends Activity {
    private WebView web;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Alerts.ensureChannel(this);

        web = new WebView(this);
        web.setBackgroundColor(0xFF0A0A0A);
        setContentView(web);

        WebSettings s = web.getSettings();
        s.setJavaScriptEnabled(true);
        s.setDomStorageEnabled(true);
        web.setWebViewClient(new WebViewClient());
        web.addJavascriptInterface(new Bridge(), "Native");
        web.loadUrl("file:///android_asset/index.html");

        if (Build.VERSION.SDK_INT >= 33
                && checkSelfPermission(Manifest.permission.POST_NOTIFICATIONS)
                != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.POST_NOTIFICATIONS}, 1);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        web.onResume();
    }

    @Override
    protected void onPause() {
        web.onPause();
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        web.destroy();
        super.onDestroy();
    }

    /** Called from the page as window.Native. */
    private class Bridge {
        @JavascriptInterface
        public void schedule(double dueAtMillis) {
            Alerts.schedule(getApplicationContext(), (long) dueAtMillis);
        }

        @JavascriptInterface
        public void cancel() {
            Alerts.cancel(getApplicationContext());
        }

        @JavascriptInterface
        public void test() {
            Alerts.notifyNow(getApplicationContext(), true);
        }

        @JavascriptInterface
        public String status() {
            return Alerts.status(getApplicationContext());
        }

        @JavascriptInterface
        public void fix() {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    openFixScreen();
                }
            });
        }
    }

    private void openFixScreen() {
        String pkg = getPackageName();
        Intent i;
        if (Build.VERSION.SDK_INT >= 31 && "exact".equals(Alerts.status(this))) {
            i = new Intent(Settings.ACTION_REQUEST_SCHEDULE_EXACT_ALARM,
                    Uri.parse("package:" + pkg));
        } else if (Build.VERSION.SDK_INT >= 26) {
            i = new Intent(Settings.ACTION_APP_NOTIFICATION_SETTINGS);
            i.putExtra(Settings.EXTRA_APP_PACKAGE, pkg);
        } else {
            i = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                    Uri.parse("package:" + pkg));
        }
        try {
            startActivity(i);
        } catch (ActivityNotFoundException e) {
            // Nothing to open on this device; the page still shows the warning.
        }
    }
}
