package com.nhatle.workmangement.ui.main.action.add.actionSmall.dialog

import com.nhatle.workmangement.data.model.ActionSmall
import com.nhatle.workmangement.data.model.UserTeam
import com.nhatle.workmangement.data.model.response.UserActionReportResponse
import com.nhatle.workmangement.data.model.response.UserActionSmallResponse
import com.nhatle.workmangement.data.model.response.UserTeamResponse

interface DialogUserTeamContract {
    interface View{
        fun loadFailed(error:String)
        fun getAllUserTeam(data: List<UserTeamResponse>)

    }
    interface Presenter{
        fun getAllUserTeamAction(actionId: Int,groupId:Int)
    }
}