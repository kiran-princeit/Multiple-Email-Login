package com.info.multiple.email.onplace.login;


import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.info.multiple.email.onplace.login.Utills.LanguagePreference;
import com.info.multiple.email.onplace.login.adsprosimple.GlobalVar;
import com.info.multiple.email.onplace.login.adsprosimple.MobileAds;

public class SettingActivity extends BaseActivity {
    private long mLastClickTime = 0;
    TextView tvLanguageName;
    private FrameLayout adContainer;
    private ShimmerFrameLayout shimmerFrameLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        adContainer = findViewById(R.id.ad_container);
        shimmerFrameLayout = findViewById(R.id.shimmer_layout);
        loadAd();
        inItView();
    }
    private void loadAd() {
        if (adContainer != null && !MyApp.isNetworkConnected(SettingActivity.this)) {
            adContainer.setVisibility(View.GONE);
            return;
        }
        MobileAds.showNativeBig(adContainer, shimmerFrameLayout, SettingActivity.this);
    }

    private void inItView() {

        tvLanguageName = findViewById(R.id.tvLanguageName);

        String selectedLanguageCode = LanguagePreference.getLanguage(SettingActivity.this);
        String languageName = getLanguageName(selectedLanguageCode);
        tvLanguageName.setText(languageName);

        findViewById(R.id.llLanguage).setOnClickListener(view -> {
            startActivity(new Intent(SettingActivity.this, LanguageSelectionActivity.class));

        });
        findViewById(R.id.ivBack).setOnClickListener(view -> {
            onBackPressed();

        });
        findViewById(R.id.llPrivacyPolicy).setOnClickListener(view -> {
            String privacyUrl = GlobalVar.appData.getprivacyurl();
            openPrivacyPolicy(privacyUrl);

        });


        findViewById(R.id.llRateUs).setOnClickListener(view -> {
            final String appName = getPackageName();
            try {
                startActivity(new Intent(Intent.ACTION_VIEW,
                        Uri.parse("market://details?id="
                                + appName)));
            } catch (android.content.ActivityNotFoundException anfe) {
                startActivity(new Intent(
                        Intent.ACTION_VIEW,
                        Uri.parse("http://play.google.com/store/apps/details?id="
                                + appName)));
            }
        });

        findViewById(R.id.llShare).setOnClickListener(view -> {
            if (SystemClock.elapsedRealtime() - mLastClickTime < 1000) {
                return;
            }
            mLastClickTime = SystemClock.elapsedRealtime();
            Intent ishare = new Intent(Intent.ACTION_SEND);
            ishare.setType("text/plain");
            ishare.putExtra(Intent.EXTRA_TEXT, getResources().getString(R.string.app_name) + " - http://play.google.com/store/apps/details?id=" + getPackageName());
            startActivity(ishare);
        });

        findViewById(R.id.llAboutApp).setOnClickListener(view -> {
            infoDialog();
        });
    }

    private void openPrivacyPolicy(String url) {
        if (url != null && !url.isEmpty()) {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            startActivity(intent);
        } else {
            Toast.makeText(this, "URL is null or empty", Toast.LENGTH_SHORT).show();
        }
    }

    private String getLanguageName(String languageCode) {
        switch (languageCode) {
            case "en":
                return "English";
            case "es":
                return "Spanish";
            case "de":
                return "German";
            case "zh":
                return "Chinese";
            case "fr":
                return "French";
            case "hi":
                return "Hindi";
            case "it":
                return "Italian";
            case "ja":
                return "Japanese";
            case "ru":
                return "Russian";

            default:
                return "English"; // Default language
        }
    }


    private void infoDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(SettingActivity.this);
        LayoutInflater inflater = getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.dialog_about, null);
        builder.setView(dialogView);

        AlertDialog infodialog = builder.create();
        infodialog.setCancelable(false);
        infodialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));


        TextView txt_app_version = dialogView.findViewById(R.id.txt_app_version);
        Button tv_ok = dialogView.findViewById(R.id.btnAbout);

        try {
            // Get package manager
            PackageManager packageManager = getPackageManager();
            // Get package info (version info)
            PackageInfo packageInfo = packageManager.getPackageInfo(getPackageName(), 0);

            // Get the app version name
            String versionName = packageInfo.versionName;
            // Get the app version code (if needed)
            int versionCode = packageInfo.versionCode;

            // Display the version name or version code
            Log.d("AppVersion", "Version Name: " + versionName);
            Log.d("AppVersion", "Version Code: " + versionCode);
            txt_app_version.setText(versionName);

        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }


        tv_ok.setOnClickListener(view -> {
            infodialog.cancel();
        });


        Window window = infodialog.getWindow();
        if (window != null) {
            // Set the desired width (in pixels or dp)
            int width = (int) (getResources().getDisplayMetrics().widthPixels * 0.85); // 85% of screen width
            window.setLayout(width, WindowManager.LayoutParams.WRAP_CONTENT);
            window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        }
        infodialog.show();

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}