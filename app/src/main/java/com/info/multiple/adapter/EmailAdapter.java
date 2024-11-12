package com.info.multiple.adapter;

import static com.info.multiple.email.onplace.login.MainActivity.REQUEST_CODE_LOGIN;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.hkappstech.adsprosimple.MobileAds;
import com.info.multiple.email.onplace.login.LoginMultipleAccountActivity;
import com.info.multiple.email.onplace.login.MainActivity;
import com.info.multiple.email.onplace.login.R;
import com.info.multiple.email.onplace.login.model.EmailData;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class EmailAdapter extends RecyclerView.Adapter<EmailAdapter.EmailViewHolder> {
    private Context context;
    private List<EmailData> i;
    MainActivity mainActivity;
    private int accountCount;

    public EmailAdapter(Context context, List<EmailData> emailList, MainActivity mainActivity) {
        this.context = context;
        this.i = emailList;
        this.mainActivity = mainActivity;
    }

    public void updateAccountCount(int count) {
        this.accountCount = count;
        notifyDataSetChanged(); // Notify the adapter to refresh the view if needed
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


        int color = emailData.getColor();
        Drawable background = ContextCompat.getDrawable(holder.itemView.getContext(), R.drawable.rounded_rectangle);
        if (background != null) {
            background.setTint(color); // Set the color dynamically
            holder.ivBackground.setBackground(background); // Set as background
        }


        holder.ivMore.setOnClickListener(v -> {

            PopupWindow popupWindow = new PopupWindow(context);
            View customView = LayoutInflater.from(context).inflate(R.layout.main_menu, null);
            popupWindow.setContentView(customView);
            popupWindow.setFocusable(true);
            popupWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

            LinearLayout menuItemAdd = customView.findViewById(R.id.llAddAccount);
            LinearLayout menuItemRemove = customView.findViewById(R.id.llRemove);
            menuItemAdd.setOnClickListener(view -> {

                Intent intent = new Intent(context, LoginMultipleAccountActivity.class);
                intent.putExtra("title", emailData.getName());
                intent.putExtra("emailType", emailData.getName());
                intent.putExtra("loginUrl", emailData.getUrl());
                context.startActivity(intent);
                popupWindow.dismiss();

            });

            menuItemRemove.setOnClickListener(view -> {

                showDialog(context, position);
                popupWindow.dismiss();
            });

            popupWindow.showAsDropDown(holder.ivMore, 0, 0);
        });

        holder.itemView.setOnClickListener(v -> {
            MobileAds.showInterstitial((Activity) context, () -> {
                Intent intent = new Intent(context, LoginMultipleAccountActivity.class);
                intent.putExtra("title", emailData.getName());
                intent.putExtra("loginUrl", emailData.getUrl());
                intent.putExtra("emailColor", emailData.getColor());
//            context.startActivity(intent);
                ((Activity) context).startActivityForResult(intent, REQUEST_CODE_LOGIN);
            });

        });

        holder.tvGmailAccount.setText("Account Count: " + accountCount);
//        Log.e("TAG", "onBindViewHolder: "+accountCount );

    }


    public void showDialog(Context activity, int position) {

        EmailData emailDatas = i.get(position);
        final Dialog dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.dialog_remove);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));


        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
        layoutParams.copyFrom(dialog.getWindow().getAttributes());
        layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT; // Width match parent
        dialog.getWindow().setAttributes(layoutParams);


        AppCompatButton btnCancel = (AppCompatButton) dialog.findViewById(R.id.btnCancel);
        AppCompatButton btnPositive = (AppCompatButton) dialog.findViewById(R.id.btnPositive);

        btnPositive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (position >= 0 && position < i.size()) {
                    i.remove(position);

                    // Update RecyclerView
                    notifyDataSetChanged();

                    // Update SharedPreferences to remove the email ID
                    SharedPreferences sharedPreferences = context.getSharedPreferences("SelectedEmails", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    Set<String> selectedEmails = new HashSet<>(sharedPreferences.getStringSet("emails", new HashSet<>()));

                    Set<String> removedDefaultEmails = new HashSet<>(sharedPreferences.getStringSet("removedDefaults", new HashSet<>()));
                    removedDefaultEmails.add(String.valueOf(emailDatas.getId()));
                    editor.putStringSet("removedDefaults", removedDefaultEmails);

                    selectedEmails.remove(String.valueOf(emailDatas.getId()));
                    editor.putStringSet("emails", selectedEmails);
                    editor.apply();

                    Toast.makeText(context, "Account removed", Toast.LENGTH_SHORT).show();
                    dialog.cancel();
                } else {
                    Toast.makeText(context, "Item does not exist", Toast.LENGTH_SHORT).show();
                    dialog.cancel();
                }
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

    }

//    public void updateEmailList(List<EmailData> updatedEmailList) {
//        this.i = updatedEmailList;  // Update the list
//        notifyDataSetChanged();  // Notify RecyclerView to refresh
//    }


    static class EmailViewHolder extends RecyclerView.ViewHolder {
        AppCompatImageView ivMore, checkbox;
        RelativeLayout ivBackground;
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

   /* btnPositive.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (position >= 0 && position < i.size()) {  // Check bounds
                i.remove(position);
                notifyItemRemoved(position);

                // Update SharedPreferences to remove the email ID
                SharedPreferences sharedPreferences = context.getSharedPreferences("SelectedEmails", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                Set<String> selectedEmails = new HashSet<>(sharedPreferences.getStringSet("emails", new HashSet<>()));

                // Add the email ID to a separate "removed" set in SharedPreferences
                Set<String> removedDefaultEmails = new HashSet<>(sharedPreferences.getStringSet("removedDefaults", new HashSet<>()));
                removedDefaultEmails.add(String.valueOf(emailDatas.getId()));
                editor.putStringSet("removedDefaults", removedDefaultEmails);

                // If it's user-selected, also remove from "emails"
                selectedEmails.remove(String.valueOf(emailDatas.getId()));
                editor.putStringSet("emails", selectedEmails);
                editor.apply();

                Toast.makeText(context, "Account removed", Toast.LENGTH_SHORT).show();
                dialog.cancel();
            } else {
                Toast.makeText(context, "Item does not exist", Toast.LENGTH_SHORT).show();
                dialog.cancel();
            }
        }
    });*/


}

