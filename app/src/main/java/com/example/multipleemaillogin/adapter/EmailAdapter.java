package com.example.multipleemaillogin.adapter;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.multipleemaillogin.LoginMultipleAccountActivity;
import com.example.multipleemaillogin.MainActivity;
import com.example.multipleemaillogin.R;
import com.example.multipleemaillogin.model.EmailData;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class EmailAdapter extends RecyclerView.Adapter<EmailAdapter.EmailViewHolder> {
    private Context context;
    private List<EmailData> i;
    MainActivity mainActivity;

    public EmailAdapter(Context context, List<EmailData> emailList, MainActivity mainActivity) {
        this.context = context;
        this.i = emailList;
        this.mainActivity = mainActivity;
    }

    @NonNull
    @Override
    public EmailViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_email, parent, false);
        return new EmailViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EmailViewHolder holder, int position) {
        EmailData emailData = i.get(position);
        holder.tvFirstChar.setText(String.valueOf(emailData.getName().charAt(0)));

        holder.tvEmailName.setText(emailData.getName());
//        holder.tvGmailAccount.setText(emailData.getUrl());
        holder.ivBackground.setBackgroundColor(emailData.getColor());


        holder.ivMore.setOnClickListener(v -> {
            PopupMenu popupMenu = new PopupMenu(context, holder.ivMore);
            popupMenu.getMenuInflater().inflate(R.menu.email_options_menu, popupMenu.getMenu());


            popupMenu.setOnMenuItemClickListener(item -> {
                if (item.getItemId() == R.id.add_account) {
                    // Handle 'Add Account' action
                    int i2 = MainActivity.pos;
                    Intent intent = new Intent(context, LoginMultipleAccountActivity.class);
                    intent.putExtra("title", emailData.getName());
                    intent.putExtra("emailType", emailData.getName());
                    intent.putExtra("loginUrl", emailData.getUrl());
                    context.startActivity(intent);
                    Toast.makeText(context, "Add Account clicked", Toast.LENGTH_SHORT).show();
                    return true;
                } else if (item.getItemId() == R.id.remove) {
                    showDialog(context, position);
                    return true;

                } else {
                    return false;
                }
            });
            popupMenu.show();
        });


        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, LoginMultipleAccountActivity.class);
            intent.putExtra("title", emailData.getName());
            intent.putExtra("emailType", MainActivity.pos);
            intent.putExtra("loginUrl", emailData.getUrl());
            context.startActivity(intent);

        });
    }


    public void showDialog(Context activity, int position) {

        EmailData emailData = i.get(position);
        final Dialog dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.dialog_remove);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));


        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
        layoutParams.copyFrom(dialog.getWindow().getAttributes());
        layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT; // Width match parent
        dialog.getWindow().setAttributes(layoutParams);


        TextView btnCancel = (TextView) dialog.findViewById(R.id.btnCancel);
        TextView btnPositive = (TextView) dialog.findViewById(R.id.btnPositive);
        btnPositive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i.remove(position);
                notifyItemRemoved(position);

                // Update SharedPreferences to remove the email ID
                SharedPreferences sharedPreferences = context.getSharedPreferences("SelectedEmails", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                Set<String> selectedEmails = new HashSet<>(sharedPreferences.getStringSet("emails", new HashSet<>()));

                // Add the email ID to a separate "removed" set in SharedPreferences
                Set<String> removedDefaultEmails = new HashSet<>(sharedPreferences.getStringSet("removedDefaults", new HashSet<>()));
                removedDefaultEmails.add(String.valueOf(emailData.getId()));
                editor.putStringSet("removedDefaults", removedDefaultEmails);

                // If it's user-selected, also remove from "emails"
                selectedEmails.remove(String.valueOf(emailData.getId()));
                editor.putStringSet("emails", selectedEmails);
                editor.apply();
                Toast.makeText(context, "Account removed", Toast.LENGTH_SHORT).show();
                dialog.cancel();
            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();

    }

    @Override
    public int getItemCount() {
        return i.size();
    }

    public void updateEmailList(List<EmailData> newEmailDataList) {
        this.i.clear();
        this.i.addAll(newEmailDataList);
        notifyDataSetChanged();
        notifyDataSetChanged();
    }


    static class EmailViewHolder extends RecyclerView.ViewHolder {
        AppCompatImageView ivBackground, ivMore, checkbox;
        AppCompatTextView tvFirstChar, tvEmailName, tvGmailAccount;

        public EmailViewHolder(@NonNull View itemView) {
            super(itemView);
            ivBackground = itemView.findViewById(R.id.ivBackground);
            ivMore = itemView.findViewById(R.id.ivMore);
            checkbox = itemView.findViewById(R.id.checkbox);
            tvFirstChar = itemView.findViewById(R.id.tvFirstChar);
            tvEmailName = itemView.findViewById(R.id.tvEmailName);
            tvGmailAccount = itemView.findViewById(R.id.tvGmailAccount);
        }
    }

}

