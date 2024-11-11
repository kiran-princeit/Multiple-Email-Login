package com.info.multiple.email.onplace.login;

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
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.info.multiple.email.onplace.login.Utills.EmailManager;
import com.info.multiple.adapter.EmailAdapter;
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

    private boolean isDialogShown = false;
    private List<EmailData> checkedEmails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.rvEmails);
        llEmpty = findViewById(R.id.llEmpty);

        emailDataList = loadAllEmails();
//        emailAdapter = new EmailAdapter(MainActivity.this, emailDataList);

        emailAdapter = new EmailAdapter(MainActivity.this, new ArrayList<>(), MainActivity.this);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        recyclerView.setAdapter(emailAdapter);

        loadCheckedEmails();
        updateLayoutVisibility();

        findViewById(R.id.ivAddMail).setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, SelectMailActivity.class);
            startActivityForResult(intent, SELECT_EMAIL_REQUEST);
        });

        findViewById(R.id.ivSetting).setOnClickListener(view -> {
            startActivity(new Intent(MainActivity.this, SettingActivity.class));
        });
    }

    private List<EmailData> loadAllEmails() {
        return EmailManager.getEmailData(this); // Replace with your logic
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d("MainActivity", "onActivityResult triggered");

        if (requestCode == SELECT_EMAIL_REQUEST && resultCode == RESULT_OK && data != null) {
            List<String> updatedSelectedEmails = data.getStringArrayListExtra("selectedEmails");

            if (updatedSelectedEmails != null) {
                // Filter the email list based on updated selections

                List<EmailData> filteredEmailList = new ArrayList<>();
                for (EmailData email : emailDataList) {
                    if (updatedSelectedEmails.contains(String.valueOf(email.getId()))) {
                        filteredEmailList.add(email);
                    }
                }

                // Update the adapter with the filtered list
                emailAdapter.updateEmailList(filteredEmailList);

                // Set the count to the TextView in EmailAdapter or wherever necessary

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
        showExitDialog();
    }

    private void showExitDialog() {
        final Dialog dialog = new Dialog(MainActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.dialog_exit);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));


        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
        layoutParams.copyFrom(dialog.getWindow().getAttributes());
        layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT; // Width match parent
        dialog.getWindow().setAttributes(layoutParams);


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


}