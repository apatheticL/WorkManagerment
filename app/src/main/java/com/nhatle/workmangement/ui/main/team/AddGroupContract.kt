package com.nhatle.workmangement.ui.main.team

import com.nhatle.workmangement.data.model.Team
import com.nhatle.workmangement.data.model.UserTeam

interface AddGroupContract {
    interface View{
        fun insertGroupSuccess(team:Team)
        fun insertUserGroupSuccess(list: List<UserTeam>)
        fun onFail(string: String)
    }
    interface Presenter{
        fun insertGroup(team: Team)
        fun insertUserGroup(list: List<UserTeam>)
    }
}