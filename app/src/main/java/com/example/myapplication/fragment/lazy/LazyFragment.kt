package com.example.myapplication.fragment.lazy

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.myapplication.R

abstract class LazyFragment :Fragment(){

    lateinit var rootView:View
    var isCreateView:Boolean=false  //用来判断fragment是否createView
    var currentVisibleState=false   //用来判断当前fragment 的可见状态
    lateinit var fragmentDelegater:FragmentDelegater


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        rootView = inflater.inflate(getResViewId(), container, false)
        D("onCreateView")
        isCreateView=true
        if(userVisibleHint){
            dispatchVisibleHint(true)
        }
        return rootView
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        D("onViewCreated")

        initView(rootView)
    }

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        fragmentDelegater.trackLife("isVisibleToUser  $isVisibleToUser")
        if(isCreateView){
            if(isVisibleToUser && !currentVisibleState)
                dispatchVisibleHint(true)
            else if(!isVisibleToUser && currentVisibleState)
                dispatchVisibleHint(false)
        }
    }

    fun dispatchVisibleHint(isVisible:Boolean){

        if(currentVisibleState==isVisible)//判断当前显示状态是否和要更新的状态一致 避免重复加载
        // （setUserVisibleHint() 和 onCreateView()方法 先后调用该方法 ）
            return
        currentVisibleState=isVisible
        D("currentVisibleState  $currentVisibleState")
        if (isVisible)
            loadData()
        else
            loadDataStop()
    }
    protected open fun loadData(){}

    protected open fun loadDataStop(){}

    abstract fun initView(view: View)

    abstract fun getResViewId():Int

    override fun onPause() {
        super.onPause()
        D("onPause")
        if (currentVisibleState && userVisibleHint){
            dispatchVisibleHint(false)
        }
    }

    override fun onResume() {
        super.onResume()
        D("onResume")
        if(!currentVisibleState && userVisibleHint){
            dispatchVisibleHint(true)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        D("onDestroy")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        D("onDestroyView")
        isCreateView=false       //被消销毁后置false
        currentVisibleState=false   //被消销毁后置false
    }

    fun D(funcation:String){
        fragmentDelegater?.trackLife(funcation)
    }
}