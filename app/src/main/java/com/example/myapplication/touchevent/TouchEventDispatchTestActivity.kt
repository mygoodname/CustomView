package com.example.myapplication.touchevent

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout.OnRefreshListener
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager
import com.example.myapplication.R
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.activity_touchevent_dispatch_layout.*

class TouchEventDispatchTestActivity: AppCompatActivity() {

    var tablayout:TabLayout?=null
    var viewpager:ViewPager?=null
    var recyclerView:RecyclerView?=null
    var titles= arrayListOf("衣物", "特价", "活动")
    var swipeRefresh:SwipeRefreshLayout?=null
    lateinit var fragments:ArrayList<Fragment>
    var recycleViews:ArrayList<RecyclerView> = ArrayList<RecyclerView>()

    companion object{
        fun startActivity(activity: Activity){
            var intent= Intent(activity, TouchEventDispatchTestActivity::class.java)
            activity.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        supportActionBar?.hide()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_touchevent_dispatch_layout)
        initView()
    }

    private fun initView() {
        tablayout=findViewById(R.id.tablayout)
        viewpager=findViewById(R.id.viewpager)
        recyclerView=findViewById(R.id.recyclerView)
        swipeRefresh=findViewById(R.id.swipeRefresh)

        swipeRefresh?.measuredHeight
        recyclerView?.layoutManager=LinearLayoutManager(this)
        recyclerView?.adapter=MyRecycleViewAdapter()
        fragments= ArrayList()
//        for ((itex, velue) in titles.withIndex()){
//            fragments.add(MyFragment((itex + 1).toString()))
//            tablayout?.addTab(TabLayout.Tab())
//        }
        for ((itex, velue) in titles.withIndex()){
            var recyclerView=RecyclerView(this)
            recyclerView.layoutManager=LinearLayoutManager(this)
            recyclerView.adapter=MyRecycleViewAdapter()
            recycleViews.add(recyclerView)
        }

        swipeRefresh?.setOnRefreshListener(OnRefreshListener {
            Handler().postDelayed({
                swipeRefresh?.isRefreshing = false
            }, 2000)
        })




        viewpager?.adapter=MyViewPagerAdapter()
        tablayout?.setupWithViewPager(viewpager)

        var one = tablayout?.getTabAt(0)
        var two = tablayout?.getTabAt(1)
        var three = tablayout?.getTabAt(2)

        one?.setIcon(getResources().getDrawable(R.mipmap.ic_ask_selected))
        two?.setIcon(getResources().getDrawable(R.mipmap.ic_selected_user_center))
        three?.setIcon(getResources().getDrawable(R.mipmap.ic_main_bookshelf_select))

    }

    inner class MyViewPagerAdapter :
        PagerAdapter() {

        override fun getCount(): Int {
            return recycleViews.size
        }

        override fun isViewFromObject(view: View, `object`: Any): Boolean {
            return view==`object`
        }

        override fun instantiateItem(container: ViewGroup, position: Int): Any {
            var layoutParams=ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT)
            //添加一个布局（不添加无效果）
            container.addView(recycleViews[position],layoutParams);
            recycleViews[position].adapter?.notifyDataSetChanged()
            return recycleViews[position]
        }
        override fun getPageTitle(position: Int): CharSequence? {
            return titles[position]
        }

        override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
            container.removeView(recycleViews[position])
        }

    }

    inner class MyRecycleViewAdapter : RecyclerView.Adapter<MyRecycleViewAdapter.MyViewHolder>() {
//        var dataList:MutableList<String> = MutableList<String>()

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
            var item=LayoutInflater.from(this@TouchEventDispatchTestActivity).inflate(
                R.layout.item_recycleview_layout,
                parent,
                false
            )
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