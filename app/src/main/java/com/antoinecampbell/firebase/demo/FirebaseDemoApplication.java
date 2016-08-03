package com.antoinecampbell.firebase.demo;


import android.app.Application;

import com.google.firebase.database.FirebaseDatabase;

public class FirebaseDemoApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
    }
}
