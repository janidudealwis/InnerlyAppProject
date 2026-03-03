package com.innerly.app.data;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "reflections")
public class Reflection {
    @PrimaryKey(autoGenerate = true)
    private long id;

    private long goalId;
    private String text;
    private String mood;
    private long date;

    // Default Constructor (Room සඳහා සහ Default අගයන් සඳහා අවශ්‍ය වේ)
    public Reflection() {
        this.date = System.currentTimeMillis();
    }

    // Constructor with parameters (පහසුව සඳහා)
    public Reflection(long goalId, String text, String mood) {
        this.goalId = goalId;
        this.text = text;
        this.mood = mood;
        this.date = System.currentTimeMillis();
    }

    // Getters and Setters
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getGoalId() {
        return goalId;
    }

    public void setGoalId(long goalId) {
        this.goalId = goalId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getMood() {
        return mood;
    }

    public void setMood(String mood) {
        this.mood = mood;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }
}