package com.nhatle.workmangement.ui.start.login

import com.nhatle.workmangement.data.model.UserProfile
import com.nhatle.workmangement.data.model.response.LoginResponse

interface LoginContract {
    interface View {
        fun loginSuccess(user: UserProfile)
        fun onLoginFailure(error: String)
    }

    interface Presenter {
        fun handleLogin(username: String, password: String)
    }
}