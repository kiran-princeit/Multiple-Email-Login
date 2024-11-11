package com.info.multiple.email.onplace.login;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.SystemClock;
import android.widget.TextView;

import com.info.multiple.email.onplace.login.Utills.ContextKt;
import com.info.multiple.email.onplace.login.Utills.LanguagePreference;

public class SettingActivity extends BaseActivity {
    private long mLastClickTime = 0;

    TextView tvLanguageName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        inItView();

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
            Uri uri = Uri.parse("http://www.google.com");
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            startActivity(intent);

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


        findViewById(R.id.llFeedback).setOnClickListener(view -> {
            ContextKt.b(SettingActivity.this, -1.0f);

        });
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

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}