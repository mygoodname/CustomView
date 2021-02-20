package com.example.myapplication.fragment.viewpager

import android.os.CountDownTimer
import android.os.Handler
import android.os.Message
import android.view.View
import android.widget.TextView
import com.example.myapplication.R
import com.example.myapplication.fragment.lazy.FragmentDelegater
import com.example.myapplication.fragment.lazy.LazyFragment

class MyFragment5 :LazyFragment{

    lateinit var textView:TextView
    var handler:Handler=object :Handler(){
        override fun handleMessage(msg: Message) {
            textView.setText("5 加载完成")
            fragmentDelegater.trackLife("5 界面加载完成")
        }
    }

    var con:CountDownTimer?=null
    constructor(){
        fragmentDelegater= FragmentDelegater(this)

    }
    override fun initView(view: View) {
        textView=view.findViewById(R.id.five)
        fragmentDelegater.trackLife("initView")

    }

    override fun getResViewId(): Int {
        return R.layout.fragment_5_layout
    }

    override fun loadData(){
        setData()
    }

    override fun loadDataStop(){
        handler?.removeMessages(2)
        con?.cancel()
        fragmentDelegater.trackLife("5 停止一切加载")
        textView?.setText("5 stop")
    }

    fun setData(){
        con=object :CountDownTimer(1000L,100L){
            override fun onFinish() {
                handler.sendEmptyMessage(2)
            }

            override fun onTick(millisUntilFinished: Long) {

            }

        }
        (con as CountDownTimer).start()
        fragmentDelegater.trackLife("5 开始加载数据")

    }

    override fun onDestroy() {
        super.onDestroy()
        fragmentDelegater.trackLife("onDestroy")
    }

    override fun onStop() {
        super.onStop()
        fragmentDelegater.trackLife("onStop")
    }

    override fun onDetach() {
        super.onDetach()
        fragmentDelegater.trackLife("onDetach")
    }

}