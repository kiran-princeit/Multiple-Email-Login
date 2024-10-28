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

    public EmailDialogAdapter(List<EmailData> emailList, Context context, OnSelectionChangedListener listener) {
        this.emailList = emailList;
        this.context = context;
        this.selectionChangedListener = listener;
    }

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

        if (emailData.isChecked()) {
            holder.checkbox.setImageResource(R.drawable.ic_checked); // Set your checked drawable
        } else {
            holder.checkbox.setImageResource(R.drawable.ic_unchecked); // Set your unchecked drawable
        }

        holder.checkbox.setOnClickListener((view) -> {

//            emailData.setChecked(!emailData.isChecked()); // Toggle the checked state
//            notifyItemChanged(position); // Notify the change to update the UI
//            if (selectionChangedListener != null) {
//                selectionChangedListener.onCheckedChanged(emailData, emailData.isChecked());
//            }



            emailData.setChecked(!emailData.isChecked()); // Toggle the checked state
            if (emailData.isChecked()) {
                selectedEmails.add(emailData); // Add to selected list
            } else {
                selectedEmails.remove(emailData); // Remove from selected list
            }
            notifyItemChanged(position); // Notify the change to update the UI

            // Notify the listener
            if (selectionChangedListener != null) {
                selectionChangedListener.onCheckedChanged(emailData, emailData.isChecked());
            }

            // Update the OK button visibility
            if (context instanceof SelectMailActivity) {
                ((SelectMailActivity) context).updateOkButtonVisibility();
            }
        });
    }
    @Override
    public int getItemCount() {
        return emailList.size();
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
