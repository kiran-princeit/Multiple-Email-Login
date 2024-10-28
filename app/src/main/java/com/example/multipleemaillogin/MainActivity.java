package com.example.multipleemaillogin;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.multipleemaillogin.Utills.EmailManager;
import com.example.multipleemaillogin.adapter.EmailAdapter;
import com.example.multipleemaillogin.database.EmailDatabaseHelper;
import com.example.multipleemaillogin.model.EmailData;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import kotlin.jvm.internal.Intrinsics;

public class MainActivity extends AppCompatActivity {
    public static final int pos = 0;
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
        emailAdapter = new EmailAdapter(MainActivity.this, new ArrayList<>(),MainActivity.this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(emailAdapter);

        loadCheckedEmails();

        findViewById(R.id.addProfile).setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, SelectMailActivity.class);
            startActivityForResult(intent, SELECT_EMAIL_REQUEST);
        });
    }

    private List<EmailData> loadAllEmails() {
        return EmailManager.getEmailData(this); // Replace with your logic
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == SELECT_EMAIL_REQUEST && resultCode == RESULT_OK && data != null) {
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
                }
            }
        }
    }

    private void loadCheckedEmails() {
        checkedEmails = new ArrayList<>();

        SharedPreferences sharedPreferences = getSharedPreferences("SelectedEmails", Context.MODE_PRIVATE);
        Set<String> selectedEmails = sharedPreferences.getStringSet("emails", new HashSet<>());

//default
        List<Integer> defaultSelectedIds = Arrays.asList(1, 2, 3, 6, 7);
        Set<Integer> defaultSelectedSet = new HashSet<>(defaultSelectedIds);


        for (EmailData email : emailDataList) {
            if (selectedEmails.contains(String.valueOf(email.getId())) || defaultSelectedSet.contains(email.getId())) {
                checkedEmails.add(email); // Add to checkedEmails if selected
            }
        }
        emailAdapter.updateEmailList(checkedEmails); // Update the adapter with checked emails
    }
    public boolean isDialogShown() {
        return isDialogShown;
    }

    public void setDialogShown(boolean dialogShown) {
        isDialogShown = dialogShown;
    }

}