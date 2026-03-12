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

public class WriteReflectionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write_reflection);

        long goalId = getIntent().getLongExtra("GOAL_ID", -1);
        if (goalId == -1L) {
        if (goalId == -1) {
            Toast.makeText(this, "Goal not found", Toast.LENGTH_SHORT).show();

            finish();
            return;
        }

        final DatabaseHelper dbHelper = new DatabaseHelper(this);

        final EditText etReflection = findViewById(R.id.etReflectionText);
        final RadioGroup rgMoods = findViewById(R.id.rgMoods);
        final Button btnSave = findViewById(R.id.btnSaveReflection);

        btnSave.setOnClickListener(v -> {
            String text = etReflection.getText().toString().trim();
            int selectedMoodId = rgMoods.getCheckedRadioButtonId();

            if (text.isEmpty() || selectedMoodId == -1) {
                Toast.makeText(this, R.string.error_write_something_mood, Toast.LENGTH_SHORT).show();
                return;
            }

            RadioButton rbSelected = findViewById(selectedMoodId);
            String mood = rbSelected.getText().toString();

            // නව DatabaseHelper method එක (addReflection) භාවිතා කිරීම
            boolean success = dbHelper.addReflection(goalId, text, mood);

            if (success) {
                Toast.makeText(WriteReflectionActivity.this, "Reflection Saved!", Toast.LENGTH_SHORT).show();
                finish();
            } else {
                Toast.makeText(WriteReflectionActivity.this, "Error saving reflection", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
        final DatabaseHelper db = new DatabaseHelper(this);
        final EditText etReflectionText = findViewById(R.id.etReflectionText);
        final RadioGroup rgMoods = findViewById(R.id.rgMoods);
        final Button btnSaveReflection = findViewById(R.id.btnSaveReflection);

        btnSaveReflection.setOnClickListener(v -> {
            String text = etReflectionText.getText().toString().trim();
            int selectedMoodId = rgMoods.getCheckedRadioButtonId();

            if (text.isEmpty()) {
                Toast.makeText(this, "Please write your reflection", Toast.LENGTH_SHORT).show();
                return;
            }

            String mood = "";
            if (selectedMoodId != -1) {
                RadioButton rbSelected = findViewById(selectedMoodId);
                mood = rbSelected.getText().toString();
            }

            boolean saved = db.addReflection(goalId, text, mood);
            if (saved) {
                Toast.makeText(this, "Reflection saved!", Toast.LENGTH_SHORT).show();
                finish();
            } else {
                Toast.makeText(this, "Error saving reflection", Toast.LENGTH_SHORT).show();
            }
        });
    }
}



