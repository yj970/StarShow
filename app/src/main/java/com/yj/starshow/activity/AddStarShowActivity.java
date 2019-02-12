package com.yj.starshow.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.TimePickerView;
import com.yj.starshow.R;
import com.yj.starshow.adapter.AddStarShowAdapter;
import com.yj.starshow.bean.StarShow;
import com.yj.starshow.event.RefreshEvent;
import com.yj.starshow.utils.SpUtil;
import com.yj.starshow.utils.ToastUtil;
import com.yj.starshow.view.timepicker.MyOnTimeSelectListener;
import com.yj.starshow.view.timepicker.MyTimePickerBuilder;
import com.yj.starshow.view.timepicker.MyTimePickerView;

import org.greenrobot.eventbus.EventBus;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AddStarShowActivity extends Activity {

    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.et_name)
    EditText etName;
    @BindView(R.id.tv_time)
    TextView tvTime;
    @BindView(R.id.ll_time)
    LinearLayout llTime;
    @BindView(R.id.tv_ok)
    TextView tvOk;
    @BindView(R.id.rv_platform)
    RecyclerView rvPlatform;
    AddStarShowAdapter platformAdapter;
    @BindView(R.id.rv_type)
    RecyclerView rvType;
    AddStarShowAdapter typeAdapter;
    public int myWeek = -1;// 星期几播出
    public Date myDate;// 播出时间


    public static void startAddStarShowActivity(Context context) {
        context.startActivity(new Intent(context, AddStarShowActivity.class));
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_star_show);
        ButterKnife.bind(this);

        // title
        tvTitle.setText("添加节目");
        // content
        List<String> platformList = new ArrayList<>();
        platformList.add("腾讯视频");
        platformList.add("爱奇艺");
        platformList.add("bilibili");
        platformList.add("优酷");
        platformList.add("AcFun");
        platformList.add("");
        platformAdapter = new AddStarShowAdapter(platformList, this);
        rvPlatform.setLayoutManager(new GridLayoutManager(this, 4));
        rvPlatform.setAdapter(platformAdapter);

        List<String> typeList = new ArrayList<>();
        typeList.add("动漫");
        typeList.add("纪录片");
        typeList.add("电视剧");
        typeList.add("科普知识");
        typeList.add("测评");
        typeList.add("");
        typeAdapter = new AddStarShowAdapter(typeList, this);
        rvType.setLayoutManager(new GridLayoutManager(this, 4));
        rvType.setAdapter(typeAdapter);

    }

    @OnClick(R.id.iv_back)
    public void onViewClicked() {
        finish();
    }

    @OnClick(R.id.ll_time)
    public void onViewClicked1() {
        // 隐藏软键盘
        InputMethodManager inputMethodManager = (InputMethodManager) this.getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(tvOk.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);

        //时间选择器
        MyTimePickerView pvTime = new MyTimePickerBuilder(AddStarShowActivity.this, new MyOnTimeSelectListener() {

            @Override
            public void onTimeSelect(Date date, View v, int week) {
                myWeek = week;// 星期几播出
                myDate = date;// 播出时间
                SimpleDateFormat formatter = new SimpleDateFormat("HH:mm");
                String text = "";
                switch (week) {
                    case 0:
                        text = "星期日";
                        break;
                    case 1:
                        text = "星期一";
                        break;
                    case 2:
                        text = "星期二";
                        break;
                    case 3:
                        text = "星期三";
                        break;
                    case 4:
                        text = "星期四";
                        break;
                    case 5:
                        text = "星期五";
                        break;
                    case 6:
                        text = "星期六";
                        break;
                }
                tvTime.setText(text+" "+formatter.format(date));
            }
        }).setType(new boolean[]{false, false, false, true, true, false})
                .setSubmitColor(getResources().getColor(R.color.color_yellow))
                .setCancelColor(getResources().getColor(R.color.color_999)).
                        build();
        pvTime.show();
    }

    @OnClick(R.id.tv_ok)
    public void onViewClicked2() {
        if (TextUtils.isEmpty(etName.getText().toString())) {
            ToastUtil.show("请输入节目名称!");
            return;
        }
        if (tvTime.getText().toString().equals("请选择")) {
            ToastUtil.show("请选择播出时间!");
            return;
        }
        if (TextUtils.isEmpty(typeAdapter.getSelectText())) {
            ToastUtil.show("请选择节目类型!");
            return;
        }
        if (TextUtils.isEmpty(platformAdapter.getSelectText())) {
            ToastUtil.show("请选择播出平台!");
            return;
        }

        // save
        StarShow starShow = new StarShow();
        starShow.setId(UUID.randomUUID().toString());
        starShow.setName(etName.getText().toString());
        starShow.setTime(myDate);
        starShow.setWeek(myWeek);
        starShow.setPlatform(platformAdapter.getSelectText());
        starShow.setType(typeAdapter.getSelectText());

        SpUtil.save(myWeek, starShow);

        EventBus.getDefault().post(new RefreshEvent());

        finish();
    }
}
