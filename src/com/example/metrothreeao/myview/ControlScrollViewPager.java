package com.example.metrothreeao.myview;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

public class ControlScrollViewPager extends ViewPager {
	private boolean isCanScroll = true;

	public ControlScrollViewPager(Context context) {
		super(context);
	}

	public ControlScrollViewPager(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public void setCanScroll(boolean isCanScroll) {
		this.isCanScroll = isCanScroll;
	}

	@Override
	public boolean onInterceptTouchEvent(MotionEvent arg0) {

		// �����ش����¼������λ�õ�ʱ�򣬷���true��
		// ˵����onTouch�����ڴ˿ؼ�������ִ�д˿ؼ���onTouchEvent
		if (!isCanScroll)
			return false;
		return super.onInterceptTouchEvent(arg0);
	}

	@Override
	public boolean onTouchEvent(MotionEvent arg0) {
		if (!isCanScroll)
			return true;
		return super.onTouchEvent(arg0);
	}
}
