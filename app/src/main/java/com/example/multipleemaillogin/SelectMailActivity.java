package com.example.multipleemaillogin;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.multipleemaillogin.Utills.ContextKt;
import com.example.multipleemaillogin.Utills.EmailManager;
import com.example.multipleemaillogin.adapter.EmailDialogAdapter;
import com.example.multipleemaillogin.database.EmailDatabaseHelper;
import com.example.multipleemaillogin.model.EmailData;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class SelectMailActivity extends AppCompatActivity implements EmailDialogAdapter.OnSelectionChangedListener {
    public static final int d = 0;
    RecyclerView rvEmails;
    public static Button btnOK;
    EmailDialogAdapter emailAdapter;
    private ArrayList<EmailData> emailDataList;
    private EmailDatabaseHelper dbHelper;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_mail);

        emailDataList = EmailManager.getEmailData(this);

        findViewById(R.id.ivClose).setOnClickListener(view -> {
            onBackPressed();
        });

        rvEmails = findViewById(R.id.rvEmails);
        btnOK = findViewById(R.id.btnOK);

        rvEmails.setLayoutManager(new LinearLayoutManager(this));
        emailAdapter = new EmailDialogAdapter(emailDataList,this,  this);

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

    private void loadSelectedEmails() {
        // Define default IDs
        Set<String> defaultIds = new HashSet<>(Arrays.asList("1", "2", "3", "6", "7"));

        // Get selected and removed default emails from SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences("SelectedEmails", Context.MODE_PRIVATE);
        Set<String> selectedEmails = sharedPreferences.getStringSet("emails", new HashSet<>());
        Set<String> removedDefaults = sharedPreferences.getStringSet("removedDefaults", new HashSet<>());

        // Loop through the email data list and update checked status
        for (EmailData email : emailDataList) {
            String emailId = String.valueOf(email.getId());

            // Check if the email is selected, or it’s a default but not removed
            if (selectedEmails.contains(emailId) || (defaultIds.contains(emailId) && !removedDefaults.contains(emailId))) {
                email.setChecked(true);
            } else {
                email.setChecked(false);
            }
        }

        // Notify adapter of changes
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


    //    private void loadSelectedEmails() {
//        SharedPreferences sharedPreferences = getSharedPreferences("SelectedEmails", Context.MODE_PRIVATE);
//        Set<String> selectedEmails = sharedPreferences.getStringSet("emails", new HashSet<>());
//
//        for (EmailData email : emailDataList) {
//            if (selectedEmails.contains(String.valueOf(email.getId())) ||
//                    email.getId() == 7 || email.getId() == 1 || email.getId() == 6 || email.getId() == 3 || email.getId() == 2) { // Default selection id
//                email.setChecked(true); // Mark as checked
//            } else {
//                email.setChecked(false);
//            }
//        }
//        if (emailAdapter != null) {
//            emailAdapter.notifyDataSetChanged();
//        }
//    }
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