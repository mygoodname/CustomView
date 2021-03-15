package com.example.myapplication.fragment.viewpager

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.viewpager.widget.ViewPager
import com.example.myapplication.R

class ViewPagerTestFragment: Fragment(),View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }

    override fun onStop() {
        super.onStop()
    }

    override fun onStart() {
        super.onStart()
    }

    override fun onDestroyView() {
        super.onDestroyView()
    }

    override fun onDestroy() {
        super.onDestroy()
    }
    override fun onDetach() {
        super.onDetach()
    }
    var viewPager:ViewPager?=null
    lateinit var tab1:LinearLayout
    lateinit var tab2:LinearLayout
    lateinit var tab3:LinearLayout
    lateinit var tab4:LinearLayout
    lateinit var tab5:LinearLayout
    var PagerFragmentTag:Int=0


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_viewpager_test,container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView(view)
    }

    fun initView(view: View){
        viewPager=view.findViewById(R.id.myViewPager)
        tab1=view.findViewById(R.id.tab_1)
        tab2=view.findViewById(R.id.tab_2)
        tab3=view.findViewById(R.id.tab_3)
        tab4=view.findViewById(R.id.tab_4)
        tab5=view.findViewById(R.id.tab_5)

        tab1.setOnClickListener(this)
        tab2.setOnClickListener(this)
        tab3.setOnClickListener(this)
        tab4.setOnClickListener(this)
        tab5.setOnClickListener(this)
        viewPager?.setOnPageChangeListener(object :ViewPager.OnPageChangeListener{
            override fun onPageScrollStateChanged(state: Int) {
            }

            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
            }

            override fun onPageSelected(position: Int) {
                PagerFragmentTag=position
                checkTab()
            }

        })


        var myViewPagerAdapter=fragmentManager?.let {
            MyViewPagerAdapter(
                it
            )
        }
        myViewPagerAdapter?.setFragments(arrayListOf(MyFragment1(),MyFragment2(),MyFragment3(),MyFragment4(),MyFragment5()))
        viewPager?.adapter= myViewPagerAdapter

        checkTab()
    }

    class MyViewPagerAdapter(fm: FragmentManager) : FragmentStatePagerAdapter(fm) {

        var viewList:MutableList<Fragment> =ArrayList()

        fun setFragments(fragments:ArrayList<Fragment>){
            viewList=fragments
        }

        override fun getCount(): Int {
            return viewList.size
        }

        override fun getItem(position: Int): Fragment {
            return viewList[position]
        }
    }

    private fun checkTab() {
        tab1.isSelected = PagerFragmentTag == 0
        tab2.isSelected = PagerFragmentTag == 1
        tab3.isSelected = PagerFragmentTag == 2
        tab4.isSelected = PagerFragmentTag == 3
        tab5.isSelected = PagerFragmentTag == 4

    }
    override fun onClick(v: View?) {
        when(v){
            tab1 ->{
                viewPager?.currentItem = 0
                PagerFragmentTag=0
            }
            tab2 ->{
                viewPager?.currentItem = 1
                PagerFragmentTag=1

            }
            tab3 ->{
                viewPager?.currentItem = 2
                PagerFragmentTag=2

            }
            tab4 ->{
                viewPager?.currentItem = 3
                PagerFragmentTag=3
            }
            tab5 ->{
                viewPager?.currentItem = 4
                PagerFragmentTag=4

            }
            else ->{}
        }
        checkTab()
    }
}