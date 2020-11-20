package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import com.example.myapplication.utils.ViewType
import java.util.*

class MainActivity : AppCompatActivity() {


    var overWriteView:TextView?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initView()
    }
    fun initView(){
        overWriteView=findViewById(R.id.overWriteView)
        overWriteView?.setOnClickListener { CommonActivity.startActivity(this, ViewType.writeView) }
    }
}