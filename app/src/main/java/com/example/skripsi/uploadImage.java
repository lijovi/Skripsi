package com.example.skripsi;

import android.app.Application;

import com.cloudinary.android.MediaManager;

import java.util.Collections;

public class uploadImage extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        MediaManager.init(this, Collections.singletonMap("cloud_name", "davg2hvds"));
    }
}
