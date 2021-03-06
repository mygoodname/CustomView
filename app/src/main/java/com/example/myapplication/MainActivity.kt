package com.example.myapplication

import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.cipher.AESUtil
import com.example.myapplication.cipher.RSAUtils
import com.example.myapplication.coroutine.CoroutineTestActivity
import com.example.myapplication.touchevent.DispatchEventActivity
import com.example.myapplication.touchevent.NestedScrollTestActivity
import com.example.myapplication.utils.PerMissionUtils
import com.example.myapplication.utils.ViewType
import java.io.File

class MainActivity : AppCompatActivity() {


    var overWriteView: TextView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initView()
    }

    fun initView() {
        overWriteView = findViewById(R.id.overWriteView)
        var flowLayout:TextView=findViewById(R.id.flowLayout)
        var viewPager: TextView =findViewById(R.id.viewPager)
        var kotlinCoroutineTest:TextView=findViewById(R.id.kotlin_coroutine)
        var nestedScrollTest:TextView=findViewById(R.id.nestedScrollTest)
        var dispatchEvent:TextView=findViewById(R.id.dispatchEvent)
        var cipher:TextView=findViewById(R.id.cipherAES)
        var cipherRSA:TextView=findViewById(R.id.cipherRSA)

        overWriteView?.setOnClickListener {
            CommonActivity.startActivity(this, ViewType.WriteView)
        }
        flowLayout?.setOnClickListener{
            CommonActivity.startActivity(this, ViewType.FlowLayout)
        }
        viewPager?.setOnClickListener{
            CommonActivity.startActivity(this,ViewType.ViewPager)
        }
        kotlinCoroutineTest?.setOnClickListener{
            CoroutineTestActivity.startActivity(this)
        }
        nestedScrollTest?.setOnClickListener{
            NestedScrollTestActivity.startActivity(this)
        }
        dispatchEvent?.setOnClickListener{
            DispatchEventActivity.startActivity(this)
        }
        cipher?.setOnClickListener{
            if(PerMissionUtils.check(this)){
//                AESUtil.decryptFile( File("file:///android_asset/1.xhtml"),"01.xhtml","file:///android_asset/","qDwnHTTcq18X7lGD");
                AESUtil.decryptFile( assets.open("1.xhtml"),"01.xhtml","/storage/emulated/0/","qDwnHTTcq18X7lGD");
            }
        }
        cipherRSA?.setOnClickListener{
            var hashMap=RSAUtils.initKey()
            Log.d("RSA","PUBLIC_KEY------"+ RSAUtils.getPublicKey(hashMap));
            Log.d("RSA","PRIVATE_KEY------"+ RSAUtils.getPrivateKey(hashMap));
        }
    }

}