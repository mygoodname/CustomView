package com.example.myapplication

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.coroutine.CoroutineTestActivity
import com.example.myapplication.touchevent.TouchEventDispatchTestActivity
import com.example.myapplication.utils.ViewType

class MainActivity : AppCompatActivity() {


    var overWriteView: TextView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        initView()
    }

    fun initView() {
        overWriteView = findViewById(R.id.overWriteView)
        var flowLayout:TextView=findViewById(R.id.flowLayout)
        var viewPager: TextView =findViewById(R.id.viewPager)
        var kotlinCoroutineTest:TextView=findViewById(R.id.kotlin_coroutine)
        var touchEventTest:TextView=findViewById(R.id.touchEventTest)

        overWriteView?.setOnClickListener {
            CommonActivity.startActivity(this, ViewType.WriteView)
        }
        flowLayout?.setOnClickListener{
            CommonActivity.startActivity(this, ViewType.FlowLayout)
        }
        viewPager?.setOnClickListener{
            CommonActivity.startActivity(this,ViewType.ViewPager)
        }
        kotlinCoroutineTest?.setOnClickListener{
            CoroutineTestActivity.startActivity(this)
        }
        touchEventTest?.setOnClickListener{
            TouchEventDispatchTestActivity.startActivity(this)
        }
    }

}