package com.example.myapplication.touchevent.dispatchevent

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.widget.ListView

class MyListView :ListView{
    constructor(context:Context):super(context)
    constructor(context:Context,attr:AttributeSet):super(context,attr)
    constructor(context:Context,attr:AttributeSet,defStyleAttr:Int):super(context,attr,defStyleAttr)

    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        return false
    }

    override fun onInterceptTouchEvent(ev: MotionEvent?): Boolean {
        return super.onInterceptTouchEvent(ev)
    }

}