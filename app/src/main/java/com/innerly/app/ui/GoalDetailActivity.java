package com.innerly.app.ui;

import android.app.AlertDialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.innerly.app.R;
import com.innerly.app.data.DatabaseHelper;
import java.util.ArrayList;
import java.util.List;

public class GoalDetailActivity extends AppCompatActivity {

    private DatabaseHelper db;
    private long goalId = -1;

    private TextView tvGoalTitle;
    private TextView tvGoalDescription;
    private RecyclerView rvReflections;
    private TextView tvNoReflections;
    private Button btnWriteNewReflection;
    private Button btnMarkAchieved;
    private Button btnEditGoal;
    private Button btnDeleteGoal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goal_detail);

        goalId = getIntent().getLongExtra("GOAL_ID", -1);
        if (goalId == -1) {
            Toast.makeText(this, "Goal not found", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        db = new DatabaseHelper(this);

        tvGoalTitle = findViewById(R.id.tvGoalTitle);
        tvGoalDescription = findViewById(R.id.tvGoalDescription);
        rvReflections = findViewById(R.id.rvReflections);
        tvNoReflections = findViewById(R.id.tvNoReflections);
        btnWriteNewReflection = findViewById(R.id.btnWriteNewReflection);
        btnMarkAchieved = findViewById(R.id.btnMarkAchieved);
        btnEditGoal = findViewById(R.id.btnEditGoal);
        btnDeleteGoal = findViewById(R.id.btnDeleteGoal);

        rvReflections.setLayoutManager(new LinearLayoutManager(this));

        loadGoalDetails();
        loadReflections();

        btnWriteNewReflection.setOnClickListener(v -> {
            Intent intent = new Intent(GoalDetailActivity.this, WriteReflectionActivity.class);
            intent.putExtra("GOAL_ID", goalId);
            startActivity(intent);
        });

        btnMarkAchieved.setOnClickListener(v -> {
            Intent intent = new Intent(GoalDetailActivity.this, GoalAchievedActivity.class);
            intent.putExtra("GOAL_ID", goalId);
            startActivity(intent);
        });

        btnEditGoal.setOnClickListener(v -> showEditDialog());

        btnDeleteGoal.setOnClickListener(v -> showDeleteConfirmation());
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadReflections();
    }

    private void loadGoalDetails() {
        Cursor cursor = db.getGoalById(goalId);
        if (cursor != null && cursor.moveToFirst()) {
            int titleIndex = cursor.getColumnIndex(DatabaseHelper.COL_GOAL_TITLE);
            int descIndex = cursor.getColumnIndex(DatabaseHelper.COL_GOAL_DESCRIPTION);

            if (titleIndex != -1) tvGoalTitle.setText(cursor.getString(titleIndex));
            if (descIndex != -1) tvGoalDescription.setText(cursor.getString(descIndex));

            cursor.close();
        }
    }

    private void loadReflections() {
        Cursor cursor = db.getReflectionsForGoal(goalId);
        List<String> reflections = new ArrayList<>();

        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    int textIndex = cursor.getColumnIndex(DatabaseHelper.COL_REF_TEXT);
                    if (textIndex != -1) {
                        reflections.add(cursor.getString(textIndex));
                    }
                } while (cursor.moveToNext());
            }
            cursor.close();
        }

        if (reflections.isEmpty()) {
            tvNoReflections.setVisibility(View.VISIBLE);
            rvReflections.setVisibility(View.GONE);
        } else {
            tvNoReflections.setVisibility(View.GONE);
            rvReflections.setVisibility(View.VISIBLE);
            ReflectionAdapter adapter = new ReflectionAdapter(reflections);
            rvReflections.setAdapter(adapter);
        }
    }

    private void showEditDialog() {
        final EditText etTitle = new EditText(this);
        final EditText etDescription = new EditText(this);

        etTitle.setHint("Goal title");
        etTitle.setText(tvGoalTitle.getText());
        etDescription.setHint("Why does this matter to you?");
        etDescription.setText(tvGoalDescription.getText());

        android.widget.LinearLayout layout = new android.widget.LinearLayout(this);
        layout.setOrientation(android.widget.LinearLayout.VERTICAL);
        int padding = (int) (16 * getResources().getDisplayMetrics().density);
        layout.setPadding(padding, padding, padding, padding);

        android.widget.LinearLayout.LayoutParams params = new android.widget.LinearLayout.LayoutParams(
                android.widget.LinearLayout.LayoutParams.MATCH_PARENT,
                android.widget.LinearLayout.LayoutParams.WRAP_CONTENT);
        params.setMargins(0, 0, 0, padding);
        etTitle.setLayoutParams(params);
        layout.addView(etTitle);
        layout.addView(etDescription);

        new AlertDialog.Builder(this)
                .setTitle("Edit Goal")
                .setView(layout)
                .setPositiveButton("Save", (dialog, which) -> {
                    String newTitle = etTitle.getText().toString().trim();
                    String newDesc = etDescription.getText().toString().trim();
                    if (newTitle.isEmpty()) {
                        Toast.makeText(this, "Title cannot be empty", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    boolean updated = db.updateGoal(goalId, newTitle, newDesc);
                    if (updated) {
                        tvGoalTitle.setText(newTitle);
                        tvGoalDescription.setText(newDesc);
                        Toast.makeText(this, "Goal updated", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(this, "Failed to update goal", Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton("Cancel", null)
                .show();
    }

    private void showDeleteConfirmation() {
        new AlertDialog.Builder(this)
                .setTitle("Delete Goal")
                .setMessage("Are you sure you want to delete this goal and all its reflections?")
                .setPositiveButton("Delete", (dialog, which) -> {
                    db.deleteGoal(goalId);
                    Toast.makeText(this, "Goal deleted", Toast.LENGTH_SHORT).show();
                    finish();
                })
                .setNegativeButton("Cancel", null)
                .show();
    }
}




