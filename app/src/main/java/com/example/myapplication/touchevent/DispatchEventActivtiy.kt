package com.example.myapplication.touchevent

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.R

class DispatchEventActivity : AppCompatActivity() {
    val tag:String="DispatchEventActivity"
    companion object {
        fun startActivity(activity: Context) {
            var intent = Intent(activity, DispatchEventActivity::class.java)
            activity.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_diapatch_event)
        initView()
    }

    fun initView(){
        var button:Button=findViewById(R.id.button)
        button.setOnClickListener({v ->  Log.e(tag,"click")})
        findViewById<View>(R.id.button).setOnTouchListener { v, event ->
            Log.e(tag,"ontouch")
            true }
    }
}