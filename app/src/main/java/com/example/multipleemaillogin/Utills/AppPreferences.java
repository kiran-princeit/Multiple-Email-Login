package com.example.multipleemaillogin.Utills;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public final class AppPreferences {

    private final SharedPreferences preferences;
    public static final String EVENT_TYPE_KEY = "type";
    public static final String PREF_NAME = "MultipleEmailLogin";
    public static final String DISPLAY_EMAIL_ACCOUNT_KEY = "displayEmailAccount";
    public static final String SELECTED_LANGUAGE_KEY = "selectedLanguage";

    public AppPreferences(Context context) {
        if (context == null) throw new IllegalArgumentException("Context cannot be null");
        this.preferences = context.getSharedPreferences("MultipleEmailLogin", Context.MODE_PRIVATE);
    }

    public void addEmailAccount(String email) {
        if (email == null) throw new IllegalArgumentException(EVENT_TYPE_KEY + " cannot be null");

        ArrayList<String> emailList = new ArrayList<>(getEmailAccounts());
        if (!emailList.contains(email)) {
            emailList.add(email);
        }
        saveEmailAccounts(emailList);
    }

    public List<String> getEmailAccounts() {
        String storedEmails = preferences.getString("displayEmailAccount", "");
        if (storedEmails == null || storedEmails.isEmpty()) {
            return Collections.emptyList();
        }

        if (storedEmails.contains(",")) {
            return Arrays.asList(storedEmails.split(","));
        }
        return Collections.singletonList(storedEmails);
    }

    public String getSelectedLanguage() {
        String language = preferences.getString(SELECTED_LANGUAGE_KEY, "en");
        return language != null ? language : "en";
    }

    public void saveEmailAccounts(ArrayList<String> emailList) {
        if (emailList == null) throw new IllegalArgumentException("emailList cannot be null");

        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("displayEmailAccount", TextUtils.join(",", emailList)).apply();
    }
}
