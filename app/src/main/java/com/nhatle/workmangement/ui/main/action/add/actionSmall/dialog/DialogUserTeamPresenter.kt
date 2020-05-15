package com.nhatle.workmangement.ui.main.action.add.actionSmall.dialog

import com.nhatle.workmangement.data.OnDataLoadedCallback
import com.nhatle.workmangement.data.model.ActionSmall
import com.nhatle.workmangement.data.model.response.UserTeamResponse
import com.nhatle.workmangement.data.reponsitory.remote.ActionRemoteRepository
import com.nhatle.workmangement.data.reponsitory.remote.ActionSmallRemoteRepository
import java.lang.Exception

class DialogUserTeamPresenter(private val view :DialogUserTeamContract.View,
                              private val  actionRepository: ActionRemoteRepository
) :DialogUserTeamContract.Presenter{
    override fun getAllUserTeamAction(actionId: Int, groupId: Int) {
        actionRepository.getAllMemberOnAction(actionId = actionId,idTeam = groupId,
            callback = object: OnDataLoadedCallback<List<UserTeamResponse>> {
                override fun onSuccess(data: List<UserTeamResponse>) {
                    view.getAllUserTeam(data)

                }

                override fun onSuccess() {

                }

                override fun onFailedConnect(string: String) {
                    view.loadFailed(string)
                }

                override fun onFailed(exception: Exception) {
                    exception.message?.let { view.loadFailed(it) }
                }

            } )
    }


}