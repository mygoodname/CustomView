package com.example.myapplication.touchevent

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.LinearLayout
import androidx.core.widget.NestedScrollView
import com.example.myapplication.utils.ScreenUtil

class MyNestedScrollView :NestedScrollView {
    var ctext:Context?=null
    constructor(context:Context) :super(context){
        ctext=context
    }
    constructor(context:Context,attrs:AttributeSet) :super(context,attrs){
        ctext=context
    }
    constructor(context:Context, attrs:AttributeSet, deftly:Int) :super(context,attrs,deftly){
        ctext=context
    }
    var contentView: View?=null

    override fun onFinishInflate() {
        super.onFinishInflate()
        var linearLayout:LinearLayout= getChildAt(0) as LinearLayout
        contentView=linearLayout.getChildAt(1)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        contentView?.layoutParams?.height=ScreenUtil.getScreenShowHeight(ctext)
    }

}