package com.nhatle.workmangement.ui.main.action

import com.nhatle.workmangement.data.OnDataLoadedCallback
import com.nhatle.workmangement.data.model.response.ActionResponse
import com.nhatle.workmangement.data.model.response.UserTeamResponse
import com.nhatle.workmangement.data.reponsitory.remote.ActionRemoteRepository
import com.nhatle.workmangement.data.reponsitory.remote.TeamRemoteRepository
import com.nhatle.workmangement.data.reponsitory.remote.UserProfileRemoteRepository
import com.nhatle.workmangement.data.reponsitory.remote.UserTeamRepository
import java.lang.Exception

class ActionPresenter(var actionView:ActionContract.View,
                      var repository: ActionRemoteRepository,
                      val teamRepository: TeamRemoteRepository
):ActionContract.Presenter {
    override fun getAllActionIsMember(profileId: Int) {
        repository.getAllActionUserMember(profileId = profileId,
            callback = object : OnDataLoadedCallback<List<ActionResponse>>{

                override fun onSuccess() {

                }

                override fun onFailedConnect(string: String) {
                    actionView.loadFailed(string)
                }

                override fun onFailed(exception: Exception) {
                    actionView.loadFailed(exception.message!!)
                }

                override fun onSuccess(data: List<ActionResponse>) {
                    actionView.loadAllActionByUserMember(data as ArrayList)
                }

            })
    }

    override fun deleteAction(actionId: Int, profileId: Int) {
        repository.deleteAction(actionId,profileId,object :OnDataLoadedCallback<Boolean>{
            override fun onSuccess(data: Boolean) {

            }

            override fun onSuccess() {
                actionView.deleteSuccess()
            }

            override fun onFailedConnect(string: String) {
                actionView.loadFailed(string)
            }

            override fun onFailed(exception: Exception) {
                actionView.loadFailed(exception.message!!)
            }

        })
    }

    override fun deleteGroup(groupId: Int) {
        teamRepository.deleteTeam(groupId,object :OnDataLoadedCallback<Boolean>{
            override fun onSuccess(data: Boolean) {

            }

            override fun onSuccess() {
                actionView.deleteSuccess()
            }

            override fun onFailedConnect(string: String) {
               actionView.loadFailed(string)
            }

            override fun onFailed(exception: Exception) {
                actionView.loadFailed(exception.message.toString())
            }

        })
    }

}