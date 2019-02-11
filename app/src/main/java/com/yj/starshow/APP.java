package com.yj.starshow;

import android.app.Application;

public class APP extends Application{
    static APP app;
    @Override
    public void onCreate() {
        super.onCreate();
        app = this;
    }

    public static APP getApp() {
        return app;
    }
}
