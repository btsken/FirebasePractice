package com.biginnov.syncnote;

import android.app.Application;

import com.firebase.client.Firebase;

/**
 * Created by ken on 2015/12/11.
 */
public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Firebase.setAndroidContext(this);
    }
}
