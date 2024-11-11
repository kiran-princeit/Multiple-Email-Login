package com.info.multiple.email.onplace.login;

import static com.info.multiple.email.onplace.login.LoginMultipleAccountActivity.title;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.CookieManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.RelativeLayout;

import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;

import com.google.android.material.progressindicator.LinearProgressIndicator;

public class WebViewActivity extends BaseActivity {
    private WebView webView;
    private LinearProgressIndicator progressIndicator;
    ConstraintLayout constraintLayout;
    AppCompatTextView tvTitle, tvFirstChar;
    AppCompatImageView ivRefresh, ivBack;
    RelativeLayout ivImage;
    LinearLayoutCompat llTopBar;
    private boolean isLogin = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);

        String url = getIntent().getStringExtra("url");
        isLogin = getIntent().getBooleanExtra("isLogin", false);
        int emailColor = getIntent().getIntExtra("emailColor", Color.WHITE);

        webView = findViewById(R.id.web_view);
        tvTitle = findViewById(R.id.tvTitle);
        ivRefresh = findViewById(R.id.ivRefresh);
        tvFirstChar = findViewById(R.id.tvFirstChar);
        progressIndicator = findViewById(R.id.spin_kit);
        constraintLayout = findViewById(R.id.container);
        ivImage = findViewById(R.id.ivImage);
        llTopBar = findViewById(R.id.llTopBar);
        ivBack = findViewById(R.id.ivBack);

        webView.setWebViewClient(new WebViewClient());
        WebSettings webSettings = webView.getSettings();

        llTopBar.setBackgroundColor(emailColor);
        setStatusBarColor(emailColor);

        Drawable background = ContextCompat.getDrawable(WebViewActivity.this, R.drawable.rounded_rectangle2);
        if (background != null) {
            background.setTint(emailColor);
            ivImage.setBackground(background);
        }

        tvTitle.setText(title);
        String capitalizedTitle = capitalizeWords(title.trim());
        tvFirstChar.setText(capitalizedTitle);
        Log.d("Title", title);
        Log.d("CapitalizedTitle", capitalizedTitle);

        if (isLogin) {
            clearWebViewSession();
        }
        clearWebViewSession();


        webSettings.setJavaScriptEnabled(true);
        webSettings.setSupportZoom(true);
        webSettings.setDomStorageEnabled(true);
        webSettings.setBuiltInZoomControls(true);
        webSettings.setDisplayZoomControls(false);
        webSettings.setAllowFileAccess(true);
        webSettings.setAllowContentAccess(true);
        webSettings.setLoadWithOverviewMode(true);// Enable JavaScript
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            WebView.setWebContentsDebuggingEnabled(true);
        }
        String userAgent = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/91.0.4472.124 Safari/537.36";
        webSettings.setUserAgentString(userAgent);

        webView.setWebViewClient(new WebViewClient() {
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                progressIndicator.setVisibility(View.VISIBLE);
                progressIndicator.setIndeterminate(true);
            }

            public void onPageFinished(WebView view, String url) {
                progressIndicator.setVisibility(View.GONE);
                constraintLayout.setVisibility(View.GONE);
            }

            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                progressIndicator.setVisibility(View.GONE);
                constraintLayout.setVisibility(View.GONE);
            }
        });

        webView.loadUrl(url);

        ivRefresh.setOnClickListener(view -> {
            webView.reload();
        });

        ivBack.setOnClickListener(view -> {
            onBackPressed();
        });
    }


    @Override
    public void onBackPressed() {
        if (webView.canGoBack()) {
            webView.goBack();
        } else {
            super.onBackPressed();
        }
    }

    private void clearWebViewSession() {
        CookieManager.getInstance().removeAllCookies(null);
        CookieManager.getInstance().flush();
        webView.clearCache(true);
    }

    private String capitalizeWords(String text) {
        if (text == null || text.isEmpty()) {
            return ""; // Return an empty string if text is null or empty
        }
        String firstLetter = String.valueOf(Character.toUpperCase(text.charAt(0)));
        Log.d("capitalizeWords", "First Letter: " + firstLetter); // Debug log
        return firstLetter;
    }

    private void setStatusBarColor(int color) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(color);
        }
    }
}