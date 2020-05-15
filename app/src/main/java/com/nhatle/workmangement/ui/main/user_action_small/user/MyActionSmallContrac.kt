package com.nhatle.workmangement.ui.main.user_action_small.user

import com.nhatle.workmangement.data.model.response.UserActionSmallResponse

interface MyActionSmallContrac {
    interface View{
        fun loadAllActionSmallByUser(listUserActionSmall: List<UserActionSmallResponse>)
        fun loadMyActionFailed(error: String)
    }
    interface Presenter{
        fun getAllActionSmallOfUser(actionId: Int,profileId:Int)
    }
}