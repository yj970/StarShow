package com.yj.starshow.dialog;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;

import com.yj.starshow.R;

public class MaterialDialog {

public static class Builder {
    Context context;
    String message;
    int positiveBtnColor;
    int negativeBtnColor;
    IPositiveClickListener positiveClickListenerImpl;
    INegativeClickListener negativeClickListenerImpl;

    public Builder setMessage(String message) {
        this.message = message;
        return this;
    }

    public Builder setPositiveBtnColor(int positiveBtnColor) {
        this.positiveBtnColor = positiveBtnColor;
        return this;
    }

    public Builder setNegativeBtnColor(int negativeBtnColor) {
        this.negativeBtnColor = negativeBtnColor;
        return this;
    }

    public Builder setPositiveClickListener(IPositiveClickListener positiveClickListenerImpl) {
        this.positiveClickListenerImpl = positiveClickListenerImpl;
        return this;
    }

    public Builder setNegativeClickListener(INegativeClickListener negativeClickListenerImpl) {
        this.negativeClickListenerImpl = negativeClickListenerImpl;
        return this;
    }

    public Builder(Context context) {
        this.context = context;
        positiveBtnColor = context.getResources().getColor(R.color.color_yellow);
        negativeBtnColor = context.getResources().getColor(R.color.color_666);
    }

    public AlertDialog create() {
        final android.support.v7.app.AlertDialog d = new android.support.v7.app.AlertDialog.Builder(context)
                .setTitle("提示")
                .setMessage(message)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (positiveClickListenerImpl != null) {
                            positiveClickListenerImpl.onPositiveClickListener();
                        }
                        dialog.dismiss();
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (negativeClickListenerImpl != null) {
                            negativeClickListenerImpl.onNegativeClickListener();
                        }
                        dialog.dismiss();
                    }
                }).create();

        d.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {
                d.getButton(DialogInterface.BUTTON_NEGATIVE).setTextColor(negativeBtnColor);
                d.getButton(DialogInterface.BUTTON_POSITIVE).setTextColor(positiveBtnColor);
            }
        });
        return d;
    }
}

public interface IPositiveClickListener {
    void onPositiveClickListener();
}

public interface INegativeClickListener {
    void onNegativeClickListener();
}
}
