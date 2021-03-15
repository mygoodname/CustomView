package com.example.myapplication.touchevent

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.core.view.children
import androidx.core.widget.NestedScrollView
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.utils.ScreenUtil

class MyNestedScrollView :NestedScrollView {
    var ctext:Context?=null
    var isStartFling:Boolean=false  //是否开始fling
    var velocityY:Int?=null  //当前滑动的速度
    var totalDy:Int=0// 总共滑动的距离
    var flingHelper:FlingHelper?=null
    constructor(context:Context) :super(context){
        ctext=context
        init()
    }
    constructor(context:Context,attrs:AttributeSet) :super(context,attrs){
        ctext=context
        init()
    }
    constructor(context:Context, attrs:AttributeSet, deftly:Int) :super(context,attrs,deftly){
        ctext=context
        init()
    }
    var contentView: ViewGroup?=null
    var topView:View?=null

    fun init(){
        flingHelper= ctext?.let { FlingHelper(it) }
        setOnScrollChangeListener(OnScrollChangeListener{ v: NestedScrollView, scrollX: Int, scrollY: Int, oldScrollX: Int, oldScrollY: Int ->
            if(isStartFling){
                totalDy=0     //开始滑动将totalDy 置零
                isStartFling=false
            }
            if(scrollY==0){}
            if(scrollY==(getChildAt(0).measuredHeight-v.measuredHeight)){//滑动到屏幕底部 就是指linearlayout 的最底部显示出来了
                dispatchChildFling()
            }
            totalDy+=scrollY-oldScrollY
        })

    }
    //分发惯性滑动给子view
    fun dispatchChildFling(){
        if(totalDy!=0){
            //获取当前速度下能惯性滚动的距离
            var splineFlingDistance= velocityY?.let { flingHelper?.getSplineFlingDistance(it) }
            splineFlingDistance?.let {
                if(it >totalDy){//判段即将滚动的距离是否大于已经滑动的距离  如果不大于则不分发给子view
                    // splineFlingDistance-totalDy  表示是子view将要惯性滚动的距离
                    flingHelper?.getVelocityByDistance(splineFlingDistance-totalDy)?.let { flingChild(it) }
                }
            }
        }
        totalDy=0
        velocityY=0
    }
    //子view执行fling事件
    fun flingChild(velDy:Int){
        var recyclerView= contentView?.let { getChildRecycleView(it) }
        recyclerView?.fling(0,velDy)
    }

    fun getChildRecycleView(viewGroup: ViewGroup): RecyclerView? {
        for (item in viewGroup.children){
            if(item is RecyclerView && item.javaClass==this.javaClass){
                return item
            }
            else if (item is ViewGroup){
                var child=getChildRecycleView(item)
                if (child is RecyclerView)
                    return child
            }
        }
        return null
    }

    override fun onFinishInflate() {
        super.onFinishInflate()
        var linearLayout:LinearLayout= getChildAt(0) as LinearLayout
        contentView= linearLayout.getChildAt(1) as ViewGroup
        topView =linearLayout.getChildAt(0)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        contentView?.layoutParams?.height=ScreenUtil.getScreenShowHeight(ctext)
    }

    override fun fling(velocityY: Int) {
        super.fling(velocityY)
        if(velocityY<0){
            this.velocityY=0
        }else{
            isStartFling=true
            this.velocityY=velocityY
        }
    }

    override fun onNestedPreScroll(target: View, dx: Int, dy: Int, consumed: IntArray, type: Int) {
//        var hide=dy>0&&scrollY <= topView?.measuredHeight!!//  不能用等于号 ，用了等于号它会一直分发
//        nestedScroll事件导致嵌套的recycleView 接受不到nestedScroll事件
        var hide=dy>0&&scrollY < topView?.measuredHeight!!
        if(hide){
            scrollBy(0,dy)
            consumed[1]=dy
        }
    }
}