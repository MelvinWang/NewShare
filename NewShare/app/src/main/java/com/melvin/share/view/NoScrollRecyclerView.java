package com.melvin.share.view;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * 不能上下划的RecyclerView
 * 
 * @author Melvin
 * 
 */
public class NoScrollRecyclerView extends RecyclerView {

	public NoScrollRecyclerView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public NoScrollRecyclerView(Context context) {
		super(context);
	}

	// 表示事件是否拦截, 返回false表示不拦截, 可以让嵌套在内部的viewpager响应左右划的事件
	@Override
	public boolean onInterceptTouchEvent(MotionEvent arg0) {
		return false;
	}

	/**
	 * 重写onTouchEvent事件,什么都不用做
	 */
	@Override
	public boolean onTouchEvent(MotionEvent arg0) {
		return false;
	}
}
