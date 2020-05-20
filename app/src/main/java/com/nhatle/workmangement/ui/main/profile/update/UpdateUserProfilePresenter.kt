package com.nhatle.workmangement.ui.main.profile.update

import com.nhatle.workmangement.data.OnDataLoadedCallback
import com.nhatle.workmangement.data.model.UserProfile
import com.nhatle.workmangement.data.reponsitory.remote.UserProfileRemoteRepository
import java.lang.Exception

class UpdateUserProfilePresenter(
    val view: UpdateUserProfileContract.View,
    val repository: UserProfileRemoteRepository
) :UpdateUserProfileContract.Presenter{

    override fun update(profile: UserProfile) {
        repository.updateProfile(profile,object :OnDataLoadedCallback<UserProfile>{
            override fun onSuccess(data: UserProfile) {
                view.updateSuccess(data)
            }

            override fun onSuccess() {

            }

            override fun onFailedConnect(string: String) {
                view.loadFail(string)
            }

            override fun onFailed(exception: Exception) {
               view.loadFail(exception.message.toString())
            }

        })
    }
}