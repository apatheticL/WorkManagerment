package com.nhatle.workmangement.ui.start.login

import android.view.View
import android.widget.Toast
import com.nhatle.workmangement.R
import com.nhatle.workmangement.data.model.UserProfile
import com.nhatle.workmangement.data.reponsitory.remote.UserProfileRemoteRepository
import com.nhatle.workmangement.data.source.remote.UserProfileRemoteDataSource
import com.nhatle.workmangement.ui.base.BaseFragment
import com.nhatle.workmangement.ui.start.StartActivity
import com.nhatle.workmangement.ui.start.register.RegisterFragment
import com.nhatle.workmangement.until.Common
import com.nhatle.workmangement.until.CommonData
import com.nhatle.workmangement.until.ShareUntil
import kotlinx.android.synthetic.main.fragment_login.*

class LoginFragment : BaseFragment(), LoginContract.View, View.OnClickListener {
    override val layoutResource: Int = R.layout.fragment_login
    private var presenter: LoginPresenter? = null

    override fun initData() {
        registerListeners()
    }

    private fun registerListeners() {
        buttonLogin.setOnClickListener(this)
        textRegister.setOnClickListener(this)
    }

    override fun initComponents() {
        initPresenter()
    }

    private fun initPresenter() {
        val userService = Common.getUserService()
        val dataSource = UserProfileRemoteDataSource.getInstance(userService)
        val userProfileRemoteRepository = UserProfileRemoteRepository(dataSource)
        presenter = LoginPresenter(this, userProfileRemoteRepository)
    }

    override fun loginSuccess(user: UserProfile) {
        (activity as StartActivity).openActivityHome()
        CommonData.getInstance().profile = user
        context?.let { ShareUntil.saveUserProfile(it, user) }
    }

    override fun onLoginFailure(error: String) {
        Toast.makeText(context,error,Toast.LENGTH_SHORT).show()
    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.buttonLogin -> {
                if (userName.text.toString().isNotEmpty() || passWord.text.toString()
                        .isNotEmpty()
                ) {
                    presenter!!.handleLogin(
                        username = userName.text.toString(),
                        password = passWord.text.toString()
                    )
                }
            }
            R.id.textRegister -> {
                replaceFragment(R.id.frag_start, RegisterFragment(), true)
            }
        }
    }
}