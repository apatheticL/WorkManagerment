package com.nhatle.workmangement.ui.main.profile

import com.nhatle.workmangement.data.model.UserProfile

interface UserProfileContract {
    interface View{
        fun showDataProfile(userProfile:UserProfile)
        fun loadFail(string: String)
    }
    interface Presenter{
        fun getAllDataUserProfile(profileId:Int)
    }
}