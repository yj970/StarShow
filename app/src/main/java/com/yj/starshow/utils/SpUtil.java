package com.yj.starshow.utils;

import android.app.Activity;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.yj.starshow.APP;
import com.yj.starshow.bean.StarShow;
import com.yj.starshow.bean.StartShowList;

import java.util.ArrayList;


public class SpUtil {
    private static final String fileKey = "starShow";
    private static final String KEY_SUN = "KEY_SUN";
    private static final String KEY_MON = "KEY_MON";
    private static final String KEY_TUES = "KEY_TUES";
    private static final String KEY_WED = "KEY_WED";
    private static final String KEY_THUR = "KEY_THUR";
    private static final String KEY_FRI = "KEY_FRI";
    private static final String KEY_SAT = "KEY_SAT";


    public static final String NULL = null;

    /**
     * 使用SharedPreference保存对象
     */
    public static void save(int week, StarShow starShow) {
        SharedPreferences sharedPreferences = APP.getApp().getApplicationContext().getSharedPreferences(fileKey, Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        String key = getKey(week);
        StartShowList startShowList = get(week);
        if (startShowList == null) {
            startShowList = new StartShowList();
            startShowList.setList(new ArrayList<StarShow>());
        }
        startShowList.getList().add(starShow);
        String json = new Gson().toJson(startShowList);
        editor.putString(key, json);
        editor.commit();
    }

    /*
      * 使用SharedPreference保存对象
     */
    public static void save(int week, StartShowList startShowList) {
        SharedPreferences sharedPreferences = APP.getApp().getApplicationContext().getSharedPreferences(fileKey, Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        String key = getKey(week);
        String json = new Gson().toJson(startShowList);
        editor.putString(key, json);
        editor.commit();
    }

    /**
     * 获取SharedPreference保存的对象
     * @return object 返回根据key得到的对象
     */
    public static StartShowList get(int week) {
        SharedPreferences sharedPreferences = APP.getApp().getApplicationContext().getSharedPreferences(fileKey, Activity.MODE_PRIVATE);
        String key = getKey(week);
        String json = sharedPreferences.getString(key, NULL);
        return new Gson().fromJson(json, StartShowList.class);
    }

    private static String getKey(int week) {
        String key = "";
        switch (week) {
            case 0:
                key = KEY_SUN;
                break;
            case 1:
                key = KEY_MON;
                break;
            case 2:
                key = KEY_TUES;
                break;
            case 3:
                key = KEY_WED;
                break;
            case 4:
                key = KEY_THUR;
                break;
            case 5:
                key = KEY_FRI;
                break;
            case 6:
                key = KEY_SAT;
                break;
        }
        return key;
    }

    public static void delete(StarShow starShow) {
        int week = starShow.getWeek();
        StartShowList startShowList = get(week);
        for (StarShow ss : startShowList.getList()) {
            if (ss.getId().equals(starShow.getId())) {
                startShowList.getList().remove(ss);
                break;
            }
        }
        save(week, startShowList);
    }
}
