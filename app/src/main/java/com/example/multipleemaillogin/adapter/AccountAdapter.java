package com.example.multipleemaillogin.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.multipleemaillogin.R;
import com.example.multipleemaillogin.WebViewActivity;
import com.example.multipleemaillogin.model.AccountData;
import com.example.multipleemaillogin.model.EmailData;

import java.util.List;

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
        holder.ivBackground.setBackgroundColor(account.getColor());

        holder.itemView.setOnClickListener(v -> {
            String accountUrl = account.getUrl();
            Log.d("AccountAdapter", "Account URL: " + accountUrl); // Log the URL

            if (accountUrl != null) {
                Intent intent = new Intent(context, WebViewActivity.class);
                intent.putExtra("url", accountUrl);
                context.startActivity(intent);
            } else {
                Log.e("AccountAdapter", "URL is null for account: " + account.getName());
                Toast.makeText(context, "Error: URL is null.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return accountList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView accountNameTextView;
        TextView tvFirstChar;
        AppCompatImageView ivBackground;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            accountNameTextView = itemView.findViewById(R.id.tvAccountName);
            tvFirstChar = itemView.findViewById(R.id.tvFirstChar);
            ivBackground = itemView.findViewById(R.id.ivBackground);
        }
    }
}
