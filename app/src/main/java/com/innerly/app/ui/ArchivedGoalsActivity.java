package com.innerly.app.ui;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.innerly.app.R;
import com.innerly.app.data.DatabaseHelper; // SQLite Helper එක import කිරීම
import com.innerly.app.data.Goal;
import com.innerly.app.utils.SessionManager;
import java.util.ArrayList;
import java.util.List;

public class ArchivedGoalsActivity extends AppCompatActivity {

    private GoalAdapter adapter;
    private DatabaseHelper db;
    private SessionManager sessionManager;
    private RecyclerView rvArchived;
    private TextView tvEmptyState;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_archived_goals);


        db = new DatabaseHelper(this);
        sessionManager = new SessionManager(this);


        rvArchived = findViewById(R.id.rvArchivedGoals);
        tvEmptyState = findViewById(R.id.tvEmptyState);

        rvArchived.setLayoutManager(new LinearLayoutManager(this));


        adapter = new GoalAdapter(goal -> {
            Intent intent = new Intent(ArchivedGoalsActivity.this, GoalDetailActivity.class);
            intent.putExtra("GOAL_ID", goal.getId());
            startActivity(intent);
        });

        rvArchived.setAdapter(adapter);

        loadArchivedGoals();
    }

    private void loadArchivedGoals() {

        int userId = (int) sessionManager.getUserId();


        Cursor cursor = db.getArchivedGoals(userId);
        List<Goal> goals = new ArrayList<>();

        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {

                    Goal goal = new Goal();
                    // Goals table column names
                    int idIndex = cursor.getColumnIndex("id");
                    int titleIndex = cursor.getColumnIndex("title");
                    int contentIndex = cursor.getColumnIndex("description");

                    if (idIndex != -1) goal.setId(cursor.getInt(idIndex));
                    if (titleIndex != -1) goal.setTitle(cursor.getString(titleIndex));
                    if (contentIndex != -1) goal.setDescription(cursor.getString(contentIndex));

                    goals.add(goal);
                } while (cursor.moveToNext());
            }
            cursor.close();
        }


        updateUI(goals);
    }

    private void updateUI(List<Goal> goals) {
        if (goals.isEmpty()) {
            tvEmptyState.setVisibility(View.VISIBLE);
            rvArchived.setVisibility(View.GONE);
        } else {
            tvEmptyState.setVisibility(View.GONE);
            rvArchived.setVisibility(View.VISIBLE);
            adapter.setGoals(goals);
        }
    }
}