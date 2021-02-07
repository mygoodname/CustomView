package com.example.myapplication.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager
import com.example.myapplication.R

class ViewPagerTestFragment: Fragment() {
    var viewPager:ViewPager?=null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_viewpager_test,container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewPager=view.findViewById(R.id.myViewPager)

        viewPager?.adapter=MyViewPagerAdapter()

    }

    class MyViewPagerAdapter : PagerAdapter(){

        var viewList:MutableList<View> =ArrayList()

        override fun getCount(): Int {
            return viewList.size
        }

        override fun isViewFromObject(view: View, `object`: Any): Boolean {
            return view==`object`
        }

        override fun instantiateItem(container: ViewGroup, position: Int): Any {

            return LayoutInflater.from()
        }
    }
}