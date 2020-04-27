package com.nhatle.workmangement.data.reponsitory.remote

import com.nhatle.workmangement.data.OnDataLoadedCallback
import com.nhatle.workmangement.data.model.Action
import com.nhatle.workmangement.data.model.response.ActionResponse
import com.nhatle.workmangement.data.model.response.UserTeamResponse
import com.nhatle.workmangement.data.source.ActionDataSource
import com.nhatle.workmangement.data.source.remote.ActionRemoteDataSource

class ActionRemoteRepository(private val dataSource: ActionRemoteDataSource) :
    ActionDataSource.Remote {


    override fun getAllActionUserMember(
        profileId: Int,
        callback: OnDataLoadedCallback<List<ActionResponse>>
    ) {
        dataSource.getAllActionUserMember(profileId = profileId, callback = callback)
    }

    override fun insertAction(action: Action, callback: OnDataLoadedCallback<Action>) {
        dataSource.insertAction(action = action, callback = callback)
    }

    override fun deleteAction(actionId: Int,profileId: Int, callback: OnDataLoadedCallback<Boolean>) {
        dataSource.deleteAction(actionId = actionId,profileId = profileId, callback = callback)
    }

    override fun updateAction(action: Action, callback: OnDataLoadedCallback<Action>) {
        dataSource.updateAction(action = action, callback = callback)
    }

    override fun getAllMemberOnAction(
        actionId: Int,
        idTeam: Int,
        callback: OnDataLoadedCallback<List<UserTeamResponse>>
    ) {
        dataSource.getAllMemberOnAction(actionId = actionId, idTeam = idTeam, callback = callback)
    }


}