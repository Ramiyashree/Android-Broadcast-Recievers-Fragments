package com.example.cs478_project3_app1;

import android.os.Bundle;
import android.util.Log;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;

public class ShowWebPageActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webpage);
        // Retrieve the URL
        String url = getIntent().getStringExtra("ShowURL");
         //Set up the WebView
        WebView TvWebView = findViewById(R.id.TvWebView);
        TvWebView.getSettings().setLoadsImagesAutomatically(true);
        TvWebView.getSettings().setJavaScriptEnabled(true);
        TvWebView.getSettings().setLoadWithOverviewMode(true);
        TvWebView.getSettings().setBuiltInZoomControls(true);
        TvWebView.setWebViewClient(new WebViewClient());
        TvWebView.loadUrl(url);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i("ShowWebPageActivity", "OnDestroyed");
    }
}
