package com.info.multiple.email.onplace.login;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.info.multiple.email.onplace.login.Utills.LanguagePreference;

public class SplashActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

//        new Handler().postDelayed(() -> {
//
//            if (LanguagePreference.isFirstTime(this)) {
//                // Launch LanguageActivity for the first-time language selection
//                Intent intent = new Intent(this, LanguageSelectionActivity.class);
//                startActivity(intent);
//            } else {
//                // Launch MainActivity if language is already set
//                Intent intent = new Intent(this, MainActivity.class);
//                startActivity(intent);
//            }
//            finish(); // Close splash screen
//
//        }, 2000);

        new Handler().postDelayed(() -> {

            if (LanguagePreference.isFirstTime(this)) {
                // Launch LanguageSelectionActivity for the first-time language selection
                Intent intent = new Intent(this, LanguageSelectionActivity.class);
                startActivity(intent);
                // Set the language selection as completed
                LanguagePreference.setFirstTime(this, false);
            } else if (!LanguagePreference.isOnboardingShown(this)) {
                // Launch OnboardingActivity if it hasn't been shown yet
                Intent intent = new Intent(this, ContinueActivity.class);
                startActivity(intent);
                // Set the onboarding as shown
                LanguagePreference.setOnboardingShown(this, true);
            } else {
                // Launch MainActivity if both LanguageSelection and Onboarding are completed
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
            }
            finish(); // Close splash screen

        }, 2000);



    }
}