package com.nhatle.workmangement.data.reponsitory.remote

import com.nhatle.workmangement.data.OnDataLoadedCallback
import com.nhatle.workmangement.data.model.UserProfile
import com.nhatle.workmangement.data.model.response.RegisterResponse
import com.nhatle.workmangement.data.source.UserProfileDataSource
import com.nhatle.workmangement.data.source.remote.UserProfileRemoteDataSource

class UserProfileRemoteRepository(private val dataSource: UserProfileRemoteDataSource) :
    UserProfileDataSource.Remote {
    override fun login(
        userName: String,
        password: String,
        callback: OnDataLoadedCallback<UserProfile>
    ) {
        dataSource.login(userName, password, callback)
    }

    override fun registerAccount(
        registerResponse: RegisterResponse,
        callback: OnDataLoadedCallback<UserProfile>
    ) {
        dataSource.registerAccount(registerResponse, callback)
    }

    override fun updateProfile(
        userProfile: UserProfile,
        callback: OnDataLoadedCallback<UserProfile>
    ) {
        dataSource.updateProfile(userProfile, callback)
    }

    override fun getInfoProfile(profileId: Int, callback: OnDataLoadedCallback<UserProfile>) {
        dataSource.getInfoProfile(profileId, callback)
    }

}