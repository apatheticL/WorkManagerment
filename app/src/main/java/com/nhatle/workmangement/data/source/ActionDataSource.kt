package com.nhatle.workmangement.data.source

import com.nhatle.workmangement.data.OnDataLoadedCallback
import com.nhatle.workmangement.data.model.Action
import com.nhatle.workmangement.data.model.response.ActionResponse
import com.nhatle.workmangement.data.model.response.UserTeamResponse

interface ActionDataSource {
    interface Remote{
        fun getAllActionUserMember(profileId:Int,callback: OnDataLoadedCallback<List<ActionResponse>>)
        fun insertAction(action:Action,callback: OnDataLoadedCallback<Action>)
        fun deleteAction(action: Action,callback: OnDataLoadedCallback<Boolean>)
        fun updateAction(action: Action,callback: OnDataLoadedCallback<Action>)
        fun getAllMemberOnAction(actionId:Int, idTeam:Int,callback: OnDataLoadedCallback<List<UserTeamResponse>>)
    }
    interface Local{
        fun getAllActionUserCreate(profileId:Int):List<Action>
        fun getAllActionUserMember(profileId:Int):List<Action>
        fun insertAction(action:Action)
        fun deleteAction(action: Action)
        fun updateAction(action: Action)
        fun getAllMemberOnAction(actionId:Int, idTeam:Int):List<UserTeamResponse>
    }

}