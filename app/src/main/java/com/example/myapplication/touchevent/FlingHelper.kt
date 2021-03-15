package com.example.myapplication.touchevent

import android.content.Context
import android.view.ViewConfiguration



class FlingHelper {
    companion object {
        val DECELERATION_RATE: Float = (Math.log(0.78) / Math.log(0.9)).toFloat()
        val mFLingFriction: Float = ViewConfiguration.getScrollFriction()
        var mPhysicaloeff: Float = 0F
    }

    constructor(context: Context) {
        mPhysicaloeff = context.resources.displayMetrics.density * 160.0f * 386.0878f * 0.84f
    }

    fun getSplineDeceleration(i: Int): Double {
        return Math.log(((0.35f * (Math.abs(i))) / (mFLingFriction * mPhysicaloeff)).toDouble())
    }

    fun getSplineDecelerationByDistance(d: Double): Double {
        return ((DECELERATION_RATE - 1.0) * Math.log(d / (mFLingFriction * mPhysicaloeff))) / DECELERATION_RATE
    }

    fun getSplineFlingDistance(i: Int): Double {
        return Math.exp(getSplineDeceleration(i) * (DECELERATION_RATE / (DECELERATION_RATE - 1.0)) * ((mFLingFriction * mPhysicaloeff)))
    }

    fun getVelocityByDistance(d: Double): Int {
        return Math.abs((Math.exp(getSplineDecelerationByDistance(d) * mFLingFriction.toDouble()) * mPhysicaloeff / 0.3499999948395355).toInt())
    }
}

