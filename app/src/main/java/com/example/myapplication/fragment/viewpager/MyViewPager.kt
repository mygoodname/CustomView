package com.example.myapplication.fragment.viewpager

import android.content.Context
import android.util.AttributeSet
import androidx.core.view.children
import androidx.viewpager.widget.ViewPager
import kotlin.math.max

class MyViewPager : ViewPager {

    constructor(context:Context, attrs: AttributeSet) : super(context,attrs)
    constructor(context:Context) : super(context)

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        var customHeight=0
        for (item in children){
            var childHeightSpec=getChildMeasureSpec(heightMeasureSpec,0,item.layoutParams.height)
            var childWidthSpec= getChildMeasureSpec(widthMeasureSpec,0,item.layoutParams.width)
            item.measure(childWidthSpec,childHeightSpec)
            var childHeight=item.measuredHeight
            customHeight= max(childHeight,customHeight)
        }
        var myHeightMeasureSpec=MeasureSpec.makeMeasureSpec(customHeight,MeasureSpec.EXACTLY)
        super.onMeasure(widthMeasureSpec, myHeightMeasureSpec)
    }

}