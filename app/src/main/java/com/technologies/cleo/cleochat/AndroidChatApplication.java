package com.technologies.cleo.cleochat;

import android.app.Application;

import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by Pepe on 10/11/2016.
 */

public class AndroidChatApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        setupFirebase();
    }

    void setupFirebase() {
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
        FirebaseDatabase.getInstance().getReference();
    }
}
