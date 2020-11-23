package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.fragment.PaintTestFragment
import com.example.myapplication.fragment.WriteViewFragment

class CommonActivity : AppCompatActivity() {

    companion object {
        const val Type = "AppCompatActivity:type"
        fun startActivity(activity: AppCompatActivity, type: Int) {
            var intent = Intent(activity, CommonActivity::class.java)
            intent.putExtra("type", type)
            activity.startActivity(intent)
        }
    }

    var viewContainer: FrameLayout? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_common)
        initView()
    }


    fun initView() {
        var transaction = supportFragmentManager.beginTransaction()
//        transaction.add(R.id.viewContainer, WriteViewFragment())
        transaction.add(R.id.viewContainer, PaintTestFragment())
        transaction.commit()
    }

}