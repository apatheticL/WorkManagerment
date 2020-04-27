package com.nhatle.workmangement.ui.main.team

import com.nhatle.workmangement.data.OnDataLoadedCallback
import com.nhatle.workmangement.data.model.Team
import com.nhatle.workmangement.data.model.UserTeam
import com.nhatle.workmangement.data.reponsitory.remote.TeamRemoteRepository
import com.nhatle.workmangement.data.reponsitory.remote.UserActionSmallRepository
import com.nhatle.workmangement.data.reponsitory.remote.UserTeamRepository
import java.lang.Exception

class AddGroupPresenter(val view: AddGroupContract.View,
val groupRepository: TeamRemoteRepository,
val userGroupRepository: UserTeamRepository):AddGroupContract.Presenter {
    override fun insertGroup(team: Team) {
        groupRepository.addTeam(team,
        object :OnDataLoadedCallback<Team>{
            override fun onSuccess(data: Team) {
                view.insertGroupSuccess(data)
            }

            override fun onSuccess() {

            }

            override fun onFailedConnect(string: String) {
                view.onFail(string)
            }

            override fun onFailed(exception: Exception) {
                view.onFail(exception.message.toString())
            }

        })
    }

    override fun insertUserGroup(list: List<UserTeam>) {
        userGroupRepository.addMemberOnTeam(list,
        object :OnDataLoadedCallback<List<UserTeam>>{
            override fun onSuccess(data: List<UserTeam>) {
                view.insertUserGroupSuccess(data)
            }

            override fun onSuccess() {

            }

            override fun onFailedConnect(string: String) {
                view.onFail(string)
            }

            override fun onFailed(exception: Exception) {
                view.onFail(exception.message.toString())
            }

        })
    }

}