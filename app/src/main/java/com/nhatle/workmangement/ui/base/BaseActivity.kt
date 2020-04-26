package com.nhatle.workmangement.ui.base

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDialogFragment
import androidx.fragment.app.Fragment

abstract class BaseActivity:AppCompatActivity() {
    protected abstract val layoutResource:Int
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layoutResource)
        initComponent()
        initData()
    }

    protected abstract fun initComponent()

    protected abstract fun initData()
    protected fun replaceFragment(id:Int,fragment:Fragment,addToBackTack:Boolean){
        supportFragmentManager.beginTransaction().replace(id,fragment).apply {
            if(addToBackTack)addToBackStack(null)
        }.commit()
    }

    protected fun addFragment(id:Int,fragment:Fragment,addToBackTack:Boolean){
        supportFragmentManager.beginTransaction().add(id,fragment).apply {
            if(addToBackTack)addToBackStack(null)
        }.commit()
    }
    protected fun openActivity(intent: Intent){
        startActivity(intent)
    }

}