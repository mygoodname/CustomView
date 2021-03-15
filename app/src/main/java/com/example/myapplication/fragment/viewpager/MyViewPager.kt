package com.example.myapplication.fragment.viewpager

import android.content.Context
import android.util.AttributeSet
import android.view.ViewGroup
import androidx.core.view.children
import androidx.viewpager.widget.ViewPager
import java.util.jar.Attributes
import kotlin.math.max

//重写onMeasure 方法解决viewPager的 height 设置为wrap_content时无效的bug
class MyViewPager :ViewPager{
    constructor(context:Context):super (context)
    constructor(context: Context,attr:AttributeSet):super (context,attr)

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        var MaxHeight=0
        for (childView in children){
            var childLP=childView.layoutParams
            childView.measure(widthMeasureSpec, getChildMeasureSpec(heightMeasureSpec,0,childLP.height))
            var childMeasureHeight=childView.measuredHeight
            MaxHeight= max(childMeasureHeight,MaxHeight)
        }
        var myHeightMeasureSpec=MeasureSpec.makeMeasureSpec(MaxHeight,MeasureSpec.EXACTLY)
        super.onMeasure(widthMeasureSpec, myHeightMeasureSpec)
    }
}