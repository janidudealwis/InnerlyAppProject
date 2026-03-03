package com.innerly.app.ui;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import com.google.android.material.navigation.NavigationView;
import com.innerly.app.R;
import com.innerly.app.data.DatabaseHelper; // Import your DatabaseHelper here
import com.innerly.app.utils.SessionManager;

public class HomeActivity extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    private SessionManager sessionManager;
    private DatabaseHelper dbHelper; // Declare SQLite Helper

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // Initialize DatabaseHelper and SessionManager
        dbHelper = new DatabaseHelper(this);
        sessionManager = new SessionManager(this);

        // Get the ID of the logged-in user
        final int userId = (int) sessionManager.getUserId();

        // Connect Toolbar and Drawer
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawerLayout = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);

        // Setup Hamburger Icon (the button that opens the Menu)
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        // Initialize UI components
        final TextView tvUserName = findViewById(R.id.tvUserName);
        Button btnWriteReflection = findViewById(R.id.btnWriteReflection);
        Button btnMyGoals = findViewById(R.id.btnMyGoals);
        Button btnPastReflections = findViewById(R.id.btnPastReflections);

        // Display user details (via SQLite)
        // Note: Since you get the ID via checkUser in DatabaseHelper,
        // you can add a new method to dbHelper to fetch the user's email or other data if needed.
        // Currently displaying a simple welcome message:
        tvUserName.setText("Welcome Back!");

        // Action when a menu item is clicked (Drawer Menu)
        navigationView.setNavigationItemSelectedListener(item -> {
            int id = item.getItemId();
            if (id == R.id.nav_goals) {
                startActivity(new Intent(HomeActivity.this, MyGoalsActivity.class));
            } else if (id == R.id.nav_write_reflection) {
                startActivity(new Intent(HomeActivity.this, MyGoalsActivity.class));
            } else if (id == R.id.nav_past_reflections) {
                startActivity(new Intent(HomeActivity.this, ArchivedGoalsActivity.class));
            } else if (id == R.id.nav_logout) {
                logout();
            }
            drawerLayout.closeDrawers();
            return true;
        });

        // Button click listeners
        btnMyGoals.setOnClickListener(v -> startActivity(new Intent(this, MyGoalsActivity.class)));
        btnWriteReflection.setOnClickListener(v -> startActivity(new Intent(this, MyGoalsActivity.class)));
        btnPastReflections.setOnClickListener(v -> startActivity(new Intent(this, ArchivedGoalsActivity.class)));
    }

    private void logout() {
        sessionManager.logoutUser();
        Intent intent = new Intent(HomeActivity.this, LoginActivity.class); // Redirect to Login screen
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }
}
