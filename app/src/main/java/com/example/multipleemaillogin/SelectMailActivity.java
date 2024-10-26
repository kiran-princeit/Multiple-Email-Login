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
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class SelectMailActivity extends AppCompatActivity implements EmailDialogAdapter.OnSelectionChangedListener {
    public static final int d = 0;
    public static int REQUEST_CODE_SELECT_EMAIL = 1;
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

        findViewById(R.id.ivClose).setOnClickListener(view -> {
            onBackPressed();
        });
        dbHelper = new EmailDatabaseHelper(this);
        rvEmails = findViewById(R.id.rvEmails);
        btnOK = findViewById(R.id.btnOK);

        emailDataList = EmailManager.getEmailData(this); // Get all email data
        loadSelectedEmails();
        // Initialize selectedEmails
        rvEmails.setLayoutManager(new LinearLayoutManager(this));

//        emailAdapter = new EmailDialogAdapter(this, emailDataList);
//        List<EmailData> selectedEmails = dbHelper.getSelectedEmails(); // Fetch selected emails
//        emailAdapter.setSelectedEmails(selectedEmails); // Update adapter

        loadSelectedEmails();
        emailAdapter = new EmailDialogAdapter(emailDataList,this,  this);

        rvEmails.setAdapter(emailAdapter);
        btnOK.setOnClickListener(view -> {

            ArrayList<String> selectedEmails = new ArrayList<>();
            for (EmailData email : emailDataList) {
                if (email.isChecked()) {
                    selectedEmails.add(String.valueOf(email.getId())); // Add the email ID
                }
            }
            Intent resultIntent = new Intent();
            resultIntent.putStringArrayListExtra("selectedEmails", selectedEmails);
            setResult(RESULT_OK, resultIntent);
            finish(); // Close the activity

        });
    }

    private void loadSelectedEmails() {
        SharedPreferences sharedPreferences = getSharedPreferences("SelectedEmails", Context.MODE_PRIVATE);
        Set<String> selectedEmails = sharedPreferences.getStringSet("emails", new HashSet<>());

        for (EmailData email : emailDataList) {
            if (selectedEmails.contains(String.valueOf(email.getId())) ||
                    email.getId() == 7 || email.getId() == 1 || email.getId() == 6 || email.getId() == 3 || email.getId() == 2) { // Default selection for id 1 and 2
                email.setChecked(true); // Mark as checked
            } else {
                email.setChecked(false); // Ensure unchecked state if not selected
            }
        }
    }

//    private void saveSelectedEmails() {
//        SharedPreferences sharedPreferences = getSharedPreferences("SelectedEmails", Context.MODE_PRIVATE);
//        SharedPreferences.Editor editor = sharedPreferences.edit();
//        Set<String> selectedEmails = new HashSet<>();
//
//        for (EmailData email : emailDataList) {
//            if (email.isChecked()) {
//                selectedEmails.add(String.valueOf(email.getId()));
//            }
//        }
//old
//        editor.putStringSet("emails", selectedEmails);
//        editor.apply();
//    }

//    @Override
//    public void onBackPressed() {
//        super.onBackPressed();
//        // Pass selected emails back to MainActivity
//        Intent intent = new Intent();
//        intent.putExtra("selectedEmails", emailAdapter.getSelectedEmails());
//        setResult(RESULT_OK, intent);
//        finish();
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


//    public class EmailDialogAdapter extends RecyclerView.Adapter<EmailDialogAdapter.EmailViewHolder> {
//        private Context context;
//        private ArrayList<EmailData> emailList;
//        private OnItemCheckedChangeListener listener;
////        private HashSet<EmailData> selectedEmails = new HashSet<>();
//
//        private List<EmailData> selectedEmails = new ArrayList<>();
//
//
////        public  List emailList;
////        public  ArrayList selectedEmails;
//
////        public EmailDialogAdapter(ArrayList arrayList) {
////            this.emailList = arrayList;
////            this.selectedEmails = new ArrayList(ContextKt.c(SelectMailActivity.this).getEmailAccounts());
////        }
//
//        public interface OnItemCheckedChangeListener {
//            void onCheckedChanged(EmailData emailData, boolean isChecked);
//        }
//        public EmailDialogAdapter(Context context, ArrayList<EmailData> emailList, OnItemCheckedChangeListener listener) {
//            this.context = context;
//            this.emailList = emailList;
//            this.listener = listener;
//        }
//
//        @NonNull
//        @Override
//        public EmailViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//            View view = LayoutInflater.from(context).inflate(R.layout.item_email_dialog, parent, false);
//            return new EmailViewHolder(view);
//        }
//
//        @Override
//        public void onBindViewHolder(@NonNull EmailViewHolder holder, int position) {
//            EmailData emailData = emailList.get(position);
//
//            holder.tvFirstChar.setText(String.valueOf(emailData.getName().charAt(0)));
//            holder.tvEmailName.setText(emailData.getName());
//            holder.tvGmailAccount.setText(emailData.getUrl());
//            holder.ivBackground.setBackgroundColor(emailData.getColor());
//
//
//            holder.checkbox.setOnClickListener(view -> {
//
//                if (selectedEmails.contains(emailData)) {
//                    selectedEmails.remove(emailData);
//                    holder.checkbox.setImageResource(R.drawable.ic_unchecked);
//                } else {
//                    selectedEmails.add(emailData);
//                    holder.checkbox.setImageResource(R.drawable.ic_checked);
//                }
//
//
//                boolean contains = emailAdapter.emailList.contains(String.valueOf(emailData.getName()));
//                List arrayList = emailAdapter.emailList;
//                int i2 = emailData.getId();
//                if (contains) {
//                    arrayList.remove(String.valueOf(i2));
//                } else {
//                    arrayList.add(String.valueOf(i2));
//                }
//                if (arrayList.isEmpty()) {
//                    int i3 = SelectMailActivity.d;
//                    SelectMailActivity.btnOK.setVisibility(8);
//                } else {
//                    int i4 = SelectMailActivity.d;
//                    SelectMailActivity.btnOK.setVisibility(0);
//                }
////                if (arrayList.contains(String.valueOf(i2))) {
////                    holder.checkbox.setImageResource(R.drawable.ic_checked);
////                    return;
////                } else {
////                    holder.checkbox.setImageResource(R.drawable.ic_unchecked);
////                    return;
////                }
//            });
////            holder.checkbox.setOnClickListener(view -> {
////                if (selectedEmails.contains(emailData)) {
////                    selectedEmails.remove(emailData);
////                    holder.checkbox.setImageResource(R.drawable.ic_unchecked);
////                } else {
////                    selectedEmails.add(emailData);
////                    holder.checkbox.setImageResource(R.drawable.ic_checked);
////                }
////
////                // Show or hide the OK button based on selection
////                SelectMailActivity.btnOK.setVisibility(selectedEmails.isEmpty() ? View.GONE : View.VISIBLE);
////            });
//
//
//        }
//
//        public ArrayList<EmailData> getSelectedEmails() {
//            return new ArrayList<>(selectedEmails);
//        }
//
//        public void setSelectedEmails(List<EmailData> selectedEmails) {
//            this.selectedEmails.clear();
//            for (EmailData email : selectedEmails) {
//                email.setChecked(true); // Mark as checked in the list
//                this.selectedEmails.add(email);
//            }
//            notifyDataSetChanged(); // Notify adapter to refresh the view
//        }
//
//        @Override
//        public int getItemCount() {
//            return emailList.size();
//        }
////        @Override
////        public int getItemCount() {
////            return emailList != null ? emailList.size() : 0; // Safeguard against null
////        }
//
//        public ArrayList<EmailData> getAll() {
//            return emailList;
//        }
//
//        public ArrayList<EmailData> getSelected() {
//            ArrayList<EmailData> selected = new ArrayList<>();
//            for (int i = 0; i < emailList.size(); i++) {
//                if (emailList.get(i).isChecked()) {
//                    selected.add(emailList.get(i));
//                }
//            }
//            return selected;
//        }
//
//        class EmailViewHolder extends RecyclerView.ViewHolder {
//            AppCompatImageView ivBackground, checkbox;
//            AppCompatTextView tvFirstChar, tvEmailName, tvGmailAccount;
//
//            public EmailViewHolder(@NonNull View itemView) {
//                super(itemView);
//                ivBackground = itemView.findViewById(R.id.ivBackground);
//                checkbox = itemView.findViewById(R.id.checkbox);
//                tvFirstChar = itemView.findViewById(R.id.tvFirstChar);
//                tvEmailName = itemView.findViewById(R.id.tvEmailName);
//                tvGmailAccount = itemView.findViewById(R.id.tvGmailAccount);
//            }
//        }
//    }


}