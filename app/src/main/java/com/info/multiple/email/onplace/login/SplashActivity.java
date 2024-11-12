package com.info.multiple.email.onplace.login;

import static com.hkappstech.adsprosimple.MainApplication.isNetworkConnected;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatTextView;

import com.hkappstech.adsprosimple.MobileAds;
import com.hkappstech.adsprosimple.OnActivityResultLauncher1;
import com.info.multiple.email.onplace.login.Utills.LanguagePreference;
import com.info.multiple.email.onplace.login.model.EmailData;

public class SplashActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        MobileAds.init(this, new MobileAds.firebaseonloadcomplete() {
            @Override
            public void onloadcomplete() {
                Log.e("data", "SplashActivity firebase data complete!");

                MobileAds.showAppopen(SplashActivity.this, new OnActivityResultLauncher1.OnActivityResultLauncher2() {
                    @Override
                    public void onLauncher() {
                        moveNext();
                    }
                });
            }
        });
    }

    private void moveNext() {
        if (isNetworkConnected(SplashActivity.this)) {

            new Handler().postDelayed(() -> {
                if (LanguagePreference.isFirstTime(this)) {
                    Intent intent = new Intent(this, LanguageSelectionActivity.class);
                    startActivity(intent);
                    LanguagePreference.setFirstTime(this, false);
                } else if (!LanguagePreference.isOnboardingShown(this)) {
                    Intent intent = new Intent(this, ContinueActivity.class);
                    startActivity(intent);
                    LanguagePreference.setOnboardingShown(this, true);
                } else {
                    Intent intent = new Intent(this, MainActivity.class);
                    startActivity(intent);
                }
                finish();

            }, 2000);
        }else {
            showNoInternetDialog();
        }
    }


    public void showNoInternetDialog() {
        final Dialog dialog = new Dialog(SplashActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.dialog_nointernet);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
        layoutParams.copyFrom(dialog.getWindow().getAttributes());
        layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT; // Width match parent
        dialog.getWindow().setAttributes(layoutParams);

        AppCompatButton btnCancel = dialog.findViewById(R.id.btnCancel);
        AppCompatButton btnPositive = dialog.findViewById(R.id.btnTyrAgain);

        btnPositive.setOnClickListener(v -> {
            finish();
        });
        btnCancel.setOnClickListener(v -> dialog.dismiss());
        dialog.show();
    }
}