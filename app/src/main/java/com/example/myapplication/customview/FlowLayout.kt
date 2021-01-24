package com.example.myapplication.customview

import android.content.Context
import android.util.AttributeSet
import android.view.ViewGroup
import androidx.core.view.children

class FlowLayout @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : ViewGroup(context, attrs, defStyleAttr) {

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {

        var lineWidth=0
        var lineHeight=0
        var horizontalSpace=10
        var verticalSpace=10
        var selfWidth=MeasureSpec.getSize(widthMeasureSpec)

        var totalWith=0
        var totalHeight=0

        for (item in children){
            var childWidthMeasureSpec=getChildMeasureSpec(widthMeasureSpec,paddingLeft+paddingRight,item.layoutParams.width)
            var childHeightMeasureSpec=getChildMeasureSpec(widthMeasureSpec,paddingTop+paddingBottom,item.layoutParams.height)

            item.measure(childWidthMeasureSpec,childHeightMeasureSpec)

            var childWidth=item.measuredWidth
            var childHeight=item.measuredHeight

            if(lineWidth+childWidth+horizontalSpace>selfWidth){
                //换行
                totalWith=Math.max(lineWidth,lineWidth+horizontalSpace)
                totalHeight=lineHeight+verticalSpace

                lineWidth=0
                lineHeight=0
            }

            lineWidth = childWidth+horizontalSpace
            lineHeight = childHeight+verticalSpace
        }

        //度量自己
        setMeasuredDimension(totalWith,totalHeight)
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)


    }
}