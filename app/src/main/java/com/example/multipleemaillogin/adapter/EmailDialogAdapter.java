package com.example.multipleemaillogin.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.multipleemaillogin.R;
import com.example.multipleemaillogin.SelectMailActivity;
import com.example.multipleemaillogin.database.EmailDatabaseHelper;
import com.example.multipleemaillogin.model.EmailData;

import java.util.ArrayList;
import java.util.List;

public class EmailDialogAdapter extends RecyclerView.Adapter<EmailDialogAdapter.EmailViewHolder> {
    private Context context;
    private List<EmailData> emailList;
    private EmailDatabaseHelper dbHelper;
    private List<EmailData> selectedEmails = new ArrayList<>();
    private OnSelectionChangedListener selectionChangedListener;


//        public  List emailList;
//        public  ArrayList selectedEmails;

//        public EmailDialogAdapter(ArrayList arrayList) {
//            this.emailList = arrayList;
//            this.selectedEmails = new ArrayList(ContextKt.c(SelectMailActivity.this).getEmailAccounts());
//        }


    public EmailDialogAdapter(List<EmailData> emailList, Context context,OnSelectionChangedListener listener) {
        this.emailList = emailList;
        this.context = context;
        this.selectionChangedListener = listener;
    }
//        public EmailDialogAdapter(Context context, ArrayList<EmailData> emailList,OnEmailClickListener onEmailClickListener) {
//            this.context = context;
//            this.emailList = emailList;
//            this.dbHelper = new EmailDatabaseHelper(context);
//            this.onEmailClickListener = onEmailClickListener;
//        }


    public void updateEmails(List<EmailData> newEmails) {
        emailList.clear();
        emailList.addAll(newEmails);
        notifyDataSetChanged();
    }
            public void setSelectedEmails(List<EmailData> selectedEmails) {
            this.selectedEmails.clear();
            for (EmailData email : selectedEmails) {
                email.setChecked(true); // Mark as checked in the list
                this.selectedEmails.add(email);
            }
            notifyDataSetChanged(); // Notify adapter to refresh the view
        }
        public ArrayList<EmailData> getSelectedEmails() {
            return new ArrayList<>(selectedEmails);
        }

    @NonNull
    @Override
    public EmailDialogAdapter.EmailViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_email_dialog, parent, false);
        return new EmailDialogAdapter.EmailViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EmailDialogAdapter.EmailViewHolder holder, int position) {
        EmailData emailData = emailList.get(position);

        holder.tvFirstChar.setText(String.valueOf(emailData.getName().charAt(0)));
        holder.tvEmailName.setText(emailData.getName());
        holder.tvGmailAccount.setText(emailData.getUrl());
        holder.ivBackground.setBackgroundColor(emailData.getColor());


//        holder.itemView.setOnClickListener(v -> {
//            if (selectedEmails.contains(emailData.getId())) {
//                selectedEmails.remove(Integer.valueOf(emailData.getId()));
//            } else {
//                selectedEmails.add(emailData.getId());
//            }
//            notifyItemChanged(position);
//
//            // Notify activity if selection changed
//            if (selectionChangedListener != null) {
//                selectionChangedListener.onSelectionChanged(!selectedEmails.isEmpty());
//            }
//        });

//        holder.checkbox.setImageResource(selectedEmails.contains(emailData.getId()) ?
//                R.drawable.ic_checked : R.drawable.ic_unchecked);

        if (emailData.isChecked()) {
            holder.checkbox.setImageResource(R.drawable.ic_checked); // Set your checked drawable
        } else {
            holder.checkbox.setImageResource(R.drawable.ic_unchecked); // Set your unchecked drawable
        }

        if (emailList.isEmpty()) {
//                int i3 = SelectMailActivity.d;
            SelectMailActivity.btnOK.setVisibility(8);
        } else {
//                int i4 = SelectMailActivity.d;
            SelectMailActivity.btnOK.setVisibility(0);
        }

        holder.checkbox.setOnClickListener((view) -> {

            emailData.setChecked(!emailData.isChecked()); // Toggle the checked state
            notifyItemChanged(position); // Notify the change to update the UI

            if (selectionChangedListener != null) {
                selectionChangedListener.onCheckedChanged(emailData, emailData.isChecked());
            }


        });

//        holder.checkbox.setOnClickListener(view -> {
////
//            if (emailList.contains(emailData)) {
//                emailList.remove(emailData);
//                holder.checkbox.setImageResource(R.drawable.ic_unchecked);
//            } else {
//                emailList.add(emailData);
//                holder.checkbox.setImageResource(R.drawable.ic_checked);
////                    dbHelper.updateEmailSelection(emailData.getId(), selectedEmails); // Update database
//            }
//            if (dbHelper != null) {
//                dbHelper.updateEmailSelection(emailData.getId(), !emailData.isChecked());
//                emailData.setChecked(!emailData.isChecked());
//                notifyItemChanged(position);
//            } else {
//                Log.e("EmailDialogAdapter", "Database helper is null!");
//            }








//           holder.checkbox.setImageResource(emailList.isChecked() ? R.drawable.ic_checked : R.drawable.ic_unchecked);


//            boolean newState = !emailData.isChecked();
//            emailData.setChecked(newState);
//            dbHelper.updateEmailSelection(emailData.getId(), newState); // Update database

            // Refresh the image based on the new selection state
//                if (newState) {
//                    holder.selectionImageView.setImageResource(R.drawable.ic_checked);
//                } else {
//                    holder.selectionImageView.setImageResource(R.drawable.ic_unchecked);
//                }

//            if (newState) {
//                onEmailClickListener.onEmailSelected(emailData.getId());
//                holder.checkbox.setImageResource(R.drawable.ic_unchecked);
//            } else {
//                onEmailClickListener.onEmailDeselected(emailData.getId());
//                holder.checkbox.setImageResource(R.drawable.ic_checked);
//            }


//            boolean contains = emailList.contains(String.valueOf(emailData.getName()));
//            List arrayList = emailList;
//            int i2 = emailData.getId();
//            if (contains) {
//                arrayList.remove(String.valueOf(i2));
//            } else {
//                arrayList.add(String.valueOf(i2));
//            }
//            if (arrayList.isEmpty()) {
//                int i3 = SelectMailActivity.d;
//                SelectMailActivity.btnOK.setVisibility(8);
//            } else {
//                int i4 = SelectMailActivity.d;
//                SelectMailActivity.btnOK.setVisibility(0);
//            }
//                if (arrayList.contains(String.valueOf(i2))) {
//                    holder.checkbox.setImageResource(R.drawable.ic_checked);
//                    return;
//                } else {
//                    holder.checkbox.setImageResource(R.drawable.ic_unchecked);
//                    return;
//                }
//        });

    }

    @Override
    public int getItemCount() {
        return emailList.size();
    }

    public void setData(List<EmailData> emailDataList) {
        this.emailList.clear();
        this.emailList.addAll(emailDataList);
        notifyDataSetChanged();
    }
//        @Override
//        public int getItemCount() {
//            return emailList != null ? emailList.size() : 0; // Safeguard against null
//        }

//    public ArrayList<EmailData> getAll() {
//        return emailList;
//    }

    public ArrayList<EmailData> getSelected() {
        ArrayList<EmailData> selected = new ArrayList<>();
        for (int i = 0; i < emailList.size(); i++) {
            if (emailList.get(i).isChecked()) {
                selected.add(emailList.get(i));
            }
        }
        return selected;
    }

    class EmailViewHolder extends RecyclerView.ViewHolder {
        AppCompatImageView ivBackground, checkbox;
        AppCompatTextView tvFirstChar, tvEmailName, tvGmailAccount;

        public EmailViewHolder(@NonNull View itemView) {
            super(itemView);
            ivBackground = itemView.findViewById(R.id.ivBackground);
            checkbox = itemView.findViewById(R.id.checkbox);
            tvFirstChar = itemView.findViewById(R.id.tvFirstChar);
            tvEmailName = itemView.findViewById(R.id.tvEmailName);
            tvGmailAccount = itemView.findViewById(R.id.tvGmailAccount);
        }
    }

    public interface OnSelectionChangedListener {
        void onCheckedChanged(EmailData emailData, boolean isChecked);
    }
}
