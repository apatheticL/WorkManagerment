package com.nhatle.workmangement.ui.main.action.add.actionSmall

import com.nhatle.workmangement.data.model.ActionSmall
import com.nhatle.workmangement.data.model.UserActionSmall
import com.nhatle.workmangement.data.model.response.UserActionSmallResponse
import com.nhatle.workmangement.data.model.response.UserTeamResponse

interface AddUserActionSmallContract {
    interface View{
        fun insertUserActionSmallSuccess()
        fun showAllActionSmall(actionSmallResponse: List<ActionSmall>)
        fun onFail(string: String)
        fun getAllMemberOnAction(list: List<UserTeamResponse>)
    }
    interface Presenter{
        fun getAllActionSmall(actionId:Int)
        fun insertUserActionSmall(userActionSmall: UserActionSmall)
        fun getAllMemberOnAction(actionId: Int,groupId:Int)
    }

}