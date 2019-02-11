package com.yj.starshow.view.recyclerview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.yj.starshow.R;

/**
 * 添加分割线
 */
 public class RecyclerItemDecoration extends RecyclerView.ItemDecoration{

    private Paint mPaint ;
    /**
     * 所有初始化可以都放在构造方法中，来初始化一些基本参数
     */
    public RecyclerItemDecoration(Context context){
        mPaint = new Paint() ;
        mPaint.setAntiAlias(true);  //设置抗锯齿
        mPaint.setColor(context.getResources().getColor(R.color.color_transparent)); //设置画笔颜色
    }
    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        // 代表在每个item底部的位置留出10px 来绘制分割线 , 效果可以实现，问题是最后一个item还有分割线

        int position = parent.getChildAdapterPosition(view);

        // position : 每一个item的角标 从0 - 25
        // getChildCount() : 表示获取当前角标对应的item个数
        // 比如当前角标position=0 -> 则getChildCount() =1
        // 当前角标position=1 -> 则getChildCount() =2
        // 当前角标position=2 -> 则getChildCount() =3

        //由于getChildCount() 是不断变化的，所以不能保证是最后一条

//            Log.e("TAG" , "position -> " + position + ", " + "parent -> " + parent.getChildCount()) ;
//            if (position != parent.getChildCount() -1){
//                outRect.bottom = 10 ;
//            }


        // 保证第一条：可以保证给每个item的头部添加分割线，但是不给第一个添加就可以  , 这个方式是可以的
//            if (position != 0){
//                outRect.top = 10 ;
//            }


        // 留出头部位置 ，即就是第一个item上边的位置
        if (position != 0){
            outRect.top = 10 ;
        }
    }



    // 绘制分割线
    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        // 利用canvas想绘制什么就绘制什么
        // 在每一个item的头部来绘制
        int childCount = parent.getChildCount() ;


        // 指定绘制的区域
        Rect rect = new Rect() ;
        rect.left = parent.getPaddingLeft() ;
        rect.right = parent.getWidth() - parent.getPaddingRight() ;
        // 头部第一个不需要绘制分割线，所以直接从第二个开始
        for (int i = 1; i < childCount; i++) {
            // 分割线的底部就是 item的头部
            rect.bottom = parent.getChildAt(i).getTop() ;
            rect.top = rect.bottom - 10 ;
            c.drawRect(rect , mPaint);
        }
    }
}
