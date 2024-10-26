package com.example.multipleemaillogin.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.multipleemaillogin.LoginConfirmationActivity;
import com.example.multipleemaillogin.MainActivity;
import com.example.multipleemaillogin.R;
import com.example.multipleemaillogin.SelectMailActivity;
import com.example.multipleemaillogin.model.EmailData;

import java.util.ArrayList;
import java.util.List;

public class EmailAdapter extends RecyclerView.Adapter<EmailAdapter.EmailViewHolder> {
    private Context context;
    private List<EmailData> i;
//public List i;

    public EmailAdapter(Context context, List<EmailData> emailList) {
        this.context = context;
        this.i = emailList;
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
//        EmailData emailData = (EmailData) this.i.get(position);
        holder.tvFirstChar.setText(String.valueOf(emailData.getName().charAt(0)));

        holder.tvEmailName.setText(emailData.getName());
        holder.tvGmailAccount.setText(emailData.getUrl());
        holder.ivBackground.setBackgroundColor(emailData.getColor());


        holder.ivMore.setOnClickListener(v -> {
            PopupMenu popupMenu = new PopupMenu(context, holder.ivMore);
            popupMenu.getMenuInflater().inflate(R.menu.email_options_menu, popupMenu.getMenu());

            // Set click listener for popup menu items
            popupMenu.setOnMenuItemClickListener(item -> {
                if (item.getItemId() == R.id.add_account) {
                    // Handle 'Add Account' action
                    int i2 = MainActivity.pos;

                    Intent intent = new Intent(context, LoginConfirmationActivity.class);
                    intent.putExtra("title", emailData.getName());
                    intent.putExtra("emailType", i2);
                    intent.putExtra("loginUrl", emailData.getUrl());
                    intent.putExtra("isOpen", true);
                    context.startActivity(intent);

//                    MainActivity.o(emailData.a, emailData.b, emailData.c, true);
                    Toast.makeText(context, "Add Account clicked", Toast.LENGTH_SHORT).show();
                    return true;
                } else if (item.getItemId() == R.id.remove) {
                    // Handle 'Remove' action
//                    i.remove(position);
//                    notifyItemRemoved(position);
                    Toast.makeText(context, "Account removed", Toast.LENGTH_SHORT).show();
                    return true;
                } else {
                    return false;
                }
            });
            popupMenu.show();
        });
//        if (isSelectActivity) {
//            holder.ivMore.setVisibility(View.VISIBLE);
//            holder.checkbox.setVisibility(View.VISIBLE);
//            // Add logic to show checked/unchecked state
////            holder.checkbox.setImageResource(emailData.isSelected() ? R.drawable.ic_checked : R.drawable.ic_unchecked);
//        } else {
//            holder.checkbox.setVisibility(View.VISIBLE);
//            holder.ivMore.setVisibility(View.GONE);
//        }

//        holder.checkbox.setOnClickListener(view -> {
//
////
////            emailData.setSelected(!emailData.isSelected()); // Toggle selection state
////            notifyItemChanged(position); //
//
//            boolean contains = i.contains(String.valueOf(emailData.getName()));
//            List arrayList = i;
//            int i2 = emailData.getId();
//            if (contains) {
//                arrayList.remove(String.valueOf(i2));
//            } else {
//                arrayList.add(String.valueOf(i2));
//            }
////            if (arrayList.isEmpty()) {
////                int i3 = SelectMailActivity.d;
////                SelectMailActivity.btnOK.setVisibility(8);
////            } else {
////                int i4 = SelectMailActivity.d;
////                SelectMailActivity.btnOK.setVisibility(0);
////            }
//            if (arrayList.contains(String.valueOf(i2))) {
//                holder.checkbox.setImageResource(R.drawable.ic_checked);
//                return;
//            } else {
//                holder.checkbox.setImageResource(R.drawable.ic_unchecked);
//                return;
//            }
//        });


        holder.itemView.setOnClickListener(view -> {
            context.startActivity(new Intent(context, LoginConfirmationActivity.class));
        });
    }

    @Override
    public int getItemCount() {
        return i.size();
    }
    public void updateEmails(ArrayList<EmailData> updatedEmails) {
        this.i = updatedEmails;
        notifyDataSetChanged();
    }
    public void updateEmailList(List<EmailData> newEmailDataList) {
//        this.i = newEmailDataList;
        this.i.clear();
        this.i.addAll(newEmailDataList);
        notifyDataSetChanged();
        notifyDataSetChanged();


//        i.clear(); // Clear the existing list
//        notifyDataSetChanged(); // Notify the adapter that the data has changed
    }


    static class EmailViewHolder extends RecyclerView.ViewHolder {
        AppCompatImageView ivBackground, ivMore,checkbox;
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

