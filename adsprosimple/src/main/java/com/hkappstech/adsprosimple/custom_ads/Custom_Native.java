package com.hkappstech.adsprosimple.custom_ads;



import android.annotation.SuppressLint;
import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import androidx.browser.customtabs.CustomTabColorSchemeParams;
import androidx.browser.customtabs.CustomTabsIntent;

import com.hkappstech.adsprosimple.R;

import java.util.Random;

import pl.droidsonroids.gif.GifImageView;

public class Custom_Native {

    @SuppressLint("SetTextI18n")
    public View QurekaNativeAdsDesigns(final Context context) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View inflate;
        inflate = inflater.inflate(R.layout.custom_native, null, false);

        TextView textView = inflate.findViewById(R.id.tv_text_ad_name);
        TextView textView2 = inflate.findViewById(R.id.tv_text_ad_desc);
        GifImageView gifImageView = inflate.findViewById(R.id.iv_round_gif);
        int nextInt = new Random().nextInt(5);
        if (nextInt == 1) {
            textView.setText("Predict Match Results");
            textView2.setText("Win 5,00,000 Coins & More");
            inflate.findViewById(R.id.rl_qurekha).setBackgroundResource(R.drawable.qureka_native1);
            gifImageView.setBackgroundResource(R.drawable.qureka_round1);
        } else if (nextInt == 2) {
            textView.setText("Bollywood Stars Prediction");
            textView2.setText("Win 50,000 Coins With Mobile Games");
            inflate.findViewById(R.id.rl_qurekha).setBackgroundResource(R.drawable.qureka_native2);
            gifImageView.setBackgroundResource(R.drawable.qureka_round2);
        } else if (nextInt == 3) {
            textView.setText("Mega Quiz Games");
            textView2.setText("Win 50,000 Coins No Install Required");
            inflate.findViewById(R.id.rl_qurekha).setBackgroundResource(R.drawable.qureka_native3);
            gifImageView.setBackgroundResource(R.drawable.qureka_round3);
        } else if (nextInt == 4) {
            textView.setText("Bank Exams Quiz");
            textView2.setText("Collect 50,000 Coins Now");
            inflate.findViewById(R.id.rl_qurekha).setBackgroundResource(R.drawable.qureka_native4);
            gifImageView.setBackgroundResource(R.drawable.qureka_round_4);
        } else {
            textView.setText("Celebrity Prediction");
            textView2.setText("Win Coin & No Installation Required");
            inflate.findViewById(R.id.rl_qurekha).setBackgroundResource(R.drawable.qureka_native5);
            gifImageView.setBackgroundResource(R.drawable.qureka_round_5);
        }
        inflate.findViewById(R.id.playNowLL).setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
//                if (qurekalinkarraycount > strQurekaLinkArray.length - 1)
//                    qurekalinkarraycount = 0;
//                openUrlInChromeCustomTab(context, strQurekaLinkArray[qurekalinkarraycount]);

            }
        });
        return inflate;
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