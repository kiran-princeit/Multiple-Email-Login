package com.info.multiple.email.onplace.login;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.info.multiple.email.onplace.login.Utills.LanguagePreference;
import com.info.multiple.email.onplace.login.Utills.LocaleHelper;
import com.info.multiple.email.onplace.login.adapter.LanguageAdapter;
import com.info.multiple.email.onplace.login.adsprosimple.MobileAds;
import com.info.multiple.email.onplace.login.model.Language;

import java.util.ArrayList;
import java.util.List;

public class LanguageSelectionActivity extends BaseActivity {
    private LanguageAdapter languageAdapter;
    private FrameLayout adContainer;
    private ShimmerFrameLayout shimmerFrameLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_language_selection);

        adContainer = findViewById(R.id.ad_container);
        shimmerFrameLayout = findViewById(R.id.shimmer_layout);
        loadAd();

        List<Language> languages = new ArrayList<>();
        languages.add(new Language("en", "English", R.drawable.us));
        languages.add(new Language("es", "Spanish", R.drawable.spain));
        languages.add(new Language("de", "German", R.drawable.germany));
        languages.add(new Language("zh", "Chinese", R.drawable.china));
        languages.add(new Language("fr", "French", R.drawable.french));
        languages.add(new Language("hi", "Hindi", R.drawable.india));
        languages.add(new Language("it", "Italian", R.drawable.italy));
        languages.add(new Language("ja", "Japanese", R.drawable.japan));
        languages.add(new Language("ru", "Russian", R.drawable.russian));

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

                    MobileAds.showInterstitial(LanguageSelectionActivity.this, () -> {
                        Intent intent = new Intent(LanguageSelectionActivity.this, ContinueActivity.class);
                        startActivity(intent);
                        finish();
                    });


                } else {
                    Toast.makeText(LanguageSelectionActivity.this, "Please select a language", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }

    private void loadAd() {
        if (adContainer != null && !MyApp.isNetworkConnected(LanguageSelectionActivity.this)) {
            adContainer.setVisibility(View.GONE);
            return;
        }
        MobileAds.showBanner(adContainer, shimmerFrameLayout, LanguageSelectionActivity.this);
    }


}