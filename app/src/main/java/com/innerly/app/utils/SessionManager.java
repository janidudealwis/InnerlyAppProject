package com.innerly.app.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class SessionManager {
    private final SharedPreferences prefs;
    private static final String PREF_NAME = "innerly_prefs";
    private static final String KEY_USER_ID = "user_id";

    // Constructor
    public SessionManager(Context context) {
        this.prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    }

    /**
     * පරිශීලකයා ලොගින් වූ පසු User ID එක සුරැකීම
     */
    public void loginUser(long userId) {
        prefs.edit().putLong(KEY_USER_ID, userId).apply();
    }

    /**
     * පරිශීලකයා Logout කිරීම (ID එක ඉවත් කිරීම)
     */
    public void logoutUser() {
        prefs.edit().remove(KEY_USER_ID).apply();
    }

    /**
     * දැනට ලොගින් වී සිටින පරිශීලකයාගේ ID එක ලබා ගැනීම
     * කිසිවෙකු නොමැති නම් -1 ලබා දෙයි.
     */
    public long getUserId() {
        return prefs.getLong(KEY_USER_ID, -1L);
    }

    /**
     * පරිශීලකයා දැනටමත් ලොගින් වී ඇත්දැයි පරීක්ෂා කිරීම
     */
    public boolean isLoggedIn() {
        return getUserId() != -1L;
    }
}