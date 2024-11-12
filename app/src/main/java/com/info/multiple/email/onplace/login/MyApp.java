package com.info.multiple.email.onplace.login;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.StrictMode;

import androidx.multidex.MultiDex;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.info.multiple.email.onplace.login.Utills.LanguagePreference;
import com.info.multiple.email.onplace.login.Utills.LocaleHelper;

public class MyApp extends Application {
    private static SharedPreferences sharedPreferences;
    FirebaseAnalytics mFirebaseAnalytics;
    private static MyApp mInstance;
    private static final String CONSTANT_FIRSTTIME = "constant_firsttime";

    public void onCreate(){
        super.onCreate();

        String languageCode = LanguagePreference.getLanguage(this);
        LocaleHelper.setLocale(this, languageCode);

        mInstance = this;

        this.mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
        MultiDex.install(this);
        StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder().build());

    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(LocaleHelper.onAttach(base));
    }

    public synchronized boolean isRunningFistTime() {
        return sharedPreferences.getBoolean(CONSTANT_FIRSTTIME, false);
    }

    public synchronized void setRunningFistTime(boolean value) {
        sharedPreferences.edit().putBoolean(CONSTANT_FIRSTTIME, value).apply();
    }
}
