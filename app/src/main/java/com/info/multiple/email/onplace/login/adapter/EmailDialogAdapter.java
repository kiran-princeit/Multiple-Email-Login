package com.info.multiple.email.onplace.login.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.info.multiple.email.onplace.login.R;
import com.info.multiple.email.onplace.login.SelectMailActivity;
import com.info.multiple.email.onplace.login.adsprosimple.MobileAds;
import com.info.multiple.email.onplace.login.model.EmailData;

import java.util.List;

public class EmailDialogAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    private List<Object> items;
    private OnSelectionChangedListener selectionChangedListener;

    private static final int TYPE_EMAIL = 0;
    private static final int TYPE_AD = 1;

    public static final Object AD_PLACEHOLDER = new Object();

    public EmailDialogAdapter(List<Object> items, Context context, OnSelectionChangedListener listener) {
        this.items = items;
        this.context = context;
        this.selectionChangedListener = listener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == TYPE_AD) {
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_ads, parent, false);
            return new AdViewHolder(itemView);
        } else {
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_email_dialog, parent, false);
            return new EmailViewHolder(itemView);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (getItemViewType(position) == TYPE_EMAIL) {
            EmailViewHolder emailHolder = (EmailViewHolder) holder;
            EmailData emailData = (EmailData) items.get(position);

            emailHolder.tvFirstChar.setText(String.valueOf(emailData.getName().charAt(0)));
            emailHolder.tvEmailName.setText(emailData.getName());

            int color = emailData.getColor();
            Drawable background = ContextCompat.getDrawable(context, R.drawable.rounded_rectangle);
            if (background != null) {
                background.setTint(color);
                emailHolder.ivBackground.setBackground(background);
            }

            emailHolder.checkbox.setImageResource(emailData.isChecked() ? R.drawable.ic_checked : R.drawable.ic_unchecked);
            emailHolder.checkbox.setOnClickListener(view -> {
                emailData.setChecked(!emailData.isChecked());
                notifyItemChanged(position);
                if (selectionChangedListener != null) {
                    selectionChangedListener.onCheckedChanged(emailData, emailData.isChecked());
                }
                if (context instanceof SelectMailActivity) {
                    ((SelectMailActivity) context).updateOkButtonVisibility();
                }
            });
        } else {
            ((AdViewHolder) holder).onBind();
        }
    }

    @Override
    public int getItemViewType(int position) {
        return items.get(position) == AD_PLACEHOLDER ? TYPE_AD : TYPE_EMAIL;
    }

    @Override
    public int getItemCount() {
        return items.size();
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

    class AdViewHolder extends RecyclerView.ViewHolder {
        FrameLayout adContainer;
        ShimmerFrameLayout shimmerFrameLayout;

        public AdViewHolder(@NonNull View itemView) {
            super(itemView);
            adContainer = itemView.findViewById(R.id.nativeAdContainer);
            shimmerFrameLayout = itemView.findViewById(R.id.shimmerFrameLayout);
        }

        public void onBind() {
            MobileAds.showNativeBig(adContainer, shimmerFrameLayout, (Activity) context);
        }
    }


    public interface OnSelectionChangedListener {
        void onCheckedChanged(EmailData emailData, boolean isChecked);
    }
}

