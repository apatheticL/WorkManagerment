package com.nhatle.workmangement.data.reponsitory.remote

import com.nhatle.workmangement.data.OnDataLoadedCallback
import com.nhatle.workmangement.data.model.UserTeam
import com.nhatle.workmangement.data.source.UserTeamDataSource
import com.nhatle.workmangement.data.source.remote.UserTeamRemoteDataSource

class UserTeamRepository(private val dataSource: UserTeamRemoteDataSource) :
    UserTeamDataSource.Remote {
    override fun addMemberOnTeam(userTeam: UserTeam, callback: OnDataLoadedCallback<UserTeam>) {
        dataSource.addMemberOnTeam(userTeam = userTeam, callback = callback)
    }

    override fun deleteMemberOnTeam(userTeam: UserTeam, callback: OnDataLoadedCallback<Boolean>) {
        dataSource.deleteMemberOnTeam(userTeam = userTeam, callback = callback)
    }

    override fun getAllMemberOnActionInGroup(
        groupId: Int,
        actionId: Int,
        callback: OnDataLoadedCallback<List<UserTeamRepository>>
    ) {

    }
}