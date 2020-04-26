package com.nhatle.workmangement.ui.main.action.detail

import com.nhatle.workmangement.data.model.response.UserTeamResponse

interface ActionDetailContract {
    interface View{
        fun showAllMember(list:ArrayList<UserTeamResponse>)
        fun onFailed(string: String)
    }
    interface Presenter{
        fun getAllMemberOnGroup(groupId:Int,actionId:Int)
    }
}