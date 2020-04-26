package com.nhatle.workmangement.data.reponsitory

import com.nhatle.workmangement.data.model.UserProfile
import com.nhatle.workmangement.data.OnDataLoadedCallback

interface UserProfileInfoDAO {
    fun findProfileWithUserName(userName:String,callback: OnDataLoadedCallback<UserProfile>)
    fun registerAccount(userProfile: UserProfile,callback: OnDataLoadedCallback<UserProfile>)
    fun updateProfile(userProfile: UserProfile,callback: OnDataLoadedCallback<UserProfile>)
}