package com.innerly.app.ui;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.innerly.app.R;
import com.innerly.app.data.DatabaseHelper;
import com.innerly.app.data.Goal;
import com.innerly.app.utils.SessionManager;
import java.util.ArrayList;
import java.util.List;

public class MyGoalsActivity extends AppCompatActivity {

    private GoalAdapter adapter;
    private DatabaseHelper db;
    private SessionManager sessionManager;
    private RecyclerView rvGoals;
    private TextView tvEmptyState;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_goals);

        db = new DatabaseHelper(this);
        sessionManager = new SessionManager(this);

        rvGoals = findViewById(R.id.rvGoals);
        tvEmptyState = findViewById(R.id.tvEmptyState);
        Button btnNewGoal = findViewById(R.id.btnNewGoal);

        rvGoals.setLayoutManager(new LinearLayoutManager(this));

        adapter = new GoalAdapter(goal -> {
            Intent intent = new Intent(MyGoalsActivity.this, GoalDetailActivity.class);
            intent.putExtra("GOAL_ID", goal.getId());
            startActivity(intent);
        });
        rvGoals.setAdapter(adapter);

        btnNewGoal.setOnClickListener(v -> {
            startActivity(new Intent(MyGoalsActivity.this, CreateGoalActivity.class));
        });

        loadGoals();
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadGoals();
    }

    private void loadGoals() {
        int userId = (int) sessionManager.getUserId();

        Cursor cursor = db.getGoalsForUser(userId);
        List<Goal> goals = new ArrayList<>();

        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    Goal goal = new Goal();
                    int idIndex = cursor.getColumnIndex(DatabaseHelper.COL_GOAL_ID);
                    int titleIndex = cursor.getColumnIndex(DatabaseHelper.COL_GOAL_TITLE);
                    int contentIndex = cursor.getColumnIndex(DatabaseHelper.COL_GOAL_DESCRIPTION);

                    if (idIndex != -1) goal.setId(cursor.getLong(idIndex));
                    if (titleIndex != -1) goal.setTitle(cursor.getString(titleIndex));
                    if (contentIndex != -1) goal.setDescription(cursor.getString(contentIndex));

                    goals.add(goal);
                } while (cursor.moveToNext());
            }
            cursor.close();
        }

        if (goals.isEmpty()) {
            tvEmptyState.setVisibility(View.VISIBLE);
            rvGoals.setVisibility(View.GONE);
        } else {
            tvEmptyState.setVisibility(View.GONE);
            rvGoals.setVisibility(View.VISIBLE);
            adapter.setGoals(goals);
        }
    }
}// Updated for RecyclerView
