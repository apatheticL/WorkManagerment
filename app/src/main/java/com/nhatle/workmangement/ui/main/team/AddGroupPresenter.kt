package com.nhatle.workmangement.ui.main.team

import com.nhatle.workmangement.data.OnDataLoadedCallback
import com.nhatle.workmangement.data.model.Team
import com.nhatle.workmangement.data.model.UserTeam
import com.nhatle.workmangement.data.model.response.FriendResponse
import com.nhatle.workmangement.data.reponsitory.remote.FriendRepository
import com.nhatle.workmangement.data.reponsitory.remote.TeamRemoteRepository
import com.nhatle.workmangement.data.reponsitory.remote.UserTeamRepository

class AddGroupPresenter(
    val view: AddGroupContract.View,
    val groupRepository: TeamRemoteRepository,
    val userGroupRepository: UserTeamRepository,
    val friendRepository: FriendRepository
) : AddGroupContract.Presenter {
    override fun insertGroup(team: Team) {
        groupRepository.addTeam(team,
            object : OnDataLoadedCallback<Team> {
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
            object : OnDataLoadedCallback<List<UserTeam>> {
                override fun onSuccess(data: List<UserTeam>) {

                }

                override fun onSuccess() {
                    view.insertUserGroupSuccess()
                }

                override fun onFailedConnect(string: String) {
                    view.onFail(string)
                }

                override fun onFailed(exception: Exception) {
                    view.onFail(exception.message.toString())
                }

            })
    }

    override fun getAllUserIsFriend(profileId: Int) {
        friendRepository.getAllFriendByUser(profileId,
        object :OnDataLoadedCallback<List<FriendResponse>>{
            override fun onSuccess(data: List<FriendResponse>) {
                view.showAllData(data)
            }

            override fun onSuccess() {

            }

            override fun onFailedConnect(string: String) {
                view.onFail(string)
            }

            override fun onFailed(exception: java.lang.Exception) {
                view.onFail(exception.message.toString())
            }

        })
    }

}