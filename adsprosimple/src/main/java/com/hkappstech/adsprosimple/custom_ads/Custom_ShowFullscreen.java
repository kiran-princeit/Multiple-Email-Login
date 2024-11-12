package com.hkappstech.adsprosimple.custom_ads;



import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.view.KeyEvent;

import androidx.browser.customtabs.CustomTabColorSchemeParams;
import androidx.browser.customtabs.CustomTabsIntent;

import com.hkappstech.adsprosimple.R;


public class Custom_ShowFullscreen {
    final Activity activity;

    final setOnBackPressListener.OnNoRecordFoundListener adsListener1;
    int Theme = R.style.interstitial_theme;

    public Custom_ShowFullscreen(Activity activity, setOnBackPressListener.OnNoRecordFoundListener interstitialAdsListener1) {
        this.activity = activity;
        adsListener1 = interstitialAdsListener1;
    }

    public void onBackPressed() {
        final Custom_Fullscreen interstitialAds = new Custom_Fullscreen(activity, Theme);
        interstitialAds.setCanceledOnTouchOutside(false);
        interstitialAds.setAnimationEnable(false);
        interstitialAds.setOnCloseListener(() -> {
            interstitialAds.dismiss();
            adsListener1.onAdsClose();

        });

        interstitialAds.setOnKeyListener((dialog, keyCode, event) -> {
            if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_UP) {
                interstitialAds.dismiss();
                adsListener1.onAdsClose();
            }
            return false;
        });

        interstitialAds.show();
    }

    public void InterstitialAds() {
        final Custom_Fullscreen interstitialAds = new Custom_Fullscreen(activity, Theme);
        interstitialAds.setCanceledOnTouchOutside(false);
        interstitialAds.setAnimationEnable(true);
        interstitialAds.setOnCloseListener(() -> {
            interstitialAds.dismiss();
            adsListener1.onAdsClose();

//            openUrlInChromeCustomTab(activity, AdsHelperClass.getQurekalink());
        });
        interstitialAds.setOnKeyListener((dialog, keyCode, event) -> {
            if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_UP) {
                interstitialAds.dismiss();
                adsListener1.onAdsClose();

//                openUrlInChromeCustomTab(activity, strQurekaLinkArray[qurekalinkarraycount]);

            }
            return false;
        });
        interstitialAds.show();
    }

    public void openUrlInChromeCustomTab(Context context, String url) {
        try {
            CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder();
            CustomTabColorSchemeParams colorSchemeParams = new CustomTabColorSchemeParams.Builder().build();
            builder.setDefaultColorSchemeParams(colorSchemeParams);
            CustomTabsIntent customTabsIntent = builder.build();
            customTabsIntent.launchUrl(context, Uri.parse(url));

//            qurekalinkarraycount++;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}