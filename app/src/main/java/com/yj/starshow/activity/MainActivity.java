package com.yj.starshow.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;

import com.yj.starshow.R;
import com.yj.starshow.adapter.WeekAdapter;
import com.yj.starshow.enums.WeekEnum;
import com.yj.starshow.event.RefreshEvent;
import com.yj.starshow.fragment.StarShowFragment;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.rv_top)
    RecyclerView rvTop;
    @BindView(R.id.vp)
    ViewPager vp;
    WeekAdapter weekAdapter;
    FragmentPagerAdapter fragmentPagerAdapter;
    List<Fragment> fragments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        EventBus.getDefault().register(this);

        // TOP
        weekAdapter = new WeekAdapter(this);
        rvTop.setLayoutManager(new GridLayoutManager(this, 7));
        rvTop.setAdapter(weekAdapter);
        weekAdapter.setiClickImpl(new WeekAdapter.IClick() {
            @Override
            public void onClick(int position) {
                vp.setCurrentItem(position);
            }
        });

        // CONTENT
        fragments = new ArrayList<>();
        fragments.add(StarShowFragment.newInstance(WeekEnum.SUN));
        fragments.add(StarShowFragment.newInstance(WeekEnum.MON));
        fragments.add(StarShowFragment.newInstance(WeekEnum.TUES));
        fragments.add(StarShowFragment.newInstance(WeekEnum.WED));
        fragments.add(StarShowFragment.newInstance(WeekEnum.THUR));
        fragments.add(StarShowFragment.newInstance(WeekEnum.FRI));
        fragments.add(StarShowFragment.newInstance(WeekEnum.SAT));

        fragmentPagerAdapter = new FragmentPagerAdapter(getSupportFragmentManager()) {

            @Override
            public int getCount() {
                return fragments.size();
            }

            @Override
            public Fragment getItem(int position) {
                return fragments.get(position);
            }
        };
        vp.setAdapter(fragmentPagerAdapter);
        vp.setOffscreenPageLimit(7);

        // listener
        vp.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                weekAdapter.setSelectPosition(i);
                weekAdapter.notifyDataSetChanged();
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });

        // 切换到当前日期
        int nowWeek = getNowWeek();
        vp.setCurrentItem(nowWeek);
    }


    // 创建菜单
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.title, menu);
        return true;
    }

    // 获取今天是星期几

    /**
     * 代表星期几，0=星期日，1=星期一，2=星期二，3=星期三，4=星期四，5=星期五，6=星期六
     * @return
     */
    private int getNowWeek() {
        Calendar calendar;
        calendar = Calendar.getInstance();
        String week;
        week = calendar.get(calendar.DAY_OF_WEEK) - 1 + "";
        return Integer.parseInt(week);
    }

    //点击事件
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_settings) {
            AddStarShowActivity.startAddStarShowActivity(this);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void acceptRefreshEvent(RefreshEvent event) {
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
