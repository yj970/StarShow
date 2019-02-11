package com.yj.starshow.view.timepicker;

import android.view.View;

import com.bigkoo.pickerview.adapter.ArrayWheelAdapter;
import com.bigkoo.pickerview.adapter.NumericWheelAdapter;
import com.bigkoo.pickerview.utils.ChinaDate;
import com.bigkoo.pickerview.view.WheelTime;
import com.contrarywind.view.WheelView;
import com.yj.starshow.R;

import java.util.ArrayList;
import java.util.List;

public class MyWheelTime extends WheelTime{
    private WheelView wv_week;
    public MyWheelTime(View view, boolean[] type, int gravity, int textSize) {
        super(view, type, gravity, textSize);

        wv_week = (WheelView) view.findViewById(R.id.week);
        wv_week.setTextSize(textSize);
        List<String> list = new ArrayList<>();
        list.add("星期日");
        list.add("星期一");
        list.add("星期二");
        list.add("星期三");
        list.add("星期四");
        list.add("星期五");
        list.add("星期六");
        ArrayWheelAdapter adapter = new ArrayWheelAdapter(list);
        wv_week.setCyclic(false);
        wv_week.setAdapter(adapter);

    }


    /**
     * 代表星期几，0=星期日，1=星期一，2=星期二，3=星期三，4=星期四，5=星期五，6=星期六
     * @return
     */
    public int getWeek(){
       return wv_week.getCurrentItem();
    }
}
