package com.example.myapplication.coroutine

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.R
import kotlinx.coroutines.*

class CoroutineTestActivity : AppCompatActivity() ,CoroutineScope by MainScope(){
    val TAG:String="CoroutineTestActivity"
    companion object{
        fun startActivity(activity: Activity){
            var intent= Intent(activity,CoroutineTestActivity::class.java)
            activity.startActivity(intent)
        }
    }


    var textView:TextView?=null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_coroutine_test)
        initView()
        test()
        doOtherThing()
    }

    fun initView(){
        textView=findViewById(R.id.text)
    }

    fun test(){
        GlobalScope.launch(Dispatchers.IO) {
            var resulet=fetchData()
            withContext(Dispatchers.Main){
                textView?.text =resulet
                Log.e(TAG,"test   ${textView?.text}")
            }
        }
    }

    suspend fun fetchData():String{
        delay(5000)
        return "content"
    }

    fun doOtherThing(){
        Log.e(TAG,"doOtherThing")
    }

    override fun onDestroy() {
        super.onDestroy()
        cancel() //关闭整个协程
    }
}