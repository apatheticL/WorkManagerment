package com.nhatle.workmangement.data.source

import com.nhatle.workmangement.data.OnDataLoadedCallback
import com.nhatle.workmangement.data.model.UserProfile
import com.nhatle.workmangement.data.model.response.RegisterResponse

interface UserProfileDataSource {
    interface Remote{
        fun login(userName:String,password:String,callback: OnDataLoadedCallback<UserProfile>)
        fun registerAccount(registerResponse: RegisterResponse,callback: OnDataLoadedCallback<UserProfile>)
        fun updateProfile(userProfile: UserProfile,callback: OnDataLoadedCallback<UserProfile>)
        fun getInfoProfile(profileId: Int,callback: OnDataLoadedCallback<UserProfile>)
    }
}