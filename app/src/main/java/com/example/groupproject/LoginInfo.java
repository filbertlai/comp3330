package com.example.groupproject;

import android.app.Application;

public class LoginInfo extends Application {
    private String user;

    public String getUser() {
        return user;
    }

    public void setUser(String str) {
        user = str;
    }
}
