package com.yj.starshow.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yj.starshow.Constant;
import com.yj.starshow.R;
import com.yj.starshow.adapter.StarShowAdapter;
import com.yj.starshow.bean.StarShow;
import com.yj.starshow.bean.StartShowList;
import com.yj.starshow.dialog.MaterialDialog;
import com.yj.starshow.enums.WeekEnum;
import com.yj.starshow.event.RefreshEvent;
import com.yj.starshow.utils.SpUtil;
import com.yj.starshow.view.recyclerview.RecyclerItemDecoration;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class StarShowFragment extends Fragment {

    WeekEnum weekEnum;
    @BindView(R.id.rv)
    RecyclerView rv;
    Unbinder unbinder;
    StarShowAdapter adapter;
    StartShowList list = null;

    public static StarShowFragment newInstance(WeekEnum weekEnum) {
        Bundle args = new Bundle();
        StarShowFragment fragment = new StarShowFragment();
        args.putSerializable(Constant.WEEK, weekEnum);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_star_show, null, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        EventBus.getDefault().register(this);

        if (getArguments() != null) {
            this.weekEnum = (WeekEnum) getArguments().getSerializable(Constant.WEEK);
        }

        refresh();

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void acceptRefreshEvent(RefreshEvent event) {
        refresh();
    }

    private void refresh() {
        switch (weekEnum) {
            case SUN:
                list = SpUtil.get(0);
                break;
            case MON:
                list = SpUtil.get(1);
                break;
            case TUES:
                list = SpUtil.get(2);
                break;
            case WED:
                list = SpUtil.get(3);
                break;
            case THUR:
                list = SpUtil.get(4);
                break;
            case FRI:
                list = SpUtil.get(5);
                break;
            case SAT:
                list = SpUtil.get(6);
                break;
        }

        sort(list);

        adapter = new StarShowAdapter(list);
        rv.setLayoutManager(new LinearLayoutManager(getActivity()));
        rv.addItemDecoration(new RecyclerItemDecoration(getActivity()));
        rv.setAdapter(adapter);

        adapter.setiLongClickImpl(new StarShowAdapter.ILongClick() {
            @Override
            public void onLongClick(int position) {
                showDeleteDialog(position);
            }
        });
    }

    /**
     * 冒泡排序, 播放时间早的在前面
     *
     * @param list
     */
    private void sort(StartShowList list) {
        if (list == null) {
            return;
        }
        Collections.sort(list.getList(), new Comparator<StarShow>() {
            @Override
            public int compare(StarShow o1, StarShow o2) {
                long t1 = o1.getTime().getTime();
                long t2 = o2.getTime().getTime();
                long diff = t1 - t2;
                if (diff > 0) {
                    return 1;
                } else if (diff < 0) {
                    return -1;
                }
                // 相等
                return 0;
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    private void showDeleteDialog(final int position) {
        new MaterialDialog.Builder(getActivity())
                .setMessage("确定删除这个节目吗?")
                .setPositiveClickListener(new MaterialDialog.IPositiveClickListener() {
                    @Override
                    public void onPositiveClickListener() {
                        SpUtil.delete(list.getList().get(position));
                        EventBus.getDefault().post(new RefreshEvent());
                    }
                }).create().show();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
