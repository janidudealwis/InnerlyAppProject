package com.innerly.app.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "Innerly.db";
    private static final int DATABASE_VERSION = 1;

    // 1. Users Table
    public static final String TABLE_USERS = "users";
    public static final String COL_USER_ID = "id";
    public static final String COL_USER_EMAIL = "email";
    public static final String COL_USER_PASSWORD = "password";

    // 2. Goals Table
    public static final String TABLE_GOALS = "goals";
    public static final String COL_GOAL_ID = "id";
    public static final String COL_GOAL_USER_ID = "user_id";
    public static final String COL_GOAL_TITLE = "title";
    public static final String COL_GOAL_DESCRIPTION = "description";
    public static final String COL_GOAL_MOOD = "mood";
    public static final String COL_GOAL_IS_COMPLETED = "is_completed";

    // 3. Reflections Table
    public static final String TABLE_REFLECTIONS = "reflections";
    public static final String COL_REF_ID = "id";
    public static final String COL_REF_GOAL_ID = "goal_id";
    public static final String COL_REF_TEXT = "text";
    public static final String COL_REF_MOOD = "mood";
    public static final String COL_REF_DATE = "date";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create Users Table
        String createUserTable = "CREATE TABLE " + TABLE_USERS + " (" +
                COL_USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL_USER_EMAIL + " TEXT UNIQUE, " +
                COL_USER_PASSWORD + " TEXT)";
        db.execSQL(createUserTable);

        // Create Goals Table
        String createGoalsTable = "CREATE TABLE " + TABLE_GOALS + " (" +
                COL_GOAL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL_GOAL_USER_ID + " INTEGER, " +
                COL_GOAL_TITLE + " TEXT, " +
                COL_GOAL_DESCRIPTION + " TEXT, " +
                COL_GOAL_MOOD + " TEXT, " +
                COL_GOAL_IS_COMPLETED + " INTEGER DEFAULT 0, " +
                "FOREIGN KEY(" + COL_GOAL_USER_ID + ") REFERENCES " + TABLE_USERS + "(" + COL_USER_ID + "))";
        db.execSQL(createGoalsTable);

        // Create Reflections Table
        String createReflectionsTable = "CREATE TABLE " + TABLE_REFLECTIONS + " (" +
                COL_REF_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL_REF_GOAL_ID + " INTEGER, " +
                COL_REF_TEXT + " TEXT, " +
                COL_REF_MOOD + " TEXT, " +
                COL_REF_DATE + " INTEGER, " +
                "FOREIGN KEY(" + COL_REF_GOAL_ID + ") REFERENCES " + TABLE_GOALS + "(" + COL_GOAL_ID + "))";
        db.execSQL(createReflectionsTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_REFLECTIONS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_GOALS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        onCreate(db);
    }

    // --- User Functions ---
    public boolean registerUser(String email, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_USER_EMAIL, email);
        values.put(COL_USER_PASSWORD, password);
        return db.insert(TABLE_USERS, null, values) != -1;
    }

    public int checkUser(String email, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT " + COL_USER_ID + " FROM " + TABLE_USERS +
                " WHERE " + COL_USER_EMAIL + "=? AND " + COL_USER_PASSWORD + "=?", new String[]{email, password});
        int userId = -1;
        if (cursor.moveToFirst()) { userId = cursor.getInt(0); }
        cursor.close();
        return userId;
    }

    // --- Goal Functions ---
    public long addGoal(int userId, String title, String description, String mood) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_GOAL_USER_ID, userId);
        values.put(COL_GOAL_TITLE, title);
        values.put(COL_GOAL_DESCRIPTION, description);
        values.put(COL_GOAL_MOOD, mood);
        return db.insert(TABLE_GOALS, null, values);
    }

    public Cursor getGoalsForUser(int userId) {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + TABLE_GOALS + " WHERE " + COL_GOAL_USER_ID + "=? AND " + COL_GOAL_IS_COMPLETED + " = 0",
                new String[]{String.valueOf(userId)});
    }

    public Cursor getGoalById(long goalId) {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.query(TABLE_GOALS, null, COL_GOAL_ID + " = ?", new String[]{String.valueOf(goalId)}, null, null, null);
    }

    public boolean updateGoal(long goalId, String title, String description) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_GOAL_TITLE, title);
        values.put(COL_GOAL_DESCRIPTION, description);
        return db.update(TABLE_GOALS, values, COL_GOAL_ID + " = ?", new String[]{String.valueOf(goalId)}) > 0;
    }

    public void deleteGoal(long goalId) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_REFLECTIONS, COL_REF_GOAL_ID + " = ?", new String[]{String.valueOf(goalId)});
        db.delete(TABLE_GOALS, COL_GOAL_ID + " = ?", new String[]{String.valueOf(goalId)});
    }

    public boolean markGoalAsCompleted(long goalId) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_GOAL_IS_COMPLETED, 1);
        return db.update(TABLE_GOALS, values, COL_GOAL_ID + " = ?", new String[]{String.valueOf(goalId)}) > 0;
    }

    // --- Reflection Functions ---
    public boolean addReflection(long goalId, String text, String mood) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_REF_GOAL_ID, goalId);
        values.put(COL_REF_TEXT, text);
        values.put(COL_REF_MOOD, mood);
        values.put(COL_REF_DATE, System.currentTimeMillis());
        return db.insert(TABLE_REFLECTIONS, null, values) != -1;
    }

    public Cursor getReflectionsForGoal(long goalId) {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.query(TABLE_REFLECTIONS, null, COL_REF_GOAL_ID + " = ?", new String[]{String.valueOf(goalId)}, null, null, COL_REF_DATE + " DESC");
    }
}