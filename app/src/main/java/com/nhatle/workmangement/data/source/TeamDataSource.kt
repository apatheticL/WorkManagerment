package com.nhatle.workmangement.data.source

import com.nhatle.workmangement.data.OnDataLoadedCallback
import com.nhatle.workmangement.data.model.Team

interface TeamDataSource {
    interface Remote{
        fun deleteTeam(temId:Int,callback: OnDataLoadedCallback<Boolean>)
        fun addTeam(team: Team,callback: OnDataLoadedCallback<Team>)
    }

}