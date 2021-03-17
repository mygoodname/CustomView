package com.example.myapplication.touchevent

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ListView
import android.widget.SimpleAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.PagerAdapter
import com.example.myapplication.R
import com.example.myapplication.touchevent.dispatchevent.MyListView
import com.example.myapplication.touchevent.dispatchevent.MyViewPager

class DispatchEventActivity : AppCompatActivity() {
    val tag:String="DispatchEventActivity"
    var viewPager:MyViewPager?=null
    var lists=ArrayList<ListView>()
    var listdata =ArrayList<HashMap<String, String>>()
    companion object {
        fun startActivity(activity: Context) {
            var intent = Intent(activity, DispatchEventActivity::class.java)
            activity.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_diapatch_event)
        initListData()
        initView()
    }

    fun initView(){
        var button:Button=findViewById(R.id.button)
        button.setOnClickListener({ v -> Log.e(tag, "click") })
        findViewById<View>(R.id.button).setOnTouchListener { v, event ->
            Log.e(tag, "ontouch")
            true }

        while (lists.size<4){
            var listView=MyListView(this)
            var listAdapter=SimpleAdapter(
                this, listdata, R.layout.item_recycleview_layout,
                arrayOf("content"),
                intArrayOf(R.id.textView)
            )
            listView.adapter=listAdapter
            lists.add(listView)
        }
        viewPager=findViewById(R.id.viewPager)
        viewPager?.adapter=MyViewPagerAdapter()
    }

    fun initListData() {
        var i=0
        while (i<10){
            var map=HashMap<String, String>()
            map.put("content", i.toString())
            listdata.add(map)
            i++
        }
    }

    inner class MyViewPagerAdapter: PagerAdapter() {

        override fun getCount(): Int {
            return lists.size
        }

        override fun isViewFromObject(view: View, `object`: Any): Boolean {
           return view==`object`
        }

        override fun instantiateItem(container: ViewGroup, position: Int): Any {
            var listView=lists[position]
            container.addView(listView)
            return listView
        }

        override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
            container.removeView(lists[position])
        }
    }
}