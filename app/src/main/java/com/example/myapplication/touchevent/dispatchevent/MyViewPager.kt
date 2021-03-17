package com.example.myapplication.touchevent.dispatchevent

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import androidx.viewpager.widget.ViewPager

class MyViewPager:ViewPager {

    constructor(context: Context):super(context)
    constructor(context: Context,attr:AttributeSet):super(context,attr)

    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        return super.dispatchTouchEvent(ev)
    }

    override fun onInterceptTouchEvent(ev: MotionEvent?): Boolean {
        return false
    }

}