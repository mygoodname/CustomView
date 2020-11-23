package com.example.myapplication.customview

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import com.example.myapplication.R

class WriteView : View {

    var defColor = R.color.colorPrimary

    var defHeight = 0

    var defWith = 0

    var mPaint: Paint? = null

    constructor(context: Context) : super(context) {
        initAttr(context, null, 0)
    }

    constructor(context: Context, attr: AttributeSet) : super(context, attr) {
        initAttr(context, attr, 0)
    }

    constructor(context: Context, attr: AttributeSet, defStyleAttr: Int) : super(
        context,
        attr,
        defStyleAttr
    ) {
        initAttr(context, attr, defStyleAttr)
    }

    fun initAttr(context: Context, attr: AttributeSet?, defStyleAttr: Int) {
        if (attr != null) {
            var a = context.obtainStyledAttributes(attr, R.styleable.MyCircleColor)
            defColor = a.getColor(
                R.styleable.MyCircleColor_circleColor,
                resources.getColor(R.color.colorAccent)
            )
            defHeight = a.getDimensionPixelSize(R.styleable.MyCircleColor_circleHeight, 200)
            defWith = a.getDimensionPixelSize(R.styleable.MyCircleColor_circleWith, 200)
            a.recycle()
        }
        initView()
    }

    fun initView() {
        mPaint = Paint(Paint.ANTI_ALIAS_FLAG)
        mPaint?.setColor(defColor)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val widthSpecMode = MeasureSpec.getMode(widthMeasureSpec)
        val widthSpecSize = MeasureSpec.getSize(widthMeasureSpec)
        val heightSpecMode = MeasureSpec.getMode(heightMeasureSpec)
        val heightSpecSize = MeasureSpec.getSize(heightMeasureSpec)
        if (widthSpecMode == MeasureSpec.AT_MOST && heightSpecMode == MeasureSpec.AT_MOST) {
            setMeasuredDimension(defWith, defHeight)
        } else if (widthSpecMode == MeasureSpec.AT_MOST) {
            setMeasuredDimension(defWith, heightSpecSize)
        } else if (heightSpecMode == MeasureSpec.AT_MOST) {
            setMeasuredDimension(widthSpecSize, defHeight)
        }
    }

    override fun draw(canvas: Canvas?) {
        super.draw(canvas)
        var circleWith=width-paddingRight-paddingLeft
        var circleHeight=height-paddingBottom-paddingTop
        var radius=Math.min(circleWith,circleHeight)/2
        mPaint?.let {
            canvas?.drawCircle((paddingLeft+circleWith/2).toFloat(),
                (paddingTop+circleHeight/2).toFloat(), radius.toFloat(), it)
        }
    }

}