package com.nhatle.workmangement.ui.start.login

import com.nhatle.workmangement.data.model.UserProfile
import com.nhatle.workmangement.data.reponsitory.remote.UserProfileRemoteRepository
import com.nhatle.workmangement.data.OnDataLoadedCallback
import com.nhatle.workmangement.data.model.response.UserTeamResponse
import java.lang.Exception

class LoginPresenter(var loginView:LoginContract.View ,
                     var remoteRepository: UserProfileRemoteRepository
):LoginContract.Presenter {
    override fun handleLogin(username: String, password: String) {
        remoteRepository.login(username,password,object :
            OnDataLoadedCallback<UserProfile> {
            override fun onFailedConnect(string: String) {
                loginView.onLoginFailure(string)
            }

            override fun onFailed(exception: Exception) {
                exception.message?.let { loginView.onLoginFailure(it) }
            }

            override fun onSuccess() {

            }

            override fun onSuccess(data: UserProfile) {
                loginView.loginSuccess(data)
            }

        })
    }
}