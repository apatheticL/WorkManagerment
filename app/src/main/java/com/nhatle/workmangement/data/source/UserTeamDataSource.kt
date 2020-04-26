package com.nhatle.workmangement.data.source

import com.nhatle.workmangement.data.OnDataLoadedCallback
import com.nhatle.workmangement.data.model.UserTeam
import com.nhatle.workmangement.data.reponsitory.remote.UserTeamRepository

interface UserTeamDataSource {

    interface Remote{
        fun addMemberOnTeam(userTeam:UserTeam,callback: OnDataLoadedCallback<UserTeam>)
        fun deleteMemberOnTeam(userTeam: UserTeam,callback: OnDataLoadedCallback<Boolean>)

    }
    interface Local{
        fun addMemberOnTeam(userTeam:UserTeam)
        fun deleteMemberOnTeam(userTeam: UserTeam)
    }
}