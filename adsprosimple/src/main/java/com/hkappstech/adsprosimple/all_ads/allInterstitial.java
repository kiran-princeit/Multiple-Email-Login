package com.hkappstech.adsprosimple.all_ads;

import static com.hkappstech.adsprosimple.GlobalVar.appData;
//import static com.hkappstech.adsprosimple.GlobalVar.qurekalinkarraycount;
//import static com.hkappstech.adsprosimple.GlobalVar.strQurekaLinkArray;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.CountDownTimer;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.browser.customtabs.CustomTabColorSchemeParams;
import androidx.browser.customtabs.CustomTabsIntent;

import com.facebook.ads.Ad;
import com.facebook.ads.InterstitialAdListener;
import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;
import com.hkappstech.adsprosimple.MainApplication;
import com.hkappstech.adsprosimple.OnActivityResultLauncher1;
import com.hkappstech.adsprosimple.R;
import com.hkappstech.adsprosimple.RemoteAppDataModel;
import com.hkappstech.adsprosimple.custom_ads.Custom_Fullscreen;

public class allInterstitial {

    public static long interstitial_timer;
    public static Dialog loadAdsDialog = null;
    public static int interstitialcount = 0;

    public static void showInterstitial(Activity activity, OnActivityResultLauncher1.OnActivityResultLauncher2 resultLauncher) {

        if (!MainApplication.isNetworkConnected(activity)) {
            doNext(activity, resultLauncher);
            return;
        }

        if (appData.getshowInterstitial() != 1) {
            doNext(activity, resultLauncher);
            return;
        }

        if (interstitial_timer > 0) {
            doNext(activity, resultLauncher);
            return;
        }

        if (RemoteAppDataModel.adspersession == appData.getAds_per_session()) {
            doNext(activity, resultLauncher);
            return;
        }

        if (appData.getintertime() == 0) {
            interstitialcount++;
            if (interstitialcount % appData.getinterclickcount() != 0) {
                doNext(activity, resultLauncher);
                return;
            }
        }

        switch (appData.getadstype()) {
            case "facebook":
                fbinterstitial(activity, resultLauncher);
                break;

            case "admob":
                aminterstitial(activity, resultLauncher);
                break;

//            case "custom":
//                custominterstitial(activity, resultLauncher);
//                break;

            default:
                doNext(activity, resultLauncher);
                break;
        }

    }

    static com.facebook.ads.InterstitialAd FBInterstitialAd;

    public static void fbinterstitial(Activity activity, OnActivityResultLauncher1.OnActivityResultLauncher2 resultLauncher) {
        String adUnitId = appData.getfbInterstitialid();

        if (TextUtils.isEmpty(adUnitId)) {
            return;
        }

        loadAdsDialog = new Dialog(activity);
        loadAdsDialog.setContentView(R.layout.layout_loading);
        loadAdsDialog.setCanceledOnTouchOutside(false);
        loadAdsDialog.setCancelable(false);
        loadAdsDialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        loadAdsDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        loadAdsDialog.getWindow().getAttributes().windowAnimations = android.R.style.Animation_Dialog;
        loadAdsDialog.show();

//            if (fbfullarraycount > strFBFullscreen.length - 1)
//                fbfullarraycount = 0;

//            FBInterstitialAd = new com.facebook.ads.InterstitialAd(activity, strFBFullscreen[fbfullarraycount]);
        FBInterstitialAd = new com.facebook.ads.InterstitialAd(activity, adUnitId);

        InterstitialAdListener interstitialAdListener = new InterstitialAdListener() {

            @Override
            public void onInterstitialDisplayed(Ad ad) {
            }

            @Override
            public void onInterstitialDismissed(Ad ad) {
//                    fbfullarraycount++;

                new CountDownTimer(appData.getintertime() * 1000L, 1000) {
                    public void onTick(long millisUntilFinished) {

                        interstitial_timer = millisUntilFinished / 1000;
//                            Log.e("PACK1_ADMOB_TAG", "seconds remaining: " + interstitial_timer);

                    }

                    public void onFinish() {
                        interstitial_timer = 0;
//                            Log.e("PACK1_ADMOB_TAG", "done!");
                    }
                }.start();

                doNext(activity, resultLauncher);
                RemoteAppDataModel.isShowingFullScreenAd = false;
            }

            @Override
            public void onError(Ad ad, com.facebook.ads.AdError adError) {
                // Handle the error
                FBInterstitialAd = null;
                if (loadAdsDialog != null && loadAdsDialog.isShowing()) {
                    loadAdsDialog.dismiss();
                }
                doNext(activity, resultLauncher);

                new CountDownTimer(appData.getintertime() * 1000L, 1000) {
                    public void onTick(long millisUntilFinished) {

                        interstitial_timer = millisUntilFinished / 1000;
//                            Log.e("PACK1_ADMOB_TAG", "seconds remaining: " + interstitial_timer);

                    }

                    public void onFinish() {
                        interstitial_timer = 0;
//                            Log.e("PACK1_ADMOB_TAG", "done!");
                    }
                }.start();
            }

            @Override
            public void onAdLoaded(Ad ad) {

                if (loadAdsDialog != null && loadAdsDialog.isShowing()) {
                    loadAdsDialog.dismiss();
                }

                if (RemoteAppDataModel.adspersession != appData.getAds_per_session()) {
                    if (FBInterstitialAd != null) {
                        FBInterstitialAd.show();
                        RemoteAppDataModel.isShowingFullScreenAd = true;
                        RemoteAppDataModel.adspersession++;
                    } else {

                        doNext(activity, resultLauncher);
//                            loadInterstitialAdResultLaunch(activity, countClicks, resultLauncher);
                        RemoteAppDataModel.isShowingFullScreenAd = false;
                        return;
                    }
                } else {
                    doNext(activity, resultLauncher);
                }
            }

            @Override
            public void onAdClicked(Ad ad) {
            }

            @Override
            public void onLoggingImpression(Ad ad) {
            }
        };

//            AdSettings.addTestDevice("HASHED ID");
        FBInterstitialAd.loadAd(FBInterstitialAd.buildLoadAdConfig().withAdListener(interstitialAdListener).build());
    }

    public static InterstitialAd amInterstitialAd = null;

    public static void aminterstitial(Activity activity, OnActivityResultLauncher1.OnActivityResultLauncher2 resultLauncher) {
        String adUnitId = appData.getamInterstitialid();

        Log.e("PACK1_ADMOB_TAG", "Admob InterstitialAd ==>" + appData.getamInterstitialid());
        if (TextUtils.isEmpty(adUnitId)) {
            return;
        }
        loadAdsDialog = new Dialog(activity);
        loadAdsDialog.setContentView(R.layout.layout_loading);
        loadAdsDialog.setCanceledOnTouchOutside(false);
        loadAdsDialog.setCancelable(false);
        loadAdsDialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        loadAdsDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        loadAdsDialog.getWindow().getAttributes().windowAnimations = android.R.style.Animation_Dialog;
        loadAdsDialog.show();

        AdRequest adRequest = new AdRequest.Builder().build();

        InterstitialAd.load(activity, adUnitId, adRequest, new InterstitialAdLoadCallback() {
            @Override
            public void onAdLoaded(@NonNull InterstitialAd interstitialAd) {

                amInterstitialAd = interstitialAd;

                Log.e("PACK1_ADMOB_TAG", "InterstitialAd ==> onAdLoaded");
                if (loadAdsDialog != null && loadAdsDialog.isShowing()) {
                    loadAdsDialog.dismiss();
                }

                displayAdMobInterstitialAdResultLauncher(activity, resultLauncher);

                super.onAdLoaded(interstitialAd);
            }

            @Override
            public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                // Handle the error
                Log.e("PACK1_ADMOB_TAG", "InterstitialAd ==> " + loadAdError);
                amInterstitialAd = null;
                if (loadAdsDialog != null && loadAdsDialog.isShowing()) {
                    loadAdsDialog.dismiss();
                }
                doNext(activity, resultLauncher);
                super.onAdFailedToLoad(loadAdError);
            }
        });
    }

    public static void displayAdMobInterstitialAdResultLauncher(Activity activity, OnActivityResultLauncher1.OnActivityResultLauncher2 resultLauncher) {
        if (RemoteAppDataModel.adspersession != appData.getAds_per_session()) {
            if (amInterstitialAd != null) {
//                Log.e("PACK1_ADMOB_TAG", "Admob InterstitialAd ==> " + "Showed");
                amInterstitialAd.show(activity);
                RemoteAppDataModel.isShowingFullScreenAd = true;
                RemoteAppDataModel.adspersession++;
            } else {
                doNext(activity, resultLauncher);
//                loadInterstitialAdResultLaunch(activity, countClicks, resultLauncher);
                RemoteAppDataModel.isShowingFullScreenAd = false;
                return;
            }
        } else {
            doNext(activity, resultLauncher);
        }

        if (amInterstitialAd != null) {

            amInterstitialAd.setFullScreenContentCallback(new FullScreenContentCallback() {
                @Override
                public void onAdDismissedFullScreenContent() {
                    super.onAdDismissedFullScreenContent();
                    // Called when fullscreen content is dismissed.
                    Log.e("PACK1_ADMOB_TAG", "Admob InterstitialAd ==> The ad was dismissed.");

                    new CountDownTimer(appData.getintertime() * 1000L, 1000) {
                        public void onTick(long millisUntilFinished) {

                            interstitial_timer = millisUntilFinished / 1000;
                            Log.e("PACK1_ADMOB_TAG", "seconds remaining: " + interstitial_timer);

                        }

                        public void onFinish() {
                            interstitial_timer = 0;
                            Log.e("PACK1_ADMOB_TAG", "done!");
                        }
                    }.start();

                    doNext(activity, resultLauncher);
                    RemoteAppDataModel.isShowingFullScreenAd = false;
                }

                @Override
                public void onAdFailedToShowFullScreenContent(@NonNull AdError adError) {
                    super.onAdFailedToShowFullScreenContent(adError);
//                    Log.e("PACK1_ADMOB_TAG", "Admob InterstitialAd ==> The ad failed to show.");
                }

                @Override
                public void onAdShowedFullScreenContent() {
                    super.onAdShowedFullScreenContent();
                    amInterstitialAd = null;
//                    Log.e("PACK1_ADMOB_TAG", "Admob InterstitialAd ==> The ad was shown.");
                }
            });
        }
    }
//
//    public static void custominterstitial(Activity activity, OnActivityResultLauncher1.OnActivityResultLauncher2 resultLauncher) {
//        final Custom_Fullscreen interstitialAds = new Custom_Fullscreen(activity, R.style.interstitial_theme);
//        interstitialAds.setCanceledOnTouchOutside(false);
//        interstitialAds.setAnimationEnable(true);
//        interstitialAds.setOnCloseListener(() -> {
//            interstitialAds.dismiss();
//
//            doNext(activity, resultLauncher);
//
////            adsListener1.onAdsClose();
////            openUrlInChromeCustomTab(activity, AdsHelperClass.getQurekalink());
//        });
//
//        interstitialAds.setOnKeyListener((dialog, keyCode, event) -> {
//            if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_UP) {
//                interstitialAds.dismiss();
//
//                doNext(activity, resultLauncher);
//
////                adsListener1.onAdsClose();
//                openUrlInChromeCustomTab(activity, strQurekaLinkArray[qurekalinkarraycount]);
//
//            }
//            return false;
//        });
//        interstitialAds.show();
//    }

//    public static void openUrlInChromeCustomTab(Context context, String url) {
//        try {
//            CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder();
//            CustomTabColorSchemeParams colorSchemeParams = new CustomTabColorSchemeParams.Builder().build();
//            builder.setDefaultColorSchemeParams(colorSchemeParams);
//            CustomTabsIntent customTabsIntent = builder.build();
//            customTabsIntent.launchUrl(context, Uri.parse(url));
//
//            qurekalinkarraycount++;
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }

    public static void doNext(Activity activity, OnActivityResultLauncher1.OnActivityResultLauncher2 resultLauncher) {
        resultLauncher.onLauncher();
    }
}