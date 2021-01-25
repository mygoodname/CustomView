package com.example.myapplication.customview

import android.content.Context
import android.content.res.Resources
import android.util.AttributeSet
import android.util.TypedValue
import android.view.View
import android.view.ViewGroup

class FlowLayoutTwo : ViewGroup {

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    private var horizontalSpace = dp2px(20)//间距
    private var verticalSpace = dp2px(20)//行距

    //记录所有的行,用于onLayout方法
    private var allLines: MutableList<MutableList<View>> = ArrayList()

    //记录每行包含的view,用于onLayout方法
    private var lineViews: MutableList<View> = ArrayList()

    //记录每行的高度，用于onLayout方法
    private var lineHeights: MutableList<Int> = ArrayList()
    private var lineUsedWidth = 0//每行使用的宽度
    private var lineUsedHeight = 0//每行使用的宽度
    private var parentWith = 0 //子view所需的总宽度
    private var parentHeight = 0 //子view所需总的高度

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        clearMeasureParams()
        //获取父布局给的宽度size
        var selfWith = MeasureSpec.getSize(widthMeasureSpec)
        for (i in 0 until childCount) {
            var childView=getChildAt(i)
            //获取子view 的layoutParams
            var childViewLayoutParams = childView.layoutParams

            //获取子view 的measureSpec
            var childViewWidthMeasureSpec = getChildMeasureSpec(
                widthMeasureSpec,
                paddingLeft + paddingRight,
                childViewLayoutParams.width
            )
            var childViewHeightMeasureSpec = getChildMeasureSpec(
                widthMeasureSpec,
                paddingTop + paddingBottom,
                childViewLayoutParams.height
            )

            //测量子view尺寸 目的计算行数、行宽、行高
            childView.measure(childViewWidthMeasureSpec, childViewHeightMeasureSpec)
            //判断是否换行
            if (lineUsedWidth + childView.measuredWidth + horizontalSpace > selfWith) {

                parentWith = Math.max(parentWith, lineUsedWidth)//计算子view 总宽
                parentHeight += lineUsedHeight + verticalSpace//计算子view 总高

                allLines.add(lineViews) // 记录该行view
                lineHeights.add(lineUsedHeight)// 记录该行高

                lineViews=ArrayList()//开始新的一行
                lineUsedHeight = 0//高置零
                lineUsedWidth = 0//宽置零
            }
            //// 不换行
            lineViews.add(childView)//记录子view
            lineUsedWidth += childView.measuredWidth + horizontalSpace //计算该行所用的宽
            lineUsedHeight = Math.max(lineUsedHeight, childView.measuredHeight) //计算该行所用的高
            /// 最后一个view 时 不会走allLines.add(lineViews)  所以单独加上
            if(i==childCount-1){
                parentWith = Math.max(parentWith, lineUsedWidth)//计算子view 总宽
                parentHeight += lineUsedHeight + verticalSpace//计算子view 总高
                allLines.add(lineViews) // 记录该行view
                lineHeights.add(lineUsedHeight)// 记录该行高
            }
        }
        ///判断宽度 子view的宽度是否比自身分得的大  如果大就用自己分得的宽度
        if(MeasureSpec.getMode(widthMeasureSpec)==MeasureSpec.AT_MOST||MeasureSpec.getMode(widthMeasureSpec)==MeasureSpec.EXACTLY){
            if(parentWith>selfWith)
            parentWith=selfWith
        }
        //测量自己
        setMeasuredDimension(parentWith, parentHeight)
    }

    override fun onLayout(p0: Boolean, p1: Int, p2: Int, p3: Int, p4: Int) {
        var curL = paddingLeft // 左边距离（相对父布局）
        var curT = paddingTop  //顶边距离
        for (index in allLines.indices) {
            var line = allLines[index] //获取某行
            for (childView in line) {
                var left = curL
                var top = curT
                var right = childView.measuredWidth + left
                var bottom = childView.measuredHeight + top
                childView.layout(left, top, right, bottom)//布局
                curL = right + horizontalSpace//计算下一个view 的左边距
            }
            curT += lineHeights[index] + verticalSpace  // 新的一行的顶边距离
            curL = paddingLeft //新的一行的左边距离
        }

        /*  for(item in line.indices){
                var view=line[item]
                view.layout(horizontalSpace,usedHieght,view.measuredWidth,view.measuredHeight)
                usedHieght= Math.max(usedHieght,view.measuredHeight+verticalSpace)
            }

            for((index,value) in line.withIndex()){
                value.layout(horizontalSpace,usedHieght,value.measuredWidth,value.measuredHeight)
                usedHieght= Math.max(usedHieght,value.measuredHeight+verticalSpace)
            }

            for(i in 0 until line.size){
                var view=line[i]
                view.layout(horizontalSpace,usedHieght,view.measuredWidth,view.measuredHeight)
                usedHieght= Math.max(usedHieght,view.measuredHeight+verticalSpace)
            }*/
    }

    fun clearMeasureParams() {
        allLines.clear()
        lineViews.clear()
        lineHeights.clear()
        lineUsedWidth = 0//每行使用的宽度
        lineUsedHeight = 0//每行使用的宽度
        parentWith = 0 //子view所需的总宽度
        parentHeight = 0 //子view所需总的高度
    }

    private fun dp2px(dp: Int): Int {
        return TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            dp.toFloat(),
            Resources.getSystem().displayMetrics
        ).toInt()
    }
}