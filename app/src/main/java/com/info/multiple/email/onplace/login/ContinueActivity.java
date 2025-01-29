package com.info.multiple.email.onplace.login;



import androidx.appcompat.widget.AppCompatButton;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.info.multiple.email.onplace.login.Utills.LanguagePreference;
import com.info.multiple.email.onplace.login.adapter.OnboardingAdapter;
import com.info.multiple.email.onplace.login.adsprosimple.GlobalVar;
import com.info.multiple.email.onplace.login.model.OnboardingItem;

import java.util.ArrayList;
import java.util.List;

public class ContinueActivity extends BaseActivity {
    private ViewPager2 viewPager;
    private OnboardingAdapter onboardingAdapter;
    private LinearLayout indicatorLayout;
    private int[] indicators;
    AppCompatButton ivContinue;
    TextView tvPrivacy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_continue);

        TextView skipButton = findViewById(R.id.skip);
        ivContinue = findViewById(R.id.ivContinue);
        tvPrivacy = findViewById(R.id.tvPrivacy);
        viewPager = findViewById(R.id.viewPager);
        indicatorLayout = findViewById(R.id.indicatorLayout);

        tvPrivacy.setPaintFlags(Paint.UNDERLINE_TEXT_FLAG);


       tvPrivacy.setOnClickListener(view -> {
            String privacyUrl = GlobalVar.appData.getprivacyurl();
            openPrivacyPolicy(privacyUrl);

        });

        ivContinue.setOnClickListener(v -> {
            LanguagePreference.setOnboardingShown(ContinueActivity.this, true);
            startActivity(new Intent(ContinueActivity.this, MainActivity.class));
            finish();
        });

        skipButton.setOnClickListener(v -> {
            LanguagePreference.setOnboardingShown(ContinueActivity.this, true);
            startActivity(new Intent(ContinueActivity.this, MainActivity.class));
            finish();
        });

        List<OnboardingItem> onboardingItems = new ArrayList<>();
        onboardingItems.add(new OnboardingItem(R.drawable.image1, getResources().getString(R.string.text1), getResources().getString(R.string.des1)));
        onboardingItems.add(new OnboardingItem(R.drawable.image2, getResources().getString(R.string.text2), getResources().getString(R.string.des2)));

        onboardingAdapter = new OnboardingAdapter(onboardingItems);
        viewPager.setAdapter(onboardingAdapter);

        setupIndicators(onboardingItems.size());
        setCurrentIndicator(0);

        viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                setCurrentIndicator(position);
                if (position == onboardingItems.size() - 1) {
                    ivContinue.setVisibility(View.VISIBLE);
                } else {
                    ivContinue.setVisibility(View.GONE);
                }
            }
        });
    }

    private void setupIndicators(int count) {
        indicators = new int[count];
        for (int i = 0; i < count; i++) {
            indicators[i] = i;
            ImageView imageView = new ImageView(this);
            imageView.setImageDrawable(getDrawable(R.drawable.indicator_inactive));
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            layoutParams.setMargins(10, 0, 10, 0);
            indicatorLayout.addView(imageView, layoutParams);
        }
    }
    private void openPrivacyPolicy(String url) {
        if (url != null && !url.isEmpty()) {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            startActivity(intent);
        } else {
            Toast.makeText(this, "URL is null or empty", Toast.LENGTH_SHORT).show();
        }
    }
    private void setCurrentIndicator(int index) {
        int childCount = indicatorLayout.getChildCount();
        for (int i = 0; i < childCount; i++) {
            ImageView imageView = (ImageView) indicatorLayout.getChildAt(i);
            if (i == index) {
                imageView.setImageDrawable(getDrawable(R.drawable.indicator_active));
            } else {
                imageView.setImageDrawable(getDrawable(R.drawable.indicator_inactive));
            }
        }
    }
}