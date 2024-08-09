package com.example.baseproject.imagebottompicker.model;

import android.content.Intent;

import com.example.baseproject.imagebottompicker.onResult.OnActivityResultListener;


public class ActivityRequest {

    private Intent intent;
    private OnActivityResultListener listener;

    public ActivityRequest(Intent intent,
                           OnActivityResultListener listener) {
        this.intent = intent;
        this.listener = listener;
    }

    public Intent getIntent() {
        return intent;
    }

    public OnActivityResultListener getListener() {
        return listener;
    }
}
