package com.hkappstech.adsprosimple.all_ads;

import static com.hkappstech.adsprosimple.GlobalVar.appData;
//import static com.hkappstech.adsprosimple.GlobalVar.qurekalinkarraycount;
//import static com.hkappstech.adsprosimple.GlobalVar.strQurekaLinkArray;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Rect;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowMetrics;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.browser.customtabs.CustomTabColorSchemeParams;
import androidx.browser.customtabs.CustomTabsIntent;

import com.facebook.ads.Ad;
import com.facebook.ads.AdError;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.LoadAdError;
import com.hkappstech.adsprosimple.MainApplication;
import com.hkappstech.adsprosimple.R;

import java.util.Random;

import pl.droidsonroids.gif.GifImageView;

public class allBanner {

    public static void showBanner(final ViewGroup BannerContainer, Activity activity) {

        if (!MainApplication.isNetworkConnected(activity)) {
            BannerContainer.setVisibility(View.GONE);
            return;
        }

        if (appData.getshowBanner() == 1) {
            switch (appData.getadstype()) {
                 case "facebook":
                    fbBannerAds(activity, BannerContainer);
                    break;

                case "admob":
                    amBannerAds(activity, BannerContainer);
                    break;
//
//                case "custom":
//                    customBannerAds(activity, BannerContainer);
//                    break;

                default:
                    BannerContainer.setVisibility(View.GONE);
                    break;
            }
        } else {
            BannerContainer.setVisibility(View.GONE);
        }
    }

    public static void fbBannerAds(final Activity context, final ViewGroup BannerContainer) {
        try {
            BannerContainer.setVisibility(View.VISIBLE);

            final com.facebook.ads.AdView adView = new com.facebook.ads.AdView(context, appData.getfbBannerid(), com.facebook.ads.AdSize.BANNER_HEIGHT_50);

            com.facebook.ads.AdListener adlistner = new com.facebook.ads.AdListener() {
                @Override
                public void onError(Ad ad, AdError adError) {
                    BannerContainer.removeAllViews();
                    BannerContainer.setVisibility(View.INVISIBLE);

                }

                @Override
                public void onAdLoaded(Ad ad) {
                    try {
                        BannerContainer.setPadding(3, 3, 3, 3);
//                            BannerContainer.setBackgroundColor(Color.parseColor("#FFC107"));
                        BannerContainer.removeAllViews();
                        BannerContainer.addView(adView);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onAdClicked(Ad ad) {

                }

                @Override
                public void onLoggingImpression(Ad ad) {

                }
            };

            // Request an ad
            adView.loadAd(adView.buildLoadAdConfig().withAdListener(adlistner).build());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void amBannerAds(Activity activity, final ViewGroup adContainerView) {
        String adUnitId = appData.getamBannerid();

        if (TextUtils.isEmpty(adUnitId)) {
            return;
        }

        //BannerAd
        AdView admobManagerAdView = new AdView(activity);
        admobManagerAdView.setAdUnitId(adUnitId);
        adContainerView.addView(admobManagerAdView);
        AdRequest adRequest = new AdRequest.Builder().build();
        AdSize adSize = getAdSize(adContainerView, activity);
        admobManagerAdView.setAdSize(adSize);
        admobManagerAdView.loadAd(adRequest);

        admobManagerAdView.setAdListener(new AdListener() {

            @Override
            public void onAdLoaded() {
                adContainerView.setVisibility(View.VISIBLE);

                Log.e("BannerAd", "BannerAd ==> onAdLoaded");
                super.onAdLoaded();

            }

            @Override
            public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                adContainerView.setVisibility(View.GONE);
                Log.e("BannerAd", "BannerAd ==> onAdFailedToLoad " + loadAdError);

                super.onAdFailedToLoad(loadAdError);
            }
        });

    }

    @SuppressLint("NewApi")
    public static AdSize getAdSize(ViewGroup adContainerView, Activity activity) {
        WindowMetrics windowMetrics = activity.getWindowManager().getCurrentWindowMetrics();
        Rect bounds = windowMetrics.getBounds();

        float adWidthPixels = adContainerView.getWidth();

        // If the ad hasn't been laid out, default to the full screen width.
        if (adWidthPixels == 0f) {
            adWidthPixels = bounds.width();
        }

        float density = activity.getResources().getDisplayMetrics().density;
        int adWidth = (int) (adWidthPixels / density);

        return AdSize.getCurrentOrientationAnchoredAdaptiveBannerAdSize(activity, adWidth);
    }

//    @SuppressLint("SetTextI18n")
//    public static View customBannerAds(Activity activity, final ViewGroup adContainerView) {
//        LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//        View inflate;
//        inflate = inflater.inflate(R.layout.custom_banner, null, false);
//
//        TextView textView = inflate.findViewById(R.id.tv_text_ad_name);
//        TextView textView2 = inflate.findViewById(R.id.tv_text_ad_desc);
//        GifImageView gifImageView = inflate.findViewById(R.id.iv_round_gif);
//        int nextInt = new Random().nextInt(5);
//        if (nextInt == 1) {
//            textView.setText("Play & Win Coins");
//            textView2.setText("Win 5,00,000 Coins & More");
//            gifImageView.setBackgroundResource(R.drawable.qureka_round1);
//        } else if (nextInt == 2) {
//            textView.setText("Play & Win Coins");
//            textView2.setText("Win 50,000 Coins With Mobile Games");
//            gifImageView.setBackgroundResource(R.drawable.qureka_round2);
//        } else if (nextInt == 3) {
//            textView.setText("Play Cricket Result");
//            textView2.setText("Win 50,000 Coins No Install Required");
//            gifImageView.setBackgroundResource(R.drawable.qureka_round3);
//        } else if (nextInt == 4) {
//            textView.setText("Predict Cricket Results");
//            textView2.setText("Collect 50,000 Coins Now");
//            gifImageView.setBackgroundResource(R.drawable.qureka_round_4);
//        } else {
//            textView.setText("Predict Result Contest");
//            textView2.setText("Win Coin & No Installation Required");
//            gifImageView.setBackgroundResource(R.drawable.qureka_round_5);
//        }
//        inflate.findViewById(R.id.rlBanner).setOnClickListener(new View.OnClickListener() {
//            public void onClick(View view) {
//
//                if (qurekalinkarraycount > strQurekaLinkArray.length - 1)
//                    qurekalinkarraycount = 0;
//
//                openUrlInChromeCustomTab(activity, strQurekaLinkArray[qurekalinkarraycount]);
//
//            }
//        });
//
//        adContainerView.addView(inflate);
//
//        return inflate;
//    }
//
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

}