package com.example.myapplication.touchevent

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import androidx.recyclerview.widget.RecyclerView

class MyNestRecycleView :RecyclerView{
    var tag:String="MyNestRecycleView : "
    constructor(context:Context) :super(context)
    constructor(context:Context,attr:AttributeSet) :super(context,attr)
    constructor(context:Context,attr: AttributeSet,defAttributes: Int) :super(context,attr,defAttributes)

    override fun setNestedScrollingEnabled(enabled: Boolean) {
        Log.e("LifeCycle",tag+"setNestedScrollingEnabled")
        super.setNestedScrollingEnabled(enabled)
    }

    override fun isNestedScrollingEnabled(): Boolean {
        Log.e("LifeCycle",tag+"isNestedScrollingEnabled")
        return super.isNestedScrollingEnabled()
    }

    override fun startNestedScroll(axes: Int): Boolean {
        Log.e("LifeCycle",tag+"startNestedScroll axes")
        return super.startNestedScroll(axes)
    }

    override fun startNestedScroll(axes: Int, type: Int): Boolean {
        Log.e("LifeCycle",tag+"startNestedScroll axes and type")
        return super.startNestedScroll(axes, type)
    }

    override fun stopNestedScroll() {
        Log.e("LifeCycle",tag+"stopNestedScroll")
        super.stopNestedScroll()
    }

    override fun stopNestedScroll(type: Int) {
        Log.e("LifeCycle",tag+"stopNestedScroll  type")
        super.stopNestedScroll(type)
    }

    override fun hasNestedScrollingParent(): Boolean {
        Log.e("LifeCycle",tag+"hasNestedScrollingParent")
        return super.hasNestedScrollingParent()
    }

    override fun hasNestedScrollingParent(type: Int): Boolean {
        Log.e("LifeCycle",tag+"hasNestedScrollingParent  type")
        return super.hasNestedScrollingParent(type)
    }

    override fun dispatchNestedScroll(
        dxConsumed: Int,
        dyConsumed: Int,
        dxUnconsumed: Int,
        dyUnconsumed: Int,
        offsetInWindow: IntArray?
    ): Boolean {
        Log.e("LifeCycle",tag+"dispatchNestedScroll")
        return super.dispatchNestedScroll(
            dxConsumed,
            dyConsumed,
            dxUnconsumed,
            dyUnconsumed,
            offsetInWindow
        )
    }

    override fun dispatchNestedScroll(
        dxConsumed: Int,
        dyConsumed: Int,
        dxUnconsumed: Int,
        dyUnconsumed: Int,
        offsetInWindow: IntArray?,
        type: Int
    ): Boolean {
        Log.e("LifeCycle",tag+"dispatchNestedScroll  type")
        return super.dispatchNestedScroll(
            dxConsumed,
            dyConsumed,
            dxUnconsumed,
            dyUnconsumed,
            offsetInWindow,
            type
        )
    }

    override fun dispatchNestedPreScroll(
        dx: Int,
        dy: Int,
        consumed: IntArray?,
        offsetInWindow: IntArray?
    ): Boolean {
        Log.e("LifeCycle",tag+"dispatchNestedPreScroll")
        return super.dispatchNestedPreScroll(dx, dy, consumed, offsetInWindow)
    }

    override fun dispatchNestedPreScroll(
        dx: Int,
        dy: Int,
        consumed: IntArray?,
        offsetInWindow: IntArray?,
        type: Int
    ): Boolean {
        Log.e("LifeCycle",tag+"dispatchNestedPreScroll  type")
        return super.dispatchNestedPreScroll(dx, dy, consumed, offsetInWindow, type)
    }

    override fun dispatchNestedFling(
        velocityX: Float,
        velocityY: Float,
        consumed: Boolean
    ): Boolean {
        Log.e("LifeCycle",tag+"dispatchNestedFling")
        return super.dispatchNestedFling(velocityX, velocityY, consumed)
    }

    override fun dispatchNestedPreFling(velocityX: Float, velocityY: Float): Boolean {
        Log.e("LifeCycle",tag+"dispatchNestedPreFling")
        return super.dispatchNestedPreFling(velocityX, velocityY)
    }
}