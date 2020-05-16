package com.nhatle.workmangement.ui.main.profile

import com.nhatle.workmangement.data.OnDataLoadedCallback
import com.nhatle.workmangement.data.model.UserProfile
import com.nhatle.workmangement.data.reponsitory.remote.UserProfileRemoteRepository
import java.lang.Exception

class UserProfilePresenter(
    val view: UserProfileContract.View,
    val repository: UserProfileRemoteRepository
) :UserProfileContract.Presenter{
    override fun getAllDataUserProfile(profileId: Int) {
        repository.getInfoProfile(profileId,object :OnDataLoadedCallback<UserProfile>{
            override fun onFailed(exception: Exception) {
                view.loadFail(exception.message.toString())
            }

            override fun onFailedConnect(string: String) {
                view.loadFail(string)
            }

            override fun onSuccess() {

            }

            override fun onSuccess(data: UserProfile) {
                view.showDataProfile(data)
            }
        })
    }
}