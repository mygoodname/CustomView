package com.example.myapplication.touchevent

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager
import com.example.myapplication.R
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.activity_touchevent_dispatch_layout.*

class TouchEventDispatchTestActivity: AppCompatActivity() {

    var tablayout:TabLayout?=null
    var viewpager:ViewPager?=null
    var recyclerView:RecyclerView?=null
    var titles= arrayListOf("衣物","特价","活动")
    lateinit var fragments:ArrayList<Fragment>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_touchevent_dispatch_layout)
        initView()
    }

    private fun initView() {
        tablayout=findViewById(R.id.tablayout)
        viewpager=findViewById(R.id.viewpager)
        recyclerView=findViewById(R.id.recyclerView)

        recyclerView?.adapter=MyRecycleViewAdapter()
        fragments= ArrayList()
        for ((itex,velue) in titles.withIndex()){
            fragments.add(MyFragment((itex+1).toString()))
            tablayout?.addTab(TabLayout.Tab().setText(velue))
        }

    }

    inner class MyViewPagerAdapter(fm: FragmentManager, behavior: Int) :
        FragmentPagerAdapter(fm, behavior) {

        override fun getCount(): Int {
            return 0
        }

        override fun getItem(position: Int): Fragment {
            TODO("Not yet implemented")
        }

    }

    inner class MyRecycleViewAdapter : RecyclerView.Adapter<MyRecycleViewAdapter.MyViewHolder>() {
//        var dataList:MutableList<String> = MutableList<String>()

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
            var item=LayoutInflater.from(this@TouchEventDispatchTestActivity).inflate(R.layout.item_recycleview_layout, parent, false)
            return MyViewHolder(item)
        }

        override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
            holder.textView.text = position.toString()
        }

        override fun getItemCount(): Int {
            return 20
        }
        inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            var textView: TextView=itemView.findViewById(R.id.textView)
            var myItemView=itemView
        }
    }


}