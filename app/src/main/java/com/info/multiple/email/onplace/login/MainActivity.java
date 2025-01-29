package com.info.multiple.email.onplace.login;

import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.info.multiple.email.onplace.login.Utills.AdConsent;
import com.info.multiple.email.onplace.login.Utills.AdConsentListener;
import com.info.multiple.email.onplace.login.Utills.EmailManager;
import com.info.multiple.email.onplace.login.adapter.EmailAdapter;
import com.info.multiple.email.onplace.login.adsprosimple.MobileAds;
import com.info.multiple.email.onplace.login.model.EmailData;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class MainActivity extends BaseActivity {
    public static final int pos = 0;
    public static final int REQUEST_CODE_LOGIN = 1;
    private static final int SELECT_EMAIL_REQUEST = 1;
    private RecyclerView recyclerView;
    private EmailAdapter emailAdapter;
    LinearLayout llEmpty;
    List<EmailData> emailDataList;
    private List<EmailData> checkedEmails;

    private Dialog dialog;
    AdConsent adConsent;
    private FrameLayout adContainer;
    private ShimmerFrameLayout shimmerFrameLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        adContainer = findViewById(R.id.ad_container);
        shimmerFrameLayout = findViewById(R.id.shimmer_layout);
        loadAd();
        adConsent = new AdConsent(this, new AdConsentListener() {
            @Override
            public void onConsentUpdate() {

            }
        });
        adConsent.checkForConsent();

        recyclerView = findViewById(R.id.rvEmails);
        llEmpty = findViewById(R.id.llEmpty);

        emailDataList = loadAllEmails();

        emailAdapter = new EmailAdapter(MainActivity.this, new ArrayList<>(), MainActivity.this);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        recyclerView.setAdapter(emailAdapter);

        loadCheckedEmails();
        updateLayoutVisibility();


        findViewById(R.id.ivAddMail).setOnClickListener(view -> {
            MobileAds.showInterstitial(MainActivity.this, () -> {
                Intent intent = new Intent(MainActivity.this, SelectMailActivity.class);
                startActivityForResult(intent, SELECT_EMAIL_REQUEST);
            });
        });

        findViewById(R.id.ivSetting).setOnClickListener(view -> {
            MobileAds.showInterstitial(MainActivity.this, () -> {
                startActivity(new Intent(MainActivity.this, SettingActivity.class));
            });
        });
        showRatingDialog();
    }

    private void loadAd() {
        if (adContainer != null && !MyApp.isNetworkConnected(MainActivity.this)) {
            adContainer.setVisibility(View.GONE);
            return;
        }
        MobileAds.showNativeBig(adContainer, shimmerFrameLayout, MainActivity.this);
    }

    private List<EmailData> loadAllEmails() {
        return EmailManager.getEmailData(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d("MainActivity", "onActivityResult triggered");

        if (requestCode == SELECT_EMAIL_REQUEST && resultCode == RESULT_OK && data != null) {
            List<String> updatedSelectedEmails = data.getStringArrayListExtra("selectedEmails");

            if (updatedSelectedEmails != null) {
                List<EmailData> filteredEmailList = new ArrayList<>();
                for (EmailData email : emailDataList) {
                    if (updatedSelectedEmails.contains(String.valueOf(email.getId()))) {
                        filteredEmailList.add(email);
                    }
                }

                emailAdapter.updateEmailList(filteredEmailList);
            }
        }
        if (requestCode == REQUEST_CODE_LOGIN && resultCode == RESULT_OK && data != null) {
            int totalAccountCount = data.getIntExtra("accountCount", 0); // Default to 0 if not passed

            // Update the EmailAdapter with the new account count
            if (emailAdapter != null) {
                emailAdapter.updateAccountCount(totalAccountCount); // Pass the account count to the adapter
            }
            Log.e("onActivityResult", "onActivityResult: " + totalAccountCount);

        }
    }

    private void loadCheckedEmails() {
        checkedEmails = new ArrayList<>();
        SharedPreferences sharedPreferences = getSharedPreferences("SelectedEmails", Context.MODE_PRIVATE);
        Set<String> selectedEmails = sharedPreferences.getStringSet("emails", new HashSet<>());

        //default
        List<Integer> defaultSelectedIds = Arrays.asList(1, 2, 3, 7, 9, 18, 12);
        Set<Integer> defaultSelectedSet = new HashSet<>(defaultSelectedIds);

        for (EmailData email : emailDataList) {
            if (selectedEmails.contains(String.valueOf(email.getId())) || defaultSelectedSet.contains(email.getId())) {
                checkedEmails.add(email); // Add to checkedEmails if selected
            }
        }
        emailAdapter.updateEmailList(checkedEmails); // Update the adapter with checked emails
    }

    public void updateLayoutVisibility() {
        if (emailAdapter.getItemCount() == 0) {
            llEmpty.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
        } else {
            llEmpty.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateLayoutVisibility();
    }

    @Override
    public void onBackPressed() {
        if (isFinishing()) {
            if (dialog != null && dialog.isShowing()) {
                dialog.dismiss();
            }
        } else {
            showExitDialog();
        }
    }

    private void showExitDialog() {
        dialog = new Dialog(MainActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.dialog_exit);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
        layoutParams.copyFrom(dialog.getWindow().getAttributes());
        layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT; // Width match parent
        dialog.getWindow().setAttributes(layoutParams);
        FrameLayout adContainerBanner = dialog.findViewById(R.id.ad_container_exit);
        ShimmerFrameLayout shimmerFrameLayout = dialog.findViewById(R.id.shimmer_layout_exit);
        MobileAds.showNativeBig(adContainerBanner, shimmerFrameLayout, MainActivity.this);

        AppCompatButton btnCancel = dialog.findViewById(R.id.btnCancel);
        AppCompatButton btnexit = dialog.findViewById(R.id.btnexit);

        btnexit.setOnClickListener(v -> {
            finishAffinity();
        });

        btnCancel.setOnClickListener(v -> {
            dialog.dismiss();
        });

        dialog.show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
    }


    private void showRatingDialog() {
        try {
            SharedPreferences app_preferences = PreferenceManager.getDefaultSharedPreferences(this);
            int counter = app_preferences.getInt("counter", 0);
            int RunEvery = 8;
            if (counter != 0 && counter % RunEvery == 0) {

                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                LayoutInflater inflater = getLayoutInflater();
                final View dialogView = inflater.inflate(R.layout.dialog_rating, null);
                builder.setView(dialogView);

                AppCompatButton btnSubmit = dialogView.findViewById(R.id.btnSubmit);
                AppCompatButton btnCancel = dialogView.findViewById(R.id.btnCancel);

                AlertDialog exitDialog = builder.create();
                exitDialog.setCancelable(false);
                exitDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));


                btnCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        exitDialog.cancel();
                    }
                });
                btnSubmit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Uri uri = Uri.parse("market://details?id=" + getPackageName());
                        Intent openPlayStore = new Intent(Intent.ACTION_VIEW, uri);
                        try {
                            startActivity(openPlayStore);
                        } catch (ActivityNotFoundException e) {
                            Toast.makeText(MainActivity.this, " unable to find market app", Toast.LENGTH_LONG).show();
                        }
                        exitDialog.dismiss();
                    }
                });

                exitDialog.show();

                Window window = exitDialog.getWindow();
                if (window != null) {
                    int width = (int) (getResources().getDisplayMetrics().widthPixels * 0.85); // 85% of screen width
                    window.setLayout(width, WindowManager.LayoutParams.WRAP_CONTENT);
                    window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                }

            }
            SharedPreferences.Editor editor = app_preferences.edit();
            editor.putInt("counter", ++counter);
            editor.commit();
        } catch (Exception e) {
            e.printStackTrace();

        }
    }
}