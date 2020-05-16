package com.nhatle.workmangement.ui.main.user_action_small

import com.nhatle.workmangement.data.model.response.UserActionSmallResponse

interface UserActionSmallContract {
    interface View{
        fun loadAllActionSmall(listAction:List<UserActionSmallResponse>)
        fun loadFailed(error:String)
        fun deleteSuccess()
        fun deleteFail(string: String)
    }
    interface Presenter{
        fun getAllUserActionSmallByAction(actionId: Int)
        fun deleteUserActionSmall(userActionSmallId:Int)
    }
}