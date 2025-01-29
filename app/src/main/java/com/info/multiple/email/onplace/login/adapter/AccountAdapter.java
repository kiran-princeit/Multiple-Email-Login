package com.info.multiple.email.onplace.login.adapter;

import static com.info.multiple.email.onplace.login.MainActivity.REQUEST_CODE_LOGIN;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;


import com.info.multiple.email.onplace.login.LoginMultipleAccountActivity;
import com.info.multiple.email.onplace.login.MainActivity;
import com.info.multiple.email.onplace.login.R;
import com.info.multiple.email.onplace.login.SettingActivity;
import com.info.multiple.email.onplace.login.Utills.EmailManager;
import com.info.multiple.email.onplace.login.WebViewActivity;
import com.info.multiple.email.onplace.login.adsprosimple.MobileAds;
import com.info.multiple.email.onplace.login.model.EmailData;

import java.util.List;
import java.util.Random;

public class AccountAdapter extends RecyclerView.Adapter<AccountAdapter.ViewHolder> {
    public static List<EmailData> accountList;
    private Context context;

    public AccountAdapter(List<EmailData> accountList, Context context) {
        this.accountList = accountList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.account_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        EmailData account = accountList.get(position);
        holder.tvFirstChar.setText(String.valueOf(account.getName().charAt(0)));
        holder.accountNameTextView.setText(account.getName());

        int[] color = holder.itemView.getContext().getResources().getIntArray(R.array.androidcolors);
        int randomAndroidColor = color[new Random().nextInt(color.length)];
        Drawable background = ContextCompat.getDrawable(holder.itemView.getContext(), R.drawable.rounded_rectangle);
        if (background != null) {
            background.setTint(randomAndroidColor); // Set the color dynamically
            holder.ivBackground.setBackground(background); // Set as background
        }

        holder.ivMoreAccount.setOnClickListener(v -> {

            PopupWindow popupWindow = new PopupWindow(context);
            View customView = LayoutInflater.from(context).inflate(R.layout.profile_menu, null);
            popupWindow.setContentView(customView);
            popupWindow.setFocusable(true);
            popupWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            LinearLayout menuItemEdit = customView.findViewById(R.id.llEdit);
            LinearLayout menuItemLogout = customView.findViewById(R.id.llRemove);
            menuItemEdit.setOnClickListener(view -> {

                showEditDialog(account, position);
                popupWindow.dismiss();
            });

            menuItemLogout.setOnClickListener(view -> {

                logoutAccount(account, position);
                popupWindow.dismiss();
            });

            popupWindow.showAsDropDown(holder.ivMoreAccount, 0, 0);
        });


        holder.itemView.setOnClickListener(v -> {
            String accountUrl = account.getUrl();
            Log.d("AccountAdapter", "Account URL: " + accountUrl); // Log the URL
            MobileAds.showInterstitial((Activity) context, () -> {
                if (accountUrl != null) {
                    Intent intent = new Intent(context, WebViewActivity.class);
                    intent.putExtra("url", accountUrl);
                    intent.putExtra("emailColor", LoginMultipleAccountActivity.emailColor);
                    intent.putExtra("isLogin", accountUrl.contains("login"));
                    context.startActivity(intent);
                } else {
                    Log.e("AccountAdapter", "URL is null for account: " + account.getName());
                    Toast.makeText(context, "Error: URL is null.", Toast.LENGTH_SHORT).show();
                }
            });

        });
    }

    @Override
    public int getItemCount() {
        return accountList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView accountNameTextView;
        TextView tvFirstChar;
        AppCompatImageView ivMoreAccount;
        RelativeLayout ivBackground;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            accountNameTextView = itemView.findViewById(R.id.tvAccountName);
            tvFirstChar = itemView.findViewById(R.id.tvFirstChar);
            ivBackground = itemView.findViewById(R.id.ivBackground);
            ivMoreAccount = itemView.findViewById(R.id.ivMoreAccount);
        }
    }

    private void showEditDialog(EmailData account, int position) {
        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.dialog_editprofile);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));


        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
        layoutParams.copyFrom(dialog.getWindow().getAttributes());
        layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT; // Width match parent
        dialog.getWindow().setAttributes(layoutParams);


        AppCompatEditText input = dialog.findViewById(R.id.etProfileName);
        input.setText(account.getName());
        AppCompatButton btnCancel = dialog.findViewById(R.id.btnCancelEdit);
        AppCompatButton btnSave = dialog.findViewById(R.id.btnedit);

        btnSave.setOnClickListener(v -> {
            String newName = input.getText().toString();
            if (!newName.isEmpty()) {
                account.setName(newName);
                notifyItemChanged(position);
                saveUpdatedAccount(context, account);
                dialog.dismiss();
            } else {
                Toast.makeText(context, "Please enter a valid name", Toast.LENGTH_SHORT).show();
            }
        });

        btnCancel.setOnClickListener(v -> dialog.dismiss());
        dialog.show();
    }

    private void saveUpdatedAccount(Context context, EmailData account) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(EmailManager.PREFS_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        // Retrieve and update the account data in SharedPreferences
        String savedAccounts = sharedPreferences.getString(EmailManager.SELECTED_EMAILS_KEY, "");
        StringBuilder updatedAccounts = new StringBuilder();

        String[] accountsArray = savedAccounts.split("\\|");
        for (String accountInfo : accountsArray) {
            String[] parts = accountInfo.split(",");
            if (parts[0].equals(String.valueOf(account.getId()))) {
                updatedAccounts.append(account.getId()).append(",").append(account.getName()).append(",").append(account.getUrl())
                        .append(",").append(account.getColor()).append(",").append(account.getEmailType());
            } else {
                updatedAccounts.append(accountInfo);
            }
            updatedAccounts.append("|");
        }

        editor.putString(EmailManager.SELECTED_EMAILS_KEY, updatedAccounts.toString());
        editor.apply();
    }

    private void logoutAccount(EmailData account, int position) {

        final Dialog dialog = new Dialog(context);
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
                accountList.remove(position);
                notifyItemRemoved(position);

                // Update SharedPreferences to remove the email ID
                SharedPreferences sharedPreferences = context.getSharedPreferences(EmailManager.PREFS_NAME, Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();

                String savedAccounts = sharedPreferences.getString(EmailManager.SELECTED_EMAILS_KEY, "");
                StringBuilder updatedAccounts = new StringBuilder();

                String[] accountsArray = savedAccounts.split("\\|");
                for (String accountInfo : accountsArray) {
                    String[] parts = accountInfo.split(",");
                    if (!parts[0].equals(String.valueOf(account.getId()))) {
                        updatedAccounts.append(accountInfo).append("|");
                    }
                }

                editor.putString(EmailManager.SELECTED_EMAILS_KEY, updatedAccounts.toString());
                editor.apply();


                // Notify user
                Toast.makeText(context, "Logged out successfully", Toast.LENGTH_SHORT).show();
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
}
