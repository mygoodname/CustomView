package com.example.myapplication.customview

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import com.example.myapplication.R

class PintTest :View{

    var mPaint: Paint?=null
    var referencePaint:Paint?=null

    constructor(context: Context):super(context){
        initPaint()
    }
    constructor(context: Context,attributeSet: AttributeSet):super(context,attributeSet){
        initPaint()
    }
    constructor(context: Context,attributeSet: AttributeSet,defAtt: Int):super(context,attributeSet,defAtt){
        initPaint()
    }

    fun initPaint(){
        mPaint= Paint()
        mPaint?.setColor(resources.getColor(R.color.redColor))
//        mPaint?.strokeWidth=50f

        //设置style
        //Paint.Style.FILL：填充内部
        //Paint.Style.FILL_AND_STROKE  ：填充内部和描边
        //Paint.Style.STROKE  ：描边
//        mPaint?.style=Paint.Style.FILL
//        mPaint?.style=Paint.Style.FILL_AND_STROKE
        mPaint?.style=Paint.Style.STROKE

        // 当画笔样式为STROKE或FILL_OR_STROKE时，设置笔刷的图形样式，如圆形样式 Cap.ROUND,或方形样式Cap.SQUARE
//        mPaint?.strokeCap=Paint.Cap.BUTT    //有多长就是多长
//        mPaint?.strokeCap=Paint.Cap.ROUND //在原来的基础上两端多出半圆长度
//        mPaint?.strokeCap=Paint.Cap.SQUARE //在原来的基础上两端多一个长方形长度

        //设置绘制时各图形的结合方式，如平滑效果等
/*        mPaint?.strokeJoin=Paint.Join.BEVEL //斜角
        mPaint?.strokeJoin=Paint.Join.MITER //斜接,成直角
        mPaint?.strokeJoin=Paint.Join.ROUND //圆角*/

        //设置绘制文字的对齐方向
        mPaint?.textAlign=Paint.Align.CENTER //文字的中心位置在绘制点
        mPaint?.textAlign=Paint.Align.LEFT //文字的左边位置在绘制点
        mPaint?.textAlign=Paint.Align.RIGHT //文字的右边位置在绘制点

        referencePaint= Paint()
        referencePaint?.setColor(resources.getColor(R.color.redColor))
        referencePaint?.style=Paint.Style.STROKE
        referencePaint?.textAlign=Paint.Align.LEFT //文字的左边位置在绘制点
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        /*mPaint?.let {
            canvas?.drawCircle(300f,300f,200f,it)
        }*/
        /*mPaint?.let {
            canvas?.drawLine(300f,300f,800f,300f,it)
        }*/
        /*mPaint?.let {
            canvas?.drawRect(300f,300f,800f,800f,it)
        }*/
        mPaint?.let {
            canvas?.drawText("测试类横不错的哟！嗯嗯嗯嗯嗯呃",300f,800f,it)
        }
        referencePaint?.let {
            canvas?.drawText("测试类横不错的哟！嗯嗯嗯嗯嗯呃",300f,850f,it)
        }

    }

}