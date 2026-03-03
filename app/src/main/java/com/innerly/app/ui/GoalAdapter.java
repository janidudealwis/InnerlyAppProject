package com.innerly.app.ui;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.innerly.app.R;
import com.innerly.app.data.Goal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class GoalAdapter extends RecyclerView.Adapter<GoalAdapter.GoalViewHolder> {

    // Click එකක් හඳුනා ගැනීමට Interface එකක් සාදා ගැනීම
    public interface OnGoalClickListener {
        void onGoalClick(Goal goal);
    }

    private List<Goal> goals = new ArrayList<>();
    private final OnGoalClickListener listener;

    // Constructor
    public GoalAdapter(OnGoalClickListener listener) {
        this.listener = listener;
    }

    public void setGoals(List<Goal> newGoals) {
        this.goals = newGoals;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public GoalViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_goal, parent, false);
        return new GoalViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GoalViewHolder holder, int position) {
        Goal goal = goals.get(position);
        holder.bind(goal);

        // අයිතමය ක්ලික් කළ විට listener එක ක්‍රියාත්මක කිරීම
        holder.itemView.setOnClickListener(v -> {
            if (listener != null) {
                listener.onGoalClick(goal);
            }
        });
    }

    @Override
    public int getItemCount() {
        return goals.size();
    }

    // ViewHolder Class එක
    public static class GoalViewHolder extends RecyclerView.ViewHolder {
        private final TextView tvTitle;
        private final TextView tvDescription;
        private final TextView tvMood;
        private final TextView tvDate;

        public GoalViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tvGoalTitle);
            tvDescription = itemView.findViewById(R.id.tvGoalDescription);
            tvMood = itemView.findViewById(R.id.tvMoodLabel);
            tvDate = itemView.findViewById(R.id.tvUpdatedDate);
        }

        public void bind(Goal goal) {
            tvTitle.setText(goal.getTitle());
            tvDescription.setText(goal.getDescription());
            tvMood.setText(goal.getCurrentMood());

            SimpleDateFormat sdf = new SimpleDateFormat("MMM dd, yyyy", Locale.getDefault());
            String formattedDate = sdf.format(new Date(goal.getCreatedDate()));

            // String සම්පත් (Resources) ලබා ගැනීම
            String dateText = itemView.getContext().getString(R.string.goal_created_date, formattedDate);
            tvDate.setText(dateText);
        }
    }
}