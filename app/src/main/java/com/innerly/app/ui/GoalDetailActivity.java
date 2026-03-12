package com.innerly.app.ui;

import android.app.AlertDialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.innerly.app.R;
import com.innerly.app.data.DatabaseHelper;
import com.innerly.app.data.Reflection;

import java.util.ArrayList;
import java.util.List;

public class GoalDetailActivity extends AppCompatActivity {

    private long goalId = -1;
    private DatabaseHelper db;
    private ReflectionAdapter adapter;
    private TextView tvTitle, tvDescription, tvNoReflections;
    private RecyclerView rvReflections;
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
        if (goalId == -1L) {
        if (goalId == -1) {
            Toast.makeText(this, "Goal not found", Toast.LENGTH_SHORT).show();

            finish();
            return;
        }

        db = new DatabaseHelper(this);

        tvTitle = findViewById(R.id.tvGoalTitle);
        tvDescription = findViewById(R.id.tvGoalDescription);
        tvNoReflections = findViewById(R.id.tvNoReflections);
        rvReflections = findViewById(R.id.rvReflections);

        Button btnWriteNew = findViewById(R.id.btnWriteNewReflection);
        Button btnMarkAchieved = findViewById(R.id.btnMarkAchieved);
        Button btnEdit = findViewById(R.id.btnEditGoal);
        Button btnDelete = findViewById(R.id.btnDeleteGoal);

        rvReflections.setLayoutManager(new LinearLayoutManager(this));
        adapter = new ReflectionAdapter();
        rvReflections.setAdapter(adapter);

        loadGoalDetails();

        btnWriteNew.setOnClickListener(v -> {
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
            // Goal එක completed ලෙස mark කිරීම
            if (db.markGoalAsCompleted(goalId)) {
                Toast.makeText(this, "Goal Marked as Completed!", Toast.LENGTH_SHORT).show();
                finish();
            }
        });

        btnEdit.setOnClickListener(v -> showEditDialog());
        btnDelete.setOnClickListener(v -> showDeleteConfirmation());
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
        // අලුත් DatabaseHelper method එක භාවිතා කිරීම
        Cursor cursor = db.getGoalById(goalId);

        if (cursor != null && cursor.moveToFirst()) {
            int titleIdx = cursor.getColumnIndex(DatabaseHelper.COL_GOAL_TITLE);
            int descIdx = cursor.getColumnIndex(DatabaseHelper.COL_GOAL_DESCRIPTION);

            if (titleIdx != -1) tvTitle.setText(cursor.getString(titleIdx));
            if (descIdx != -1) tvDescription.setText(cursor.getString(descIdx));
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
        // Reflections වගුවෙන් දත්ත ලබා ගැනීම
        Cursor cursor = db.getReflectionsForGoal(goalId);
        List<Reflection> reflections = new ArrayList<>();

        if (cursor != null && cursor.moveToFirst()) {
            do {
                Reflection ref = new Reflection();
                int idIdx = cursor.getColumnIndex(DatabaseHelper.COL_REF_ID);
                int textIdx = cursor.getColumnIndex(DatabaseHelper.COL_REF_TEXT);
                int moodIdx = cursor.getColumnIndex(DatabaseHelper.COL_REF_MOOD);
                int dateIdx = cursor.getColumnIndex(DatabaseHelper.COL_REF_DATE);

                if (idIdx != -1) ref.setId(cursor.getLong(idIdx));
                if (textIdx != -1) ref.setText(cursor.getString(textIdx));
                if (moodIdx != -1) ref.setMood(cursor.getString(moodIdx));
                if (dateIdx != -1) ref.setDate(cursor.getLong(dateIdx));
                
                reflections.add(ref);
            } while (cursor.moveToNext());
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
            adapter.setReflections(reflections);
            ReflectionAdapter adapter = new ReflectionAdapter(reflections);
            rvReflections.setAdapter(adapter);

        }
    }

    private void showEditDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Edit Goal");

        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setPadding(50, 40, 50, 10);

        final EditText etTitle = new EditText(this);
        etTitle.setHint("Goal Title");
        etTitle.setText(tvTitle.getText().toString());
        layout.addView(etTitle);

        final EditText etDesc = new EditText(this);
        etDesc.setHint("Why is this goal important?");
        etDesc.setText(tvDescription.getText().toString());
        layout.addView(etDesc);

        builder.setView(layout);

        builder.setPositiveButton("Update", (dialog, which) -> {
            String newTitle = etTitle.getText().toString().trim();
            String newDesc = etDesc.getText().toString().trim();

            if (!newTitle.isEmpty() && !newDesc.isEmpty()) {
                if (db.updateGoal(goalId, newTitle, newDesc)) {
                    tvTitle.setText(newTitle);
                    tvDescription.setText(newDesc);
                    Toast.makeText(this, "Goal updated successfully!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "Update failed!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        builder.setNegativeButton("Cancel", null);
        builder.show();
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
                    Toast.makeText(this, "Goal deleted!", Toast.LENGTH_SHORT).show();
                    finish();
                })
                .setNegativeButton("Cancel", null)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }
}
                    Toast.makeText(this, "Goal deleted", Toast.LENGTH_SHORT).show();
                    finish();
                })
                .setNegativeButton("Cancel", null)
                .show();
    }
}






