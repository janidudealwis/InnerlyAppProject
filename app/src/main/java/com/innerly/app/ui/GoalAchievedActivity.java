package com.innerly.app.ui;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.innerly.app.R;
import com.innerly.app.data.DatabaseHelper;

public class GoalAchievedActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goal_achieved);

        long goalId = getIntent().getLongExtra("GOAL_ID", -1);
        if (goalId == -1L) {
            finish();
            return;
        }

        final DatabaseHelper dbHelper = new DatabaseHelper(this);
        final EditText etLessons = findViewById(R.id.etLessonsLearned);
        final Button btnComplete = findViewById(R.id.btnCompleteGoal);

        btnComplete.setOnClickListener(v -> {
            String lessons = etLessons.getText().toString().trim();

            // 1. Goal එක 'Completed' ලෙස Update කිරීම
            boolean success = dbHelper.markGoalAsCompleted(goalId);

            if (success) {
                // 2. යම් පාඩමක් ලියා ඇත්නම් එය Reflection එකක් ලෙස සුරැකීම
                if (!lessons.isEmpty()) {
                    dbHelper.addReflection(goalId, "Lessons Learned: " + lessons, "Proud");
                }

                Toast.makeText(this, "Goal Marked as Completed!", Toast.LENGTH_SHORT).show();

                // 3. HomeActivity එකට යාම
                Intent intent = new Intent(GoalAchievedActivity.this, HomeActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
            } else {
                Toast.makeText(this, "Error updating goal!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}