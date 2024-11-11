package com.info.multiple.email.onplace.login;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.info.multiple.email.onplace.login.Utills.LanguagePreference;
import com.info.multiple.email.onplace.login.Utills.LocaleHelper;
import com.info.multiple.adapter.LanguageAdapter;
import com.info.multiple.email.onplace.login.model.Language;

import java.util.ArrayList;
import java.util.List;

public class LanguageSelectionActivity extends BaseActivity {
    private LanguageAdapter languageAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_language_selection);

        List<Language> languages = new ArrayList<>();
        languages.add(new Language("en", "English"));
        languages.add(new Language("es", "Spanish"));
        languages.add(new Language("de", "German"));
        languages.add(new Language("zh", "Chinese"));
        languages.add(new Language("fr", "French"));
        languages.add(new Language("hi", "Hindi"));
        languages.add(new Language("it", "Italian"));
        languages.add(new Language("ja", "Japanese"));
        languages.add(new Language("ru", "Russian"));

        RecyclerView recyclerView = findViewById(R.id.rvLanguages);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        languageAdapter = new LanguageAdapter(languages);
        recyclerView.setAdapter(languageAdapter);


        Button continueButton = findViewById(R.id.ivDone);
        continueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String selectedLanguageCode = languageAdapter.getSelectedLanguageCode();
                if (selectedLanguageCode != null && !selectedLanguageCode.isEmpty()) {
                    // Save selected language
                    LanguagePreference.saveLanguage(LanguageSelectionActivity.this, selectedLanguageCode);
                    LocaleHelper.setLocale(LanguageSelectionActivity.this, selectedLanguageCode);
                    LanguagePreference.setFirstTime(LanguageSelectionActivity.this, false);

                    Intent intent;
                    if (!LanguagePreference.isOnboardingShown(LanguageSelectionActivity.this)) {
                        // If onboarding hasn't been shown, launch OnboardingActivity
                        intent = new Intent(LanguageSelectionActivity.this, ContinueActivity.class);
                    } else {
                        // If onboarding was already shown, proceed to MainActivity
                        intent = new Intent(LanguageSelectionActivity.this, MainActivity.class);
                    }

                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(LanguageSelectionActivity.this, "Please select a language", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}