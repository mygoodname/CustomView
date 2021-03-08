package com.example.myapplication.customview

import android.content.Context
import android.content.res.Resources
import android.util.AttributeSet
import android.util.Log
import android.util.TypedValue
import android.view.View
import android.view.ViewGroup
import androidx.core.view.children
import androidx.core.view.doOnLayout
import kotlin.math.max

class FlowLayout @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : ViewGroup(context, attrs, defStyleAttr) {

    // 记录所有的行，一行一行的存储，用于layout
    private var allLines: MutableList<MutableList<View>> = ArrayList()
    // 记录每一行的行高，用于layout
    private var linesHeights: MutableList<Int> = ArrayList()
//    var allLines=ArrayList<ArrayList<View>>()
    var horizontalSpace=dp2px(10)
    var verticalSpace=dp2px(10)
//    var linesHeights=ArrayList<Int>()
    //测量子view 宽高、行数、行高
    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        Log.e("FlowLayout","onMeasure")
        clearLines()//防止内存颤抖
        var lineWidth=0
        var lineHeight=0
        var selfWidth=MeasureSpec.getSize(widthMeasureSpec)
        var totalWith=0
        var totalHeight=0
        var lineViews: MutableList<View> = ArrayList()


        for (childView in children){
//        for (i in 0 until childCount ){
//            var childView:View=getChildAt(i) //获取子View
            //计算子viewMeasureSpec
            var childWidthMeasureSpec=getChildMeasureSpec(widthMeasureSpec,paddingLeft+paddingRight,childView.layoutParams.width)
            var childHeightMeasureSpec=getChildMeasureSpec(widthMeasureSpec,paddingTop+paddingBottom,childView.layoutParams.height)
            // 测量子view
            childView.measure(childWidthMeasureSpec,childHeightMeasureSpec)
            //获取子view的measure 宽高
            var currentChildWidth=childView.measuredWidth
            var currentChildHeight=childView.measuredHeight
            //换行 (计算此行所需的宽高信息)
            if(lineWidth+currentChildWidth+horizontalSpace>selfWidth){

//                totalWith=Math.max(lineWidth,lineWidth+horizontalSpace)
                //获取最大的行的宽度
                totalWith=Math.max(totalWith,lineWidth)
                //计算所需的高度
                totalHeight +=lineHeight+verticalSpace
                //保存此行的高度
                linesHeights.add(lineHeight)
                //保存此行
                allLines.add(lineViews)
                lineWidth=0
                lineHeight=0
                lineViews.clear()
            }
            //不换行
            lineViews.add(childView)//保存此行所排列的view
            //行宽
            lineWidth += currentChildWidth+horizontalSpace
            //行高
            lineHeight = Math.max(currentChildHeight,lineHeight)
        }
        //度量自己
        setMeasuredDimension(totalWith,totalHeight)
    }
    //根据onmearsure 方法测量出的行数、行高进行子view 的 layout方法
    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        Log.e("FlowLayout","onLayout")
        var curL=paddingLeft  //左边距离中间值
        var curT=paddingTop   //上边距离中间值
        for((index,line) in allLines.withIndex()){
            for (view:View in line){
                var left=curL
                var top=curT
                var right=left+view.measuredWidth
                var bottom=curT+view.measuredHeight
                view.layout(left,top,right,bottom)
                curL=right+horizontalSpace
            }
            curT=linesHeights[index]+verticalSpace
            curL=paddingLeft
        }
    }
    fun clearLines(){
        linesHeights.clear()
        allLines.clear()
    }
    private fun dp2px(dp: Int): Int {
        return TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            dp.toFloat(),
            Resources.getSystem().displayMetrics
        ).toInt()
    }
}