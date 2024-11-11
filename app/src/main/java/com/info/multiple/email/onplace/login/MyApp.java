package com.info.multiple.email.onplace.login;

import android.app.Application;
import android.content.Context;

import com.info.multiple.email.onplace.login.Utills.LanguagePreference;
import com.info.multiple.email.onplace.login.Utills.LocaleHelper;

public class MyApp extends Application {
    public void onCreate(){
        super.onCreate();

        String languageCode = LanguagePreference.getLanguage(this);
        LocaleHelper.setLocale(this, languageCode);

    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(LocaleHelper.onAttach(base));
    }
}
