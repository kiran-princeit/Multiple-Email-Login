package com.info.multiple.email.onplace.login.adapter;

import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.info.multiple.email.onplace.login.R;
import com.info.multiple.email.onplace.login.model.Language;

import java.util.List;

public class LanguageAdapter extends RecyclerView.Adapter<LanguageAdapter.LanguageViewHolder> {

    private List<Language> languages;
    private int selectedPosition = -1;

    public LanguageAdapter(List<Language> languages) {
        this.languages = languages;
    }

    @NonNull
    @Override
    public LanguageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.language_item, parent, false);
        return new LanguageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LanguageViewHolder holder, int position) {

        Language language = languages.get(position);
        holder.languageName.setText(language.getName());
        holder.languageCode.setText(language.getCode());

        if (selectedPosition == -1 && "en".equals(language.getCode())) {
            selectedPosition = position;  // Set the position to English
        }

        holder.radioButton.setChecked(position == selectedPosition);

        holder.ivFlags.setImageResource(language.getFlags());
//        Glide.with((Activity) context)
//                .load(arrayList[position])
//                .placeholder(R.drawable.ic_placeholder_portrait)
//                .centerCrop()
//                .into(holder.my_image_view);

        if (position == selectedPosition) {
            holder.itemView.setSelected(true);  // Set the item as selected
            holder.radioButton.setSelected(true);  // Set the item as selected
        } else {
            holder.itemView.setSelected(false); // Remove selection for others
            holder.radioButton.setSelected(false); // Remove selection for others
        }

        Log.e("TAG", "selectedPosition: " + selectedPosition);
        holder.itemView.setOnClickListener(v -> {
            selectedPosition = position;
            Log.d("LanguageAdapter", "Radio Button clicked. Selected Position: " + selectedPosition);
            notifyDataSetChanged(); // Refresh the UI
        });

        holder.radioButton.setOnClickListener(v -> {
            selectedPosition = position;  // Update selected position
            Log.d("LanguageAdapter", "Radio Button clicked. Selected Position: " + selectedPosition);
            notifyDataSetChanged(); // Refresh the UI
        });

    }

    @Override
    public int getItemCount() {
        return languages.size();
    }

    public String getSelectedLanguageCode() {
        if (selectedPosition != -1) {
            Log.d("LanguageAdapter", "Selected language code: " + languages.get(selectedPosition).getCode());
            return languages.get(selectedPosition).getCode();
        }
        Log.d("LanguageAdapter", "No language selected");
        return null; // Return null if no language is selected
    }

    public static class LanguageViewHolder extends RecyclerView.ViewHolder {
        TextView languageName, languageCode;
        RadioButton radioButton;
        ImageView ivFlags;

        public LanguageViewHolder(@NonNull View itemView) {
            super(itemView);
            languageName = itemView.findViewById(R.id.tvLanguages);
            languageCode = itemView.findViewById(R.id.tvLanguageCode);
            radioButton = itemView.findViewById(R.id.tvRadio);
            ivFlags = itemView.findViewById(R.id.ivFlags);
        }
    }
}

