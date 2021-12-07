package com.example.o_tang;

import android.app.Application;

import io.realm.Realm;

public class OTang extends Application {
    public void onCreate() {
        super.onCreate();
        Realm.init(this);
    }
}
