package com.innerly.app.ui;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.innerly.app.R;
import com.innerly.app.data.DatabaseHelper;
import com.innerly.app.utils.SessionManager;

public class CreateGoalActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_goal);

        final DatabaseHelper db = new DatabaseHelper(this);
        final SessionManager sessionManager = new SessionManager(this);

        final EditText etGoalTitle = findViewById(R.id.etGoalTitle);
        final EditText etGoalWhy = findViewById(R.id.etGoalWhy);
        final RadioGroup rgMoods = findViewById(R.id.rgMoods);
        final Button btnSaveGoal = findViewById(R.id.btnSaveGoal);

        btnSaveGoal.setOnClickListener(v -> {
            String title = etGoalTitle.getText().toString().trim();
            String description = etGoalWhy.getText().toString().trim();
            int selectedMoodId = rgMoods.getCheckedRadioButtonId();

            if (title.isEmpty() || description.isEmpty() || selectedMoodId == -1) {
                Toast.makeText(this, "Please fill all fields and select a mood", Toast.LENGTH_SHORT).show();
                return;
            }

            RadioButton rbSelected = findViewById(selectedMoodId);
            String mood = rbSelected.getText().toString();
            int userId = (int) sessionManager.getUserId();

            // DatabaseHelper හි ඇති addGoal method එක භාවිතා කිරීම
            long isInserted = db.addGoal(userId, title, description, mood);

            if (isInserted != -1) {
                Toast.makeText(CreateGoalActivity.this, "Goal Saved!", Toast.LENGTH_SHORT).show();
                finish();
            } else {
                Toast.makeText(CreateGoalActivity.this, "Error saving goal!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}