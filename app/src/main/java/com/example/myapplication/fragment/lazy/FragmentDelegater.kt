package com.example.myapplication.fragment.lazy

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.myapplication.IDelegate

class FragmentDelegater :Fragment{

    lateinit var mFragment:Fragment

    constructor(mFragment: Fragment) {
        this.mFragment = mFragment
    }


    override fun onAttach(context: Context) {
        super.onAttach(context)
        trackLife("onAttach(context)")

    }

    override fun onAttach(activity: Activity) {
        super.onAttach(activity)
        trackLife("onAttach(activity)")

    }

    override fun onPause() {
        super.onPause()
        trackLife("onPause")

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        trackLife("onViewCreated")

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        trackLife("onCreate")

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        trackLife("onActivityCreated")

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        trackLife("onCreateView")

        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onResume() {
        super.onResume()
        trackLife("onResume")

    }

    override fun onDestroy() {
        super.onDestroy()
        trackLife("onDestroy")

    }

    override fun onDestroyView() {
        super.onDestroyView()
        trackLife("onDestroyView")


    }

    override fun onDetach() {
        super.onDetach()
        trackLife("onDetach")
    }
    fun trackLife(str:String){
        Log.d("trackLife",mFragment.javaClass.simpleName+"-->"+str)
    }
}