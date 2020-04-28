package com.nhatle.workmangement.ui.main.profile.update

import com.nhatle.workmangement.data.model.UserProfile

interface UpdateUserProfileContract {
    interface View{
        fun updateSuccess(userProfile:UserProfile)
        fun loadFail(string: String)
    }
    interface Presenter{
        fun update(profile:UserProfile)
    }
}