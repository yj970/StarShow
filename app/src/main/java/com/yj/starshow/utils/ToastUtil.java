package com.yj.starshow.utils;

import android.widget.Toast;

import com.yj.starshow.APP;

public class ToastUtil {

    public static void show(String text) {
        Toast.makeText(APP.getApp(), text, Toast.LENGTH_SHORT).show();
    }
}
