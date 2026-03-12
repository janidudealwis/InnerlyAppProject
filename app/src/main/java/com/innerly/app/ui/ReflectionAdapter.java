package com.innerly.app.ui;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.innerly.app.R;
import com.innerly.app.data.Reflection;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class ReflectionAdapter extends RecyclerView.Adapter<ReflectionAdapter.ReflectionViewHolder> {

    private List<Reflection> reflections = new ArrayList<>();

    // Call this to refresh the list with new data
    public void setReflections(List<Reflection> reflections) {
        this.reflections = reflections;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ReflectionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate the item layout for each reflection card
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_reflection, parent, false);
        return new ReflectionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ReflectionViewHolder holder, int position) {
        // Grab the reflection at this position and bind it to the view
        Reflection reflection = reflections.get(position);
        holder.bind(reflection);
    }

    @Override
    public int getItemCount() {
        return reflections.size();
    }

    // ViewHolder class that holds references to the card's views
    public static class ReflectionViewHolder extends RecyclerView.ViewHolder {
        private final TextView tvDate;
        private final TextView tvMood;
        private final TextView tvPreview;

        public ReflectionViewHolder(@NonNull View itemView) {
            super(itemView);
            tvDate = itemView.findViewById(R.id.tvReflectionDate);
            tvMood = itemView.findViewById(R.id.tvReflectionMood);
            tvPreview = itemView.findViewById(R.id.tvReflectionPreview);
        }

        public void bind(Reflection reflection) {
            // Format the date nicely (e.g. Mar 12, 2026)
            SimpleDateFormat sdf = new SimpleDateFormat("MMM dd, yyyy", Locale.getDefault());
            String formattedDate = sdf.format(new Date(reflection.getDate()));

            tvDate.setText(formattedDate);
            tvMood.setText(reflection.getMood());
            tvPreview.setText(reflection.getText());
        }
    }
}
