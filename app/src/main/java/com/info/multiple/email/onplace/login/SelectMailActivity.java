package com.info.multiple.email.onplace.login;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.remoteconfig.FirebaseRemoteConfig;
import com.info.multiple.email.onplace.login.Utills.EmailManager;
import com.info.multiple.email.onplace.login.adapter.EmailDialogAdapter;
import com.info.multiple.email.onplace.login.model.EmailData;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class SelectMailActivity extends BaseActivity implements EmailDialogAdapter.OnSelectionChangedListener {
    RecyclerView rvEmails;
    public static Button btnOK;
    EmailDialogAdapter emailAdapter;
    private ArrayList<EmailData> emailDataList;
    private List<Object> items;
    int nativeAdShow = 9;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_mail);
        emailDataList = EmailManager.getEmailData(this);

        // Populate the list with ads based on the nativeAdShow parameter
        items = insertAdsInList(emailDataList, nativeAdShow);

        findViewById(R.id.ivClose).setOnClickListener(view -> {
            onBackPressed();
        });
        rvEmails = findViewById(R.id.rvEmails);
        btnOK = findViewById(R.id.btnOK);
        rvEmails.setLayoutManager(new LinearLayoutManager(this));
        emailAdapter = new EmailDialogAdapter(items, this, this);
        rvEmails.setAdapter(emailAdapter);
        loadSelectedEmails();
        btnOK.setOnClickListener(view -> {

            ArrayList<String> selectedEmails = new ArrayList<>();
            for (EmailData email : emailDataList) {
                if (email.isChecked()) {
                    selectedEmails.add(String.valueOf(email.getId())); // Add the email ID
                }
            }
            // Save selected emails to SharedPreferences
            SharedPreferences sharedPreferences = getSharedPreferences("SelectedEmails", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putStringSet("emails", new HashSet<>(selectedEmails)); // Save as Set
            editor.apply();
            Intent resultIntent = new Intent();
            resultIntent.putStringArrayListExtra("selectedEmails", selectedEmails);
            setResult(RESULT_OK, resultIntent);
            finish(); // Close the activity

        });

        updateOkButtonVisibility();
    }
//    private List<Object> insertAdsInList(List<EmailData> emailDataList) {
//        List<Object> combinedList = new ArrayList<>();
//        for (int i = 0; i < emailDataList.size(); i++) {
//            combinedList.add(emailDataList.get(i));
//            // Insert ad placeholder every 6 items
//            if ((i + 1) % 3 == 0) {
//                combinedList.add(EmailDialogAdapter.AD_PLACEHOLDER);
//            }
//        }
//        return combinedList;
//    }

    private List<Object> insertAdsInList(List<EmailData> emailDataList, int nativeAdShow) {
        List<Object> combinedList = new ArrayList<>();
        for (int i = 0; i < emailDataList.size(); i++) {
            combinedList.add(emailDataList.get(i));
            // Insert ad placeholder after every `nativeAdShow` items
            if ((i + 1) % nativeAdShow == 0) {
                combinedList.add(EmailDialogAdapter.AD_PLACEHOLDER);
            }
        }
        return combinedList;
    }
//private List<Object> insertAdsInList(List<EmailData> emailDataList) {
//    List<Object> combinedList = new ArrayList<>();
//    for (int i = 0; i < emailDataList.size(); i++) {
//        combinedList.add(emailDataList.get(i));
//
//        // Insert ad placeholder after every `nativeAdShow` items
//        if (nativeAdShow > 0 && (i + 1) % nativeAdShow == 0) {
//            combinedList.add(EmailDialogAdapter.AD_PLACEHOLDER);
//        }
//    }
//    return combinedList;
//}


    private void loadSelectedEmails() {
        // Define default IDs
        Set<String> defaultIds = new HashSet<>(Arrays.asList("1", "2", "3", "7", "9", "18", "12"));
        // Get selected and removed default emails from SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences("SelectedEmails", Context.MODE_PRIVATE);
        Set<String> selectedEmails = sharedPreferences.getStringSet("emails", new HashSet<>());
        Set<String> removedDefaults = sharedPreferences.getStringSet("removedDefaults", new HashSet<>());

        // Loop through the email data list and update checked status
        for (EmailData email : emailDataList) {
            String emailId = String.valueOf(email.getId());


            if (selectedEmails.contains(emailId) || (defaultIds.contains(emailId) && !removedDefaults.contains(emailId))) {
                email.setChecked(true);
            } else {
                email.setChecked(false);
            }
        }

        if (emailAdapter != null) {
            emailAdapter.notifyDataSetChanged();
        }
    }

    public void updateOkButtonVisibility() {
        boolean isAnyEmailSelected = false;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            isAnyEmailSelected = emailDataList.stream().anyMatch(EmailData::isChecked);
        }
        btnOK.setVisibility(isAnyEmailSelected ? View.VISIBLE : View.GONE);
    }

    @Override
    public void onCheckedChanged(EmailData emailData, boolean isChecked) {
        emailData.setChecked(isChecked);
        saveSelectedEmails();
    }

    private void saveSelectedEmails() {
        SharedPreferences sharedPreferences = getSharedPreferences("SelectedEmails", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Set<String> selectedEmails = new HashSet<>();

        for (EmailData email : emailDataList) {
            if (email.isChecked()) {
                selectedEmails.add(String.valueOf(email.getId())); // Save only checked email IDs
            }
        }
        editor.putStringSet("emails", selectedEmails); // Save the set of selected emails
        editor.apply(); // Apply the changes
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadSelectedEmails();
        updateOkButtonVisibility();// Reload emails to update checked state based on SharedPreferences
    }
}