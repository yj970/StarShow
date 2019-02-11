package com.yj.starshow.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yj.starshow.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class WeekAdapter extends RecyclerView.Adapter<WeekAdapter.ViewHolder>{

    private int selectPosition = 0;
    private Context context;
    private IClick iClickImpl;

    public WeekAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_week, null, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int i) {
        if (selectPosition == i) {
            viewHolder.itemView.setBackgroundColor(context.getResources().getColor(R.color.color_yellow));
            viewHolder.tv.setTextColor(context.getResources().getColor(R.color.color_while));
        } else {
            viewHolder.itemView.setBackgroundColor(context.getResources().getColor(R.color.color_while));
            viewHolder.tv.setTextColor(context.getResources().getColor(R.color.color_666));
        }
        String text = "";
        switch (i) {
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
        viewHolder.tv.setText(text);

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (iClickImpl != null) {
                    iClickImpl.onClick(i);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return 7;
    }

    public void setSelectPosition(int selectPosition) {
        this.selectPosition = selectPosition;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.tv)
        TextView tv;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public interface IClick{
        void onClick(int position);
    }

    public void setiClickImpl(IClick iClickImpl) {
        this.iClickImpl = iClickImpl;
    }
}


