package com.hkappstech.adsprosimple;

import android.app.Activity;
import android.content.Context;
import android.view.ViewGroup;

import androidx.annotation.Keep;

import com.hkappstech.adsprosimple.all_ads.allAppopen;
import com.hkappstech.adsprosimple.all_ads.allBanner;
import com.hkappstech.adsprosimple.all_ads.allInterstitial;
import com.hkappstech.adsprosimple.all_ads.allNative;

@Keep
public interface MobileAds {

    interface firebaseonloadcomplete {
        void onloadcomplete();
    }

    static void init(Context context, firebaseonloadcomplete firebasecompte) {
        Firebase_Helper.loadFirebase(context, firebasecompte);
    }

    static void showAppopen(Activity activity, OnActivityResultLauncher1.OnActivityResultLauncher2 resultLauncher) {
        allAppopen.showAppopen(activity, resultLauncher);
    }

    static void showBanner(final ViewGroup BannerContainer, Activity activity) {
        allBanner.showBanner(BannerContainer, activity);
    }

    static void showNative(final ViewGroup nativeContainer, Activity activity) {
        allNative.showNative(nativeContainer, activity);
    }

    static void showInterstitial(Activity activity, OnActivityResultLauncher1.OnActivityResultLauncher2 resultLauncher) {
        allInterstitial.showInterstitial(activity, resultLauncher);
    }
}