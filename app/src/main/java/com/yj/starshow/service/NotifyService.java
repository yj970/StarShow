package com.yj.starshow.service;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Build;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;

import com.yj.starshow.APP;
import com.yj.starshow.R;
import com.yj.starshow.activity.MainActivity;
import com.yj.starshow.bean.StarShow;
import com.yj.starshow.bean.StartShowList;
import com.yj.starshow.utils.CommonUtil;
import com.yj.starshow.utils.SpUtil;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class NotifyService extends Service{
    int notification_ID = 0;
    List<String> alreadyNotifyStarShowId = new ArrayList<>();
    public static final String PRIMARY_CHANNEL = "default";

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        // startService，不会调用此方法
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                int nowWeek = CommonUtil.getNowWeek();
                StartShowList starShowList = SpUtil.get(nowWeek);
                SimpleDateFormat formatter = new SimpleDateFormat("HH:mm");
                Date nowDate = new Date();
                String nowStr = formatter.format(nowDate);
                Date realDate = null;
                try {
                     realDate = formatter.parse(nowStr);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                if (starShowList != null && realDate != null) {
                    for (StarShow ss : starShowList.getList()) {
                        String ssStr = formatter.format(ss.getTime());
                        Date ssDate = null;
                        try {
                            ssDate = formatter.parse(ssStr);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        if (ssDate != null) {
                            long diffTime = ssDate.getTime() - realDate.getTime();
                            if (diffTime >= 0 && diffTime <= 1000*60*10 && !alreadyNotifyStarShowId.contains(ss.getId())) {
                                // 小于十分钟
                                showNotify(ss);
                            }
                        }
                    }
                }
            }
        }, 0,1000*60*3);// 3分钟一次
        return super.onStartCommand(intent, flags, startId);
    }

    // 通知栏提醒
    private void showNotify(StarShow starShow) {
        alreadyNotifyStarShowId.add(starShow.getId());
        notification_ID++;
        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm");
        String time = formatter.format(starShow.getTime());
        NotificationManager manager  = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        Intent intent = new Intent(this,MainActivity.class);
        PendingIntent pintent = PendingIntent.getActivity(this,0,intent,0);
        //判断是否是8.0Android.O
        NotificationCompat.Builder builder = null;
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel chan1 = new NotificationChannel(PRIMARY_CHANNEL,
                    "Primary Channel", NotificationManager.IMPORTANCE_HIGH);
            // 以下设置，部分手机（miui）要在系统设置中打开，才生效！
            // 设置通知出现时的闪灯（如果 android 设备支持的话）
            chan1.enableLights(true);
            chan1.setLightColor(Color.GREEN);
            // 设置通知出现时的震动（如果 android 设备支持的话）
            chan1.enableVibration(true);
            chan1.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
            chan1.setLockscreenVisibility(Notification.VISIBILITY_PRIVATE);
            manager.createNotificationChannel(chan1);
            builder = new NotificationCompat.Builder(getBaseContext(), PRIMARY_CHANNEL);
        } else {
             builder = new NotificationCompat.Builder(this);
        }
        builder.setSmallIcon(R.mipmap.iiicon);//设置图标
        builder.setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.iiicon));
//        builder.setTicker("您有一条信息，待查收");//设置手机状态栏提示
        builder.setWhen(System.currentTimeMillis());//时间
        builder.setContentTitle("<"+starShow.getName()+"> 即将播出");//标题
        builder.setContentText("播出时间  "+time);//通知内容
        builder.setContentIntent(pintent);//点击后的意图
        builder.setDefaults(Notification.DEFAULT_ALL);//给通知设置震动，声音，和提示灯三种效果，不过要记得申请权限
        builder.setAutoCancel(true);// 点击消失
        Notification notification = builder.build(); //4.1版本以上用这种方法
        //builder.getNotification();   //4.1版本以下用这种方法
        manager.notify(notification_ID,notification);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
