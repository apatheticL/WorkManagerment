package com.nhatle.workmangement.ui.start.register

import com.nhatle.workmangement.data.model.UserProfile
import com.nhatle.workmangement.data.model.response.RegisterResponse
import com.nhatle.workmangement.data.reponsitory.remote.UserProfileRemoteRepository
import com.nhatle.workmangement.data.OnDataLoadedCallback
import com.nhatle.workmangement.data.model.response.UserTeamResponse
import java.lang.Exception

class RegisterPresenter(var registerView: RegisterContract.View,
                        var repository: UserProfileRemoteRepository):RegisterContract.Presenter {
    override fun handleRegister(registerResponse: RegisterResponse) {
        repository.registerAccount(registerResponse,object :
            OnDataLoadedCallback<UserProfile> {
            override fun onSuccess(data: ArrayList<UserTeamResponse>?) {
                registerView.registerSuccess(user = data)
            }

            override fun onFailedConnect(string: String) {
                registerView.onRegisterFailure(string)
            }

            override fun onFailed(exception: Exception) {
                exception.message?.let { registerView.onRegisterFailure(it) }
            }

        })
    }


}