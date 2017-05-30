package com.example.android.btp;

import android.app.Application;

import com.firebase.client.Firebase;

/**
 * Created by Sainadh Chilukamari on 9/9/2016.
 */
public class BTP extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        Firebase.setAndroidContext(this);
    }
}
