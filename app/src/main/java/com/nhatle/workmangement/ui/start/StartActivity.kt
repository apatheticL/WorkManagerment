package com.nhatle.workmangement.ui.start

import android.content.Intent
import com.nhatle.workmangement.R
import com.nhatle.workmangement.ui.MainActivity
import com.nhatle.workmangement.ui.base.BaseActivity
import com.nhatle.workmangement.ui.start.login.LoginFragment

class StartActivity: BaseActivity() {
    override val layoutResource: Int= R.layout.activity_start

    override fun initComponent() {
        addFragment(R.id.frag_start,LoginFragment(),false)
    }

    override fun initData() {

    }

    fun openActivityHome() {
        val intent = Intent(this,MainActivity::class.java)
        openActivity(intent)
        finish()
    }

}