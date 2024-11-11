package com.info.multiple.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.info.multiple.email.onplace.login.R;
import com.info.multiple.email.onplace.login.SelectMailActivity;
import com.info.multiple.email.onplace.login.model.EmailData;

import java.util.ArrayList;
import java.util.List;

public class EmailDialogAdapter extends RecyclerView.Adapter<EmailDialogAdapter.EmailViewHolder> {
    private Context context;
    private List<EmailData> emailList;

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


        int color = emailData.getColor();
        Drawable background = ContextCompat.getDrawable(holder.itemView.getContext(), R.drawable.rounded_rectangle);
        if (background != null) {
            background.setTint(color);
            holder.ivBackground.setBackground(background);
        }

        if (emailData.isChecked()) {
            holder.checkbox.setImageResource(R.drawable.ic_checked);
        } else {
            holder.checkbox.setImageResource(R.drawable.ic_unchecked);
        }

        holder.checkbox.setOnClickListener((view) -> {
            emailData.setChecked(!emailData.isChecked());
            if (emailData.isChecked()) {
                selectedEmails.add(emailData);
            } else {
                selectedEmails.remove(emailData);
            }
            notifyItemChanged(position);
            if (selectionChangedListener != null) {
                selectionChangedListener.onCheckedChanged(emailData, emailData.isChecked());
            }
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
        AppCompatTextView tvFirstChar, tvEmailName;

        public EmailViewHolder(@NonNull View itemView) {
            super(itemView);
            ivBackground = itemView.findViewById(R.id.ivBackground);
            checkbox = itemView.findViewById(R.id.checkbox);
            tvFirstChar = itemView.findViewById(R.id.tvFirstChar);
            tvEmailName = itemView.findViewById(R.id.tvEmailName);

        }
    }
    public interface OnSelectionChangedListener {
        void onCheckedChanged(EmailData emailData, boolean isChecked);
    }
}
