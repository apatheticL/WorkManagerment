package com.nhatle.workmangement.ui.start.register

import com.nhatle.workmangement.data.model.UserProfile
import com.nhatle.workmangement.data.model.response.RegisterResponse

interface RegisterContract {
    interface View {
        fun registerSuccess(user: UserProfile)
        fun onRegisterFailure(error: String)
        fun onRegisteSuccess()
    }

    interface Presenter {
        fun handleRegister(registerResponse: RegisterResponse)
    }
}