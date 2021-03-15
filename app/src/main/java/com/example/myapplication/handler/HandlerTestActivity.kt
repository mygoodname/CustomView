package com.example.myapplication.handler

import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import java.lang.Thread.sleep

class HandlerTestActivity : AppCompatActivity() {
    var tag: String = "HandlerTestActivity"
    var mHandler: Handler? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initView()
        threadTest()
    }

    fun initView() {
        var msg = Message.obtain()
        mHandler = object : Handler() {
            override fun handleMessage(msg: Message) {
                super.handleMessage(msg)
                Log.e(tag, "msg.what:" + msg.what)
            }
        }
    }
    // handler  机制： handler 发送消息message 到主线程的looper的消息队列中，即messageQueue。looper 循环遍历messageQueue 处理 即message.target 调用dispatchMessage方法
    // 分发给handler  （message dispatchMessage 分发流程：1、msg.callback(runnable) 2、handler的callback 方法 3、handler 的handlerMessage）
    fun threadTest() {
        Thread {
            kotlin.run {
                for (index in 0 until 10) {
                    mHandler?.sendEmptyMessage(index)
                    sleep(2000)
                }
            }
        }.start()
    }
}