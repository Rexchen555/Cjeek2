package com.example.administrator.cjeek;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;


/**
 * Created by Administrator on 2017/3/15.
 */

public class TopNewsViewPager extends ViewPager {
    private int startX;
    private int startY;

    public TopNewsViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public TopNewsViewPager(Context context) {
        super(context);
    }
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        getParent().requestDisallowInterceptTouchEvent(true);
        return super.dispatchTouchEvent(ev);
    }
}
