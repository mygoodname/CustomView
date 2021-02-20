package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.fragment.FlowLayoutFragment
import com.example.myapplication.fragment.PaintTestFragment
import com.example.myapplication.fragment.WriteViewFragment
import com.example.myapplication.fragment.viewpager.ViewPagerTestFragment
import com.example.myapplication.utils.ViewType

class CommonActivity : AppCompatActivity() {

    companion object {
        const val Type = "AppCompatActivity:type"
        fun startActivity(activity: AppCompatActivity, type: Int) {
            var intent = Intent(activity, CommonActivity::class.java)
            intent.putExtra("type", type)
            activity.startActivity(intent)
        }
    }
    var type=-1
    var viewContainer: FrameLayout? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_common)
        getType()
        initView()
    }

    fun getType(){
        type=intent.getIntExtra("type",0)
    }

    fun initView() {
        var transaction = supportFragmentManager.beginTransaction()
//        transaction.add(R.id.viewContainer, WriteViewFragment())
        when(type){
            ViewType.WriteView -> {
                transaction.add(R.id.viewContainer, WriteViewFragment())
            }
            ViewType.Paint -> {
                transaction.add(R.id.viewContainer, PaintTestFragment())
            }
            ViewType.FlowLayout -> {
                transaction.add(R.id.viewContainer, FlowLayoutFragment())
            }
            ViewType.ViewPager ->{
                transaction.add(R.id.viewContainer,ViewPagerTestFragment())
            }
            else ->{
                transaction.add(R.id.viewContainer, FlowLayoutFragment())
            }
        }
        Log.d("trackLife","CommonActivity --> transaction.commit()")
        transaction.commit()
    }
}