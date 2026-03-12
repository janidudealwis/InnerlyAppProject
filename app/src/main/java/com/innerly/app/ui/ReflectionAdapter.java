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

    // දත්ත අලුත් කිරීම සඳහා method එක
    public void setReflections(List<Reflection> reflections) {
        this.reflections = reflections;
        notifyDataSetChanged();
import java.util.List;

public class ReflectionAdapter extends RecyclerView.Adapter<ReflectionAdapter.ViewHolder> {

    private final List<String> reflections;

    public ReflectionAdapter(List<String> reflections) {
        this.reflections = reflections;

    }

    @NonNull
    @Override
    public ReflectionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // item_reflection layout එක inflate කිරීම
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_reflection, parent, false);
        return new ReflectionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ReflectionViewHolder holder, int position) {
        // අදාළ ස්ථානයේ ඇති Reflection එක ලබාගෙන ViewHolder එකට සම්බන්ධ කිරීම
        Reflection reflection = reflections.get(position);
        holder.bind(reflection);
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(android.R.layout.simple_list_item_1, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.tvText.setText(reflections.get(position));

    }

    @Override
    public int getItemCount() {
        return reflections.size();
    }

    // ViewHolder Class එක
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
            // දිනය Format කිරීම (MMM dd, yyyy)
            SimpleDateFormat sdf = new SimpleDateFormat("MMM dd, yyyy", Locale.getDefault());
            String formattedDate = sdf.format(new Date(reflection.getDate()));

            tvDate.setText(formattedDate);
            tvMood.setText(reflection.getMood());
            tvPreview.setText(reflection.getText());
        }
    }
}
    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvText;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvText = itemView.findViewById(android.R.id.text1);
        }
    }
}



