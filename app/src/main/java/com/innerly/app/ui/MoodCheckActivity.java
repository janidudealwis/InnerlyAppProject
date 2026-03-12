package com.innerly.app.ui;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.widget.Button;
import android.widget.GridLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import com.innerly.app.R;
import com.innerly.app.utils.SessionManager;

public class MoodCheckActivity extends AppCompatActivity {

    private Button selectedMoodButton = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mood_check);

        // SessionManager එක සාදා ගැනීම
        final SessionManager sessionManager = new SessionManager(this);

        GridLayout moodGrid = findViewById(R.id.moodGrid);

        // Apply click listener to every Button in the grid
        for (int i = 0; i < moodGrid.getChildCount(); i++) {
            if (moodGrid.getChildAt(i) instanceof Button) {
                Button btn = (Button) moodGrid.getChildAt(i);
                btn.setOnClickListener(v -> selectMood(btn));
            }
        }

        // "Start Reflecting" බොත්තම ක්ලික් කළ විට සිදුවන දේ
        findViewById(R.id.btnStartReflecting).setOnClickListener(v -> {

            // පරිශීලකයා දැනටමත් ලොගින් වී ඇත්නම් HomeActivity වෙත යැවීම
            if (sessionManager.isLoggedIn()) {
                Intent intent = new Intent(MoodCheckActivity.this, HomeActivity.class);
                startActivity(intent);
            }
            // ලොගින් වී නොමැති නම් LoginActivity වෙත යැවීම
            else {
                Intent intent = new Intent(MoodCheckActivity.this, LoginActivity.class);
                startActivity(intent);
            }

            // මෙම Screen එක වසා දැමීම
            finish();
        });
    }

    private void selectMood(Button tapped) {
        // Reset previously selected button back to white
        if (selectedMoodButton != null) {
            selectedMoodButton.setBackgroundTintList(
                    ColorStateList.valueOf(ContextCompat.getColor(this, R.color.white)));
        }
        // Highlight the tapped button pink
        tapped.setBackgroundTintList(
                ColorStateList.valueOf(ContextCompat.getColor(this, R.color.primary_pink)));
        selectedMoodButton = tapped;
    }
}