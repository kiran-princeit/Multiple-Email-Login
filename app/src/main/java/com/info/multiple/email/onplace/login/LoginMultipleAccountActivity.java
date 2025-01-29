package com.info.multiple.email.onplace.login;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.info.multiple.email.onplace.login.Utills.EmailManager;
import com.info.multiple.email.onplace.login.adapter.AccountAdapter;
import com.info.multiple.email.onplace.login.adsprosimple.MobileAds;
import com.info.multiple.email.onplace.login.model.EmailData;

import java.util.ArrayList;
import java.util.List;

public class LoginMultipleAccountActivity extends BaseActivity {
    private RecyclerView recyclerView;
    private AccountAdapter accountAdapter;
    private static List<EmailData> accountList;
    private int accountIdCounter = 1;
    LinearLayout btnAdd;
    LinearLayout llData, ll_main;
    Button addProfile;
    TextView tvTitle;
    AppCompatImageView ivBack;
    String loginUrl;
    public static String title;
    public static int emailColor;
    private FrameLayout adContainer;
    private ShimmerFrameLayout shimmerFrameLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_multiple_account);

        adContainer = findViewById(R.id.ad_container);
        shimmerFrameLayout = findViewById(R.id.shimmer_layout);
        loadAd();

        title = getIntent().getStringExtra("title");
        loginUrl = getIntent().getStringExtra("loginUrl");
        emailColor = getIntent().getIntExtra("emailColor", Color.WHITE);

        String emailType = title;
        btnAdd = findViewById(R.id.btnAdd);
        recyclerView = findViewById(R.id.rvLoginData);
        tvTitle = findViewById(R.id.tvTitle);
        ivBack = findViewById(R.id.ivBack);
        addProfile = findViewById(R.id.addProfile);
        llData = findViewById(R.id.llData);
        ll_main = findViewById(R.id.ll_main);

        accountList = loadAccounts(this, emailType);

        accountAdapter = new AccountAdapter(accountList, this);
        checkArrayListAndUpdateButtonVisibility();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(accountAdapter);
        addProfile.setOnClickListener(view -> showDialog(emailType));
        tvTitle.setText(title);
        ivBack.setOnClickListener(view -> onBackPressed());
        btnAdd.setOnClickListener(view -> showDialog(emailType));

    }

    private void loadAd() {
        if (adContainer != null && !MyApp.isNetworkConnected(LoginMultipleAccountActivity.this)) {
            adContainer.setVisibility(View.GONE);
            return;
        }
        MobileAds.showBanner(adContainer, shimmerFrameLayout, LoginMultipleAccountActivity.this);
    }

    private void checkArrayListAndUpdateButtonVisibility() {
        if (accountList == null || accountList.isEmpty()) {
            // Hide llData
            llData.setVisibility(View.GONE);
            btnAdd.setVisibility(View.VISIBLE);
            addProfile.setVisibility(View.GONE);

            Log.d("TAG", "checkArrayListAndUpdateButtonVisibility: " + accountList.size());

            // Adjust ll_main layout params to take more space
            LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) ll_main.getLayoutParams();
            params.weight = 1.7f; // Increase weight as needed to fill space
            ll_main.setLayoutParams(params);
        } else {
            // Show llData and reset layout params for ll_main
            llData.setVisibility(View.VISIBLE);
            btnAdd.setVisibility(View.GONE);
            addProfile.setVisibility(View.VISIBLE);
            LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) ll_main.getLayoutParams();
            params.weight = 1f; // Restore original weight if needed
            ll_main.setLayoutParams(params);
            Log.d("TAG1", "checkArrayListAndUpdateButtonVisibility: " + accountList.size());
        }

    }

    public void showDialog(String emailType) {
        final Dialog dialog = new Dialog(LoginMultipleAccountActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.dialog_addprofile);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
        layoutParams.copyFrom(dialog.getWindow().getAttributes());
        layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT; // Width match parent
        dialog.getWindow().setAttributes(layoutParams);

        AppCompatEditText input = dialog.findViewById(R.id.etProfileName);
        AppCompatButton btnCancel = dialog.findViewById(R.id.btnCancel);
        AppCompatButton btnPositive = dialog.findViewById(R.id.btnAdd);
        AppCompatTextView tvCounter = dialog.findViewById(R.id.tvCounter);
        input.setFilters(new android.text.InputFilter[]{
                new android.text.InputFilter.LengthFilter(20)
        });
        input.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int start, int count, int after) {
                // Not needed in this case
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
                // Update character count
                String inputText = input.getText().toString();
                int charCount = inputText.length();
                tvCounter.setText(charCount + "/20");
            }

            @Override
            public void afterTextChanged(Editable editable) {
                // Not needed in this case
            }
        });


        btnPositive.setOnClickListener(v -> {
            String accountName = input.getText().toString();
            if (!accountName.isEmpty()) {
                EmailData newAccount = new EmailData(accountIdCounter++, 0, accountName, loginUrl, emailType);
                accountList.add(newAccount);
                accountAdapter.notifyItemInserted(accountList.size() - 1);
                saveAccount(LoginMultipleAccountActivity.this, newAccount);
                checkArrayListAndUpdateButtonVisibility();
                dialog.cancel();
            } else {
                Toast.makeText(LoginMultipleAccountActivity.this, "Please enter an account name", Toast.LENGTH_SHORT).show();
            }
        });
        btnCancel.setOnClickListener(v -> dialog.dismiss());
        dialog.show();
    }


    private ArrayList<EmailData> loadAccounts(Context context, String emailType) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(EmailManager.PREFS_NAME, Context.MODE_PRIVATE);
        String savedAccounts = sharedPreferences.getString(EmailManager.SELECTED_EMAILS_KEY, "");

        ArrayList<EmailData> accountList = new ArrayList<>();

        if (!savedAccounts.isEmpty()) {
            String[] accountsArray = savedAccounts.split("\\|");
            for (String accountInfo : accountsArray) {
                String[] parts = accountInfo.split(",");
                if (parts.length == 5) { // Ensure it matches the number of fields
                    int id = Integer.parseInt(parts[0]);
                    String name = parts[1];
                    String url = parts[2]; // Ensure URL is being retrieved
                    int color = Integer.parseInt(parts[3]);
                    String type = parts[4];

                    // Filter accounts by emailType
                    if (type.equals(emailType)) {
                        accountList.add(new EmailData(id, color, name, url, type));
                    }


                }
            }
        }
        if (accountAdapter != null) {
            accountAdapter.notifyDataSetChanged();
        }
        return accountList;
    }

    private void saveAccount(Context context, EmailData emailData) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(EmailManager.PREFS_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        String existingAccounts = sharedPreferences.getString(EmailManager.SELECTED_EMAILS_KEY, "");
        String newAccount = emailData.getId() + "," + emailData.getName() + "," + emailData.getUrl() + "," + emailData.getColor() + "," + emailData.getEmailType(); // Save email type
        String newAccounts = existingAccounts.isEmpty() ? newAccount : existingAccounts + "|" + newAccount; // Use '|' as a delimiter

        editor.putString(EmailManager.SELECTED_EMAILS_KEY, newAccounts);
        editor.apply();
    }

    private void returnAccountCountToMain() {
        // Count the number of accounts

        int accountCount = accountList.size();
        Log.d("SelectMailActivity", "Account Count: " + accountCount);

        // Create an Intent to return the count to MainActivity
        Intent resultIntent = new Intent();
        resultIntent.putExtra("accountCount", accountCount);
        setResult(RESULT_OK, resultIntent);
        finish();
    }

    @Override
    protected void onResume() {
        super.onResume();
        checkArrayListAndUpdateButtonVisibility();
//        returnAccountCountToMain();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        returnAccountCountToMain();

    }
}



