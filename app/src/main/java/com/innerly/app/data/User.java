package com.innerly.app.data;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "users")
public class User {

    @PrimaryKey(autoGenerate = true)
    private long id;

    private String name;
    private String email;
    private String password;

    // Default Constructor (Room සඳහා අනිවාර්ය වේ)
    public User() {
    }

    // දත්ත ඇතුළත් කිරීමේ පහසුව සඳහා Constructor එකක් (Optional)
    public User(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
    }

    // Getters and Setters (Room දත්ත කියවීමට සහ ලිවීමට මේවා භාවිතා කරයි)
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}