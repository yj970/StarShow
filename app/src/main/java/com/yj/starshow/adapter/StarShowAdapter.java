package com.yj.starshow.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yj.starshow.R;
import com.yj.starshow.bean.StarShow;
import com.yj.starshow.bean.StartShowList;

import java.text.SimpleDateFormat;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class StarShowAdapter extends RecyclerView.Adapter<StarShowAdapter.ViewHolder> {

    StartShowList list;
    ILongClick iLongClickImpl;
    public StarShowAdapter(StartShowList list) {
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_star_show, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int i) {
        StarShow starShow = list.getList().get(i);
        viewHolder.tvName.setText(starShow.getName());
        viewHolder.tvPlatform.setText(starShow.getPlatform());
        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm");
        viewHolder.tvTime.setText(formatter.format(starShow.getTime()));
        viewHolder.tvType.setText(starShow.getType());

        viewHolder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (iLongClickImpl != null) {
                    iLongClickImpl.onLongClick(i);
                }
                return false;
            }
        });
    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.getList().size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_time)
        TextView tvTime;
        @BindView(R.id.tv_name)
        TextView tvName;
        @BindView(R.id.tv_platform)
        TextView tvPlatform;
        @BindView(R.id.tv_type)
        TextView tvType;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public interface ILongClick{
        void onLongClick(int position);
    }

    public void setiLongClickImpl(ILongClick iLongClickImpl) {
        this.iLongClickImpl = iLongClickImpl;
    }
}



