package com.example.myapplication.touchevent

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.myapplication.R

class MyFragment : Fragment {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
    var pager:String="1"
    constructor(page:String):super(){
        pager=page
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view:View=inflater.inflate(R.layout.fragment_1_layout,container,false)
        initView(view)
        return view
//        return super.onCreateView(inflater, container, savedInstanceState)
    }

    fun initView(view:View){
        view.findViewById<TextView>(R.id.one).text = pager
    }

}