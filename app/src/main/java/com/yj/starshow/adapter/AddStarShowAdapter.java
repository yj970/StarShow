package com.yj.starshow.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.SpannedString;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.style.AbsoluteSizeSpan;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.yj.starshow.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AddStarShowAdapter extends RecyclerView.Adapter<AddStarShowAdapter.ViewHolder> {
    List<String> list;
    Context context;
    String etText;

    int selectPosition;

    public AddStarShowAdapter(List<String> list, Context context) {
        this.list = list;
        this.context = context;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_add_star_show, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, final int i) {
        String str = list.get(i);
        if (!TextUtils.isEmpty(str)) {
            viewHolder.et.setVisibility(View.GONE);
            viewHolder.tv.setVisibility(View.VISIBLE);
        } else {
            viewHolder.tv.setVisibility(View.GONE);
            viewHolder.et.setVisibility(View.VISIBLE);
            if (!TextUtils.isEmpty(etText)) {
                viewHolder.et.setText(etText);
            }
        }

        //  设置edittext中hint的字体大小
        // 新建一个可以添加文本的对象
        SpannableString ss = new SpannableString("其他");
        // 设置文本字体大小
        AbsoluteSizeSpan ass = new AbsoluteSizeSpan(12, true);
        // 将字体大小附加到文本的属性
        ss.setSpan(ass, 0, ss.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        // 设置hint属性
        viewHolder.et.setHint(new SpannedString(ss));//转码

        viewHolder.tv.setText(str);

        viewHolder.et.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(final View v, boolean hasFocus) {
                if (hasFocus) {
                    if (selectPosition != i) {
                        selectPosition = i;
                        notifyDataSetChanged();
                    }
                }
            }
        });

        if (selectPosition == i) {
            viewHolder.et.requestFocus();
            // 弹出软键盘
            viewHolder.et.postDelayed(new Runnable() {
                @Override
                public void run() {
                    InputMethodManager inputMethodManager = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
                    inputMethodManager.showSoftInput(viewHolder.et, 0);
                }
            }, 200);

        }


        viewHolder.et.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                etText = s.toString();
            }
        });

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectPosition = i;
                notifyDataSetChanged();
                // 隐藏软键盘
                InputMethodManager inputMethodManager = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
                inputMethodManager.hideSoftInputFromWindow(viewHolder.et.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            }
        });


        if (i == selectPosition) {
            viewHolder.itemView.setBackground(context.getResources().getDrawable(R.drawable.bg_add_star_show));
        } else {
            viewHolder.itemView.setBackground(context.getResources().getDrawable(R.color.color_while));
        }
    }

    public String getSelectText() {
        String str = list.get(selectPosition);
        if (!TextUtils.isEmpty(str)) {
            return str;
        } else {
            return etText;
        }
    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv)
        TextView tv;
        @BindView(R.id.et)
        EditText et;
        @BindView(R.id.fl)
        FrameLayout fl;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}




