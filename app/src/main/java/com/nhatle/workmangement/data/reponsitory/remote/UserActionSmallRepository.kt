package com.nhatle.workmangement.data.reponsitory.remote

import com.nhatle.workmangement.data.OnDataLoadedCallback
import com.nhatle.workmangement.data.model.UserActionSmall
import com.nhatle.workmangement.data.model.response.UserActionSmallResponse
import com.nhatle.workmangement.data.model.response.UserTeamResponse
import com.nhatle.workmangement.data.source.UserActionSmallDataSource
import com.nhatle.workmangement.data.source.remote.UserActionSmallRemoteDataSource

class UserActionSmallRepository(private val dataSource: UserActionSmallRemoteDataSource) :
    UserActionSmallDataSource.Remote {
    override fun getALlUserActionSmall(
        actionId: Int,
        callback: OnDataLoadedCallback<List<UserActionSmallResponse>>
    ) {
        dataSource.getALlUserActionSmall(actionId = actionId, callback = callback)
    }

    override fun getAllActionSmallByUser(
        actionId: Int,
        profileId: Int,
        callback: OnDataLoadedCallback<List<UserActionSmallResponse>>
    ) {
        dataSource.getAllActionSmallByUser(
            actionId = actionId,
            profileId = profileId,
            callback = callback
        )
    }

    override fun addUserActionSmall(
        userActionSmall: UserActionSmall,
        callback: OnDataLoadedCallback<UserActionSmall>
    ) {
        dataSource.addUserActionSmall(userActionSmall = userActionSmall, callback = callback)
    }

    override fun deleteUserActionSmall(
        userActionSmallId: Int,
        callback: OnDataLoadedCallback<Boolean>
    ) {
        dataSource.deleteUserActionSmall(
          userActionSmallId = userActionSmallId, callback = callback
        )
    }

    override fun updateUserActionSmall(
        userActionSmall: UserActionSmall,
        callback: OnDataLoadedCallback<UserActionSmall>
    ) {
        dataSource.updateUserActionSmall(userActionSmall = userActionSmall, callback = callback)
    }

    override fun getAllMemberOnActionInGroup(
        groupId: Int,
        actionId: Int,
        callback: OnDataLoadedCallback<List<UserTeamResponse>>
    ) {
        dataSource.getAllMemberOnActionInGroup(groupId, actionId, callback)
    }
}