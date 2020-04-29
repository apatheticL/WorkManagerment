package com.nhatle.workmangement.ui.main.team

import com.nhatle.workmangement.data.model.Team
import com.nhatle.workmangement.data.model.UserTeam
import com.nhatle.workmangement.data.model.response.FriendResponse

interface AddGroupContract {
    interface View{
        fun showAllData(list: List<FriendResponse>)
        fun insertGroupSuccess(team:Team)
        fun insertUserGroupSuccess()
        fun onFail(string: String)
    }
    interface Presenter{
        fun insertGroup(team: Team)
        fun insertUserGroup(list: List<UserTeam>)
        fun getAllUserIsFriend(profileId:Int)
    }
}