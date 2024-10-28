package com.example.multipleemaillogin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.multipleemaillogin.Utills.EmailType;
import com.google.android.material.progressindicator.LinearProgressIndicator;

import kotlin.jvm.internal.Intrinsics;

public class WebViewActivity extends AppCompatActivity {

    private WebView webView;
    private LinearProgressIndicator progressIndicator;
    ConstraintLayout constraintLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);

        webView = findViewById(R.id.web_view);
        progressIndicator = findViewById(R.id.spin_kit);
        constraintLayout = findViewById(R.id.container);

        webView.setWebViewClient(new WebViewClient()); // Prevent opening in browser
        WebSettings webSettings = webView.getSettings();

        // Enable JavaScript

        webSettings.setJavaScriptEnabled(true);
        webSettings.setJavaScriptEnabled(true);
        webSettings.setSupportZoom(true);
        webSettings.setDomStorageEnabled(true);
        webSettings.setBuiltInZoomControls(true);
        webSettings.setDisplayZoomControls(false);
        webSettings.setAllowFileAccess(true);
        webSettings.setAllowContentAccess(true);
        webSettings.setLoadWithOverviewMode(true);// Enable JavaScript

        // Get the URL from the Intent
        String url = getIntent().getStringExtra("url");

        webView.setWebViewClient(new WebViewClient() {
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                progressIndicator.setVisibility(View.VISIBLE); // Show loading indicator
                progressIndicator.setIndeterminate(true); // Start indeterminate progress
            }

            public void onPageFinished(WebView view, String url) {
                progressIndicator.setVisibility(View.GONE); // Hide loading indicator
                constraintLayout.setVisibility(View.GONE);
            }

            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                progressIndicator.setVisibility(View.GONE); // Hide loading indicator
                constraintLayout.setVisibility(View.GONE);
            }
        });
        // Load your URL
        webView.loadUrl(url);

    }


    @Override
    public void onBackPressed() {
        if (webView.canGoBack()) {
            webView.goBack(); // Go back in the WebView history
        } else {
            super.onBackPressed(); // Otherwise, use default back navigation
        }
    }
}