package com.info.multiple.email.onplace.login.adapter;

import android.os.Build;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.info.multiple.email.onplace.login.R;
import com.info.multiple.email.onplace.login.model.OnboardingItem;

import java.util.List;

public class OnboardingAdapter  extends RecyclerView.Adapter<OnboardingAdapter.OnboardingViewHolder> {

    private final List<OnboardingItem> onboardingItems;

    public OnboardingAdapter(List<OnboardingItem> onboardingItems) {
        this.onboardingItems = onboardingItems;
    }

    @NonNull
    @Override
    public OnboardingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_onboarding, parent, false);
        return new OnboardingViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OnboardingViewHolder holder, int position) {
        holder.bind(onboardingItems.get(position));
    }

    @Override
    public int getItemCount() {
        return onboardingItems.size();
    }

    static class OnboardingViewHolder extends RecyclerView.ViewHolder {

        private final ImageView imageView;
        private final TextView titleTextView;
        private final AppCompatTextView descriptionTextView;

        public OnboardingViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.image_onboarding);
            titleTextView = itemView.findViewById(R.id.text_title);
            descriptionTextView = itemView.findViewById(R.id.text_description);
        }

        public void bind(OnboardingItem onboardingItem) {
            imageView.setImageResource(onboardingItem.getImage());
            titleTextView.setText(onboardingItem.getTitle());
//            descriptionTextView.setText(onboardingItem.getDescription());
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                descriptionTextView.setText(Html.fromHtml(onboardingItem.getDescription(),Html.FROM_HTML_MODE_LEGACY));
            }else {
                descriptionTextView.setText(Html.fromHtml(onboardingItem.getDescription()));
            }
        }
    }
}
