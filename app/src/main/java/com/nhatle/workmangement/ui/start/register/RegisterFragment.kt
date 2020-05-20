package com.nhatle.workmangement.ui.start.register

import android.view.View
import android.widget.Toast
import com.nhatle.workmangement.R
import com.nhatle.workmangement.data.model.UserProfile
import com.nhatle.workmangement.data.model.response.RegisterResponse
import com.nhatle.workmangement.data.reponsitory.remote.UserProfileRemoteRepository
import com.nhatle.workmangement.data.source.remote.UserProfileRemoteDataSource
import com.nhatle.workmangement.ui.base.BaseFragment
import com.nhatle.workmangement.ui.start.login.LoginFragment
import com.nhatle.workmangement.until.Common
import kotlinx.android.synthetic.main.fragment_register.*
import kotlinx.android.synthetic.main.fragment_update_profile.*

class RegisterFragment:BaseFragment(), RegisterContract.View, View.OnClickListener {
    override val layoutResource: Int =R.layout.fragment_register
    private var presenter:RegisterPresenter?=null

    override fun initData() {
        initPresenter()

    }
    override fun initComponents() {
        registerListener()
    }

    private fun registerListener() {
        buttonRegister.setOnClickListener(this)
        imageButtonLogin.setOnClickListener(this)
    }

    private fun initPresenter() {
        val userService = Common.getUserService()
        val dataSource = UserProfileRemoteDataSource.getInstance(userService)
        val repository= UserProfileRemoteRepository(dataSource)
        presenter = RegisterPresenter(this,repository)
    }

    override fun registerSuccess(user: UserProfile) {
        Toast.makeText(context,"is success",Toast.LENGTH_SHORT).show()
    }

    override fun onRegisterFailure(error: String) {

    }

    override fun onRegisteSuccess() {
        Toast.makeText(context,"Đăng ký thành công",Toast.LENGTH_SHORT).show()
        replaceFragment(R.id.frag_start,LoginFragment(),true)
    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.buttonRegister->{
                updateProfile()
            }
            R.id.imageButtonLogin->{
                replaceFragment(R.id.frag_start,LoginFragment(),true)
            }
        }
    }

    private fun updateProfile() {
        if (editUsername.text.isNotEmpty()
            &&editConfirmPass.text.isNotEmpty()
            && editPass.text.isNotEmpty()
            && editAddress.text.isNotEmpty()
            && editEmal.text.isNotEmpty()
            && editFullname.text.isNotEmpty()
            && editPhoneNumber.text.isNotEmpty()){
            if (editPass.text.toString().equals(editConfirmPass.text.toString())){
                val username = editUsername.text.toString()
                val pass = editPass.text.toString()
                val fullName = editFullname.text.toString()
                val address = editAddress.text.toString()
                val email = editEmal.text.toString()
                val phone = editPhoneNumber.text.toString()
                val registerResponse = RegisterResponse(username,pass,null,fullName,
                    address,email,phone,null)
                presenter!!.handleRegister(registerResponse)
            }else{
                Toast.makeText(context,"Mật khẩu phải trùng nhau",Toast.LENGTH_SHORT).show()
            }
        }
    }
}