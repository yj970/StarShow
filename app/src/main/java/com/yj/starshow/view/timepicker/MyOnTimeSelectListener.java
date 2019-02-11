package com.yj.starshow.view.timepicker;

import android.view.View;

import com.bigkoo.pickerview.listener.OnTimeSelectChangeListener;

import java.util.Date;

public interface MyOnTimeSelectListener{
    /**
     *
     * @param date
     * @param v
     * @param i 代表星期几，0=星期日，1=星期一，2=星期二，3=星期三，4=星期四，5=星期五，6=星期六
     */
    void onTimeSelect(Date date, View v, int i);
}
