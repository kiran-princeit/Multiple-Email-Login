package com.hkappstech.adsprosimple.custom_ads;




import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.widget.TextView;

import androidx.browser.customtabs.CustomTabColorSchemeParams;
import androidx.browser.customtabs.CustomTabsIntent;

import com.hkappstech.adsprosimple.R;
import com.hkappstech.adsprosimple.custom_ads.Dialog.LibAnimationLoader;
import com.hkappstech.adsprosimple.custom_ads.Dialog.LibDisplayUtil;

import java.util.Random;

import pl.droidsonroids.gif.GifImageView;

public class Custom_Fullscreen extends Dialog {
    private AnimationSet mAnimIn, mAnimOut;
    private boolean mIsShowAnim;

    private View mDialogView;
    public Context mContext;

    Display display;
    int width;
    int height;

    private InterstitialAdsListener.OnCloseListener mOnPositiveListener;

    public Custom_Fullscreen(Context context) {
        this(context, R.style.interstitial_theme);
        mContext = context;

        Activity activity = (Activity) mContext;
        display = activity.getWindowManager().getDefaultDisplay();
        width = display.getWidth();
        height = display.getHeight();
    }

    public Custom_Fullscreen(Context context, int theme) {
        super(context, theme);
        mContext = context;

        Activity activity = (Activity) mContext;
        display = activity.getWindowManager().getDefaultDisplay();
        width = display.getWidth();
        height = display.getHeight();
        init();

    }

    private void init() {
        mAnimIn = LibAnimationLoader.getInAnimation(getContext());
        mAnimOut = LibAnimationLoader.getOutAnimation(getContext());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
    }

    @SuppressLint("SetTextI18n")
    private void initView() {
        View contentView = View.inflate(getContext(), R.layout.custom_interstitial_layout, null);
        setContentView(contentView);

        resizeDialog();
        initAnimListener();

//        if (qurekalinkarraycount > strQurekaLinkArray.length - 1)
//            qurekalinkarraycount = 0;

        mDialogView = getWindow().getDecorView().findViewById(android.R.id.content);

        GifImageView gifImageView = contentView.findViewById(R.id.gif_inter_round);
        TextView textView = contentView.findViewById(R.id.tv_text_ad_name);
        TextView textView2 = contentView.findViewById(R.id.tv_text_ad_desc);
        int nextInt = new Random().nextInt(5);
        if (nextInt == 1) {
            textView.setText("Question Prediction Contest");
            textView2.setText("Win 50,000 Coins No Install Required");
            gifImageView.setBackgroundResource(R.drawable.qureka_round3);
            contentView.findViewById(R.id.qurekaAds).setBackgroundResource(R.drawable.qureka_inter1);
            contentView.findViewById(R.id.qurekaAds1).setBackgroundResource(R.drawable.qureka_inter1);
        } else if (nextInt == 2) {
            textView.setText("Movie Prediction Contest");
            textView2.setText("Win 50,000 Coins No Install Required");
            gifImageView.setBackgroundResource(R.drawable.qureka_round3);
            contentView.findViewById(R.id.qurekaAds).setBackgroundResource(R.drawable.qureka_inter2);
            contentView.findViewById(R.id.qurekaAds1).setBackgroundResource(R.drawable.qureka_inter2);
        } else if (nextInt == 3) {
            textView.setText("Fantasy Cricket Contest");
            textView2.setText("Win 50,000 Coins With Mobile Games");
            gifImageView.setBackgroundResource(R.drawable.qureka_round2);
            contentView.findViewById(R.id.qurekaAds).setBackgroundResource(R.drawable.qureka_inter3);
            contentView.findViewById(R.id.qurekaAds1).setBackgroundResource(R.drawable.qureka_inter3);
        } else if (nextInt == 4) {
            textView.setText("Social Prediction od Celeb");
            textView2.setText("Win 5,00,000 Coins & More");
            gifImageView.setBackgroundResource(R.drawable.qureka_round_4);
            contentView.findViewById(R.id.qurekaAds).setBackgroundResource(R.drawable.qureka_inter4);
            contentView.findViewById(R.id.qurekaAds1).setBackgroundResource(R.drawable.qureka_inter4);
        } else {
            textView.setText("Bollywood Actress Prediction");
            textView2.setText("Win Coin & No Installation Required");
            gifImageView.setBackgroundResource(R.drawable.qureka_round_5);
            contentView.findViewById(R.id.qurekaAds).setBackgroundResource(R.drawable.qureka_inter5);
            contentView.findViewById(R.id.qurekaAds1).setBackgroundResource(R.drawable.qureka_inter5);
        }
        contentView.findViewById(R.id.qurekaAds).setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                try {
                    if (mOnPositiveListener != null) {
                        mOnPositiveListener.onAdsClose();
                    }

//                    openUrlInChromeCustomTab(getContext(), strQurekaLinkArray[qurekalinkarraycount]);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        contentView.findViewById(R.id.ll_qureka).setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                try {
                    if (mOnPositiveListener != null) {
                        mOnPositiveListener.onAdsClose();
                    }

//                    openUrlInChromeCustomTab(getContext(), strQurekaLinkArray[qurekalinkarraycount]);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        contentView.findViewById(R.id.qurekaAdsClose).setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if (mOnPositiveListener != null) {
                    mOnPositiveListener.onAdsClose();
                }

//                openUrlInChromeCustomTab(getContext(), strQurekaLinkArray[qurekalinkarraycount]);
            }
        });
    }

    private void resizeDialog() {
        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.width = LibDisplayUtil.getScreenSize(getContext()).x;
        params.height = LibDisplayUtil.getScreenSize(getContext()).y;
//        getWindow().setAttributes(params);
        getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.MATCH_PARENT);
    }

    @Override
    public void dismiss() {
        dismissWithAnimation(mIsShowAnim);
    }

    private void startWithAnimation(boolean showInAnimation) {
        if (showInAnimation) {
            mDialogView.startAnimation(mAnimIn);
        }
    }

    private void dismissWithAnimation(boolean showOutAnimation) {
        if (showOutAnimation) {
            try {
                mDialogView.startAnimation(mAnimOut);
            } catch (Exception e) {
                super.dismiss();
            }

        } else {
            super.dismiss();
        }
    }

    public void setAnimationEnable(boolean enable) {
        mIsShowAnim = enable;
    }

    @Override
    protected void onStart() {
        super.onStart();
        startWithAnimation(mIsShowAnim);
    }

    private void initAnimListener() {
        mAnimOut.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                mDialogView.post(() -> callDismiss());
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });
    }

    private void callDismiss() {
        super.dismiss();
//        System.exit(0);
    }

    public Custom_Fullscreen setAnimationIn(AnimationSet animIn) {
        mAnimIn = animIn;
        return this;
    }

    public Custom_Fullscreen setAnimationOut(AnimationSet animOut) {
        mAnimOut = animOut;
        initAnimListener();
        return this;
    }

    public void setOnCloseListener(InterstitialAdsListener.OnCloseListener l) {
        mOnPositiveListener = l;
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