package com.nhatle.workmangement.data.reponsitory.remote

import com.nhatle.workmangement.data.OnDataLoadedCallback
import com.nhatle.workmangement.data.model.Team
import com.nhatle.workmangement.data.source.TeamDataSource
import com.nhatle.workmangement.data.source.remote.TeamRemoteDataSource

class TeamRemoteRepository(private val dataSource: TeamRemoteDataSource) : TeamDataSource.Remote {
    override fun deleteTeam(temId: Int, callback: OnDataLoadedCallback<Boolean>) {
        dataSource.deleteTeam(temId = temId, callback = callback)
    }

    override fun addTeam(team: Team, callback: OnDataLoadedCallback<Team>) {
        dataSource.addTeam(team = team, callback = callback)
    }
}