package com.nhatle.workmangement.ui.start.register

import android.view.View
import android.widget.Toast
import com.nhatle.workmangement.R
import com.nhatle.workmangement.data.model.UserProfile
import com.nhatle.workmangement.data.reponsitory.remote.UserProfileRemoteRepository
import com.nhatle.workmangement.data.source.remote.UserProfileRemoteDataSource
import com.nhatle.workmangement.ui.base.BaseFragment
import com.nhatle.workmangement.until.Common
import com.nhatle.workmangement.until.api.UserService
import kotlinx.android.synthetic.main.fragment_register.*

class RegisterFragment:BaseFragment(), RegisterContract.View, View.OnClickListener {
    override val layoutResource: Int =R.layout.fragment_register
    private var presenter:RegisterPresenter?=null
    private var userService:UserService?=null
    override fun initData() {
        initPresenter()
        userService = Common.getUserService()
    }
    override fun initComponents() {
        registerListener()
    }

    private fun registerListener() {
        buttonRegister.setOnClickListener(this)
        imageButtonLogin.setOnClickListener(this)
    }

    private fun initPresenter() {
        val dataSource = UserProfileRemoteDataSource.getInstance(userService!!)
        val repository= UserProfileRemoteRepository(dataSource)
        presenter = RegisterPresenter(this,repository)
    }

    override fun registerSuccess(user: UserProfile) {
        Toast.makeText(context,"is success",Toast.LENGTH_SHORT).show()
    }

    override fun onRegisterFailure(error: String) {

    }

    override fun onClick(v: View?) {
        TODO("Not yet implemented")
    }
}