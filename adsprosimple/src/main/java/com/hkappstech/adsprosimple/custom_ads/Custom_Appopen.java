//package com.hkappstech.adsprosimple.custom_ads;
//
//import static com.hkappstech.adsprosimple.GlobalVar.appData;
////import static com.hkappstech.adsprosimple.GlobalVar.qurekalinkarraycount;
////import static com.hkappstech.adsprosimple.GlobalVar.strQurekaLinkArray;
//
//import android.app.Activity;
//import android.app.Dialog;
//import android.content.Context;
//import android.content.DialogInterface;
//import android.graphics.drawable.ColorDrawable;
//import android.net.Uri;
//import android.view.KeyEvent;
//import android.view.View;
//import android.widget.ImageView;
//import android.widget.LinearLayout;
//import android.widget.RelativeLayout;
//import android.widget.TextView;
//
//import androidx.browser.customtabs.CustomTabColorSchemeParams;
//import androidx.browser.customtabs.CustomTabsIntent;
//
//import com.hkappstech.adsprosimple.MainApplication;
//import com.hkappstech.adsprosimple.OnActivityResultLauncher1;
//import com.hkappstech.adsprosimple.R;
//
//import java.util.Random;
//
//import pl.droidsonroids.gif.GifImageView;
//
//public class Custom_Appopen
//{
//    static Dialog appopendialog;
//    static LinearLayout ll_continue;
//    static RelativeLayout rl_qureka;
//
//    public static void loadAppOpenAd(Activity act, OnActivityResultLauncher1.OnActivityResultLauncher2 resultLauncher) {
//        if (appData.getshowOpenad() == 1) {
//            if (!MainApplication.isNetworkConnected(act)) {
//                return;
//            }
//
//            try {
////                if (qurekalinkarraycount > strQurekaLinkArray.length - 1)
////                    qurekalinkarraycount = 0;
//
//                Dialog dialog = new Dialog(act);
//                appopendialog = dialog;
//                dialog.requestWindowFeature(1);
//                appopendialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
//                appopendialog.setContentView(R.layout.custom_appopen);
//                appopendialog.getWindow().setLayout(-1, -2);
//                appopendialog.setCancelable(false);
//                appopendialog.show();
//                GifImageView gifImageView = appopendialog.findViewById(R.id.gif_app_open);
//                TextView textView = appopendialog.findViewById(R.id.tv_header_text);
//                ImageView imageView = appopendialog.findViewById(R.id.iv_qureka_img);
//                int nextInt = new Random().nextInt(3);
//                if (nextInt == 1) {
//                    textView.setText("Guess And Win Coins");
//                    imageView.setBackgroundResource(R.drawable.qureka_open2);
//                    gifImageView.setBackgroundResource(R.drawable.qureka_round2);
//                } else if (nextInt == 2) {
//                    textView.setText("Play Cricket Quiz");
//                    imageView.setBackgroundResource(R.drawable.qureka_open3);
//                    gifImageView.setBackgroundResource(R.drawable.qureka_round3);
//                } else {
//                    textView.setText("Mega Prediction Contest");
//                    imageView.setBackgroundResource(R.drawable.qureka_open5);
//                    gifImageView.setBackgroundResource(R.drawable.qureka_round_5);
//                }
//                appopendialog.findViewById(R.id.btn_play_now1).setOnClickListener(new View.OnClickListener() {
//                    public void onClick(View view) {
//                        resultLauncher.onLauncher();
//                        appopendialog.dismiss();
//
//                        openUrlInChromeCustomTab(act, strQurekaLinkArray[qurekalinkarraycount]);
//                    }
//                });
//                appopendialog.findViewById(R.id.btn_play_now).setOnClickListener(new View.OnClickListener() {
//                    public void onClick(View view) {
//                        resultLauncher.onLauncher();
//                        appopendialog.dismiss();
//
//                        openUrlInChromeCustomTab(act, strQurekaLinkArray[qurekalinkarraycount]);
//                    }
//                });
//                ll_continue = appopendialog.findViewById(R.id.ll_continue);
//                rl_qureka = appopendialog.findViewById(R.id.rl_qureka);
//                ll_continue.setOnClickListener(new View.OnClickListener() {
//                    public void onClick(View view) {
//                        appopendialog.dismiss();
//                        resultLauncher.onLauncher();
//                        openUrlInChromeCustomTab(act, strQurekaLinkArray[qurekalinkarraycount]);
//
//                    }
//                });
//                rl_qureka.setOnClickListener(new View.OnClickListener() {
//                    public void onClick(View view) {
//                        resultLauncher.onLauncher();
//
//                        openUrlInChromeCustomTab(act, strQurekaLinkArray[qurekalinkarraycount]);
//                    }
//                });
//                appopendialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
//                    public boolean onKey(DialogInterface dialogInterface, int i, KeyEvent keyEvent) {
//                        if (i != 4) {
//                            return true;
//                        }
//                        appopendialog.dismiss();
//                        resultLauncher.onLauncher();
//
//                        openUrlInChromeCustomTab(act, strQurekaLinkArray[qurekalinkarraycount]);
//
//                        return true;
//                    }
//                });
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//
//        } else {
//            resultLauncher.onLauncher();
//        }
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
//
//}