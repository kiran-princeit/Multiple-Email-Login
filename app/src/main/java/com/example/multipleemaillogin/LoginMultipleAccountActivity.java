package com.example.multipleemaillogin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.multipleemaillogin.Utills.AppPreferences;
import com.example.multipleemaillogin.Utills.EmailManager;
import com.example.multipleemaillogin.adapter.AccountAdapter;
import com.example.multipleemaillogin.adapter.EmailAdapter;
import com.example.multipleemaillogin.model.AccountData;
import com.example.multipleemaillogin.model.EmailData;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class LoginMultipleAccountActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private AccountAdapter accountAdapter;
    private static List<EmailData> accountList;
    private int accountIdCounter = 1;
    TextView btnAdd;
    LinearLayout llData;
    FloatingActionButton addProfile;
    TextView tvTitle;
    AppCompatImageView ivBack;
    String loginUrl;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_multiple_account);

        String title = getIntent().getStringExtra("title");
        loginUrl = getIntent().getStringExtra("loginUrl");
        Log.d("LoginMultipleAccountActivity", "onCreate: " + loginUrl); // Log the login URL

        String emailType = title;
        btnAdd = findViewById(R.id.btnAdd);
        recyclerView = findViewById(R.id.rvLoginData);
        tvTitle = findViewById(R.id.tvTitle);
        ivBack = findViewById(R.id.ivBack);
        addProfile = findViewById(R.id.addProfile);
        llData = findViewById(R.id.llData);

        accountList = loadAccounts(this, emailType);
        accountAdapter = new AccountAdapter(accountList, this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(accountAdapter);

        if (accountAdapter != null) {
            llData.setVisibility(View.VISIBLE);
        }else {
            llData.setVisibility(View.GONE);
        }

        addProfile.setOnClickListener(view -> showDialog(emailType));
        tvTitle.setText(title);
        ivBack.setOnClickListener(view -> onBackPressed());
        btnAdd.setOnClickListener(view -> showDialog(emailType));
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
        TextView btnCancel = dialog.findViewById(R.id.btnCancel);
        TextView btnPositive = dialog.findViewById(R.id.btnAdd);

        btnPositive.setOnClickListener(v -> {

            String accountName = input.getText().toString();
            if (!accountName.isEmpty()) {
                Log.d("LoginMultipleAccountActivity", "Adding account: " + accountName + ", URL: " + loginUrl); // Log account details
                EmailData newAccount = new EmailData(accountIdCounter++, 0, accountName, loginUrl, emailType);
                accountList.add(newAccount);
                accountAdapter.notifyItemInserted(accountList.size() - 1);
                saveAccount(LoginMultipleAccountActivity.this, newAccount);
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

                    Log.d("LoginMultipleAccountActivity", "Loaded account: " + name + ", URL: " + url); // Log loaded account details

                    // Filter accounts by emailType
                    if (type.equals(emailType)) {
                        accountList.add(new EmailData(id, color, name, url, type));
                    }
                }
            }
        }

        return accountList;
    }
    public static int getTotalAccountCount() {
        return accountList.size(); // Replace this with the appropriate method based on your data source
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
}
