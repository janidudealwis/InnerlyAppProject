package com.innerly.app.ui;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class ReflectionAdapter extends RecyclerView.Adapter<ReflectionAdapter.ViewHolder> {

    private final List<String> reflections;

    public ReflectionAdapter(List<String> reflections) {
        this.reflections = reflections;
    }

    @NonNull
    @Override
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

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvText;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvText = itemView.findViewById(android.R.id.text1);
        }
    }
}

