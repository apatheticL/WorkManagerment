package com.nhatle.workmangement.ui.main.action.small

import com.nhatle.workmangement.data.model.response.UserActionReportResponse
import com.nhatle.workmangement.data.model.response.UserActionSmallResponse
import com.nhatle.workmangement.data.model.response.UserTeamResponse

interface UserActionSmallContract {
    interface View{
        fun loadAllActionSmall(listAction:List<UserActionSmallResponse>)
        fun loadFailed(error:String)
        fun loadAllActionSmallByUser(listUserActionSmall: List<UserActionSmallResponse>)
        fun loadMyActionFailed(error: String)
    }
    interface Presenter{
        fun getAllUserActionSmallByAction(actionId: Int)
        fun getAllActionSmallOfUser(actionId: Int,profileId:Int)
    }
}