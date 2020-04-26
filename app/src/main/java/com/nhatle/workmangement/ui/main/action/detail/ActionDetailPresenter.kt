package com.nhatle.workmangement.ui.main.action.detail

import android.view.CollapsibleActionView
import com.nhatle.workmangement.data.OnDataLoadedCallback
import com.nhatle.workmangement.data.model.response.UserTeamResponse
import com.nhatle.workmangement.data.reponsitory.remote.ActionRemoteRepository
import com.nhatle.workmangement.data.reponsitory.remote.UserActionSmallRepository
import java.lang.Exception

class ActionDetailPresenter(private val actionView: ActionDetailContract.View,
                            private val repository: ActionRemoteRepository
):ActionDetailContract.Presenter {
    override fun getAllMemberOnGroup(groupId: Int, actionId: Int) {
        repository.getAllMemberOnAction(actionId = actionId,idTeam = groupId,
            callback = object: OnDataLoadedCallback<List<UserTeamResponse>> {
                override fun onSuccess(data: List<UserTeamResponse>) {
                    actionView.showAllMember(data as ArrayList<UserTeamResponse>)

                }

                override fun onSuccess() {

                }

                override fun onFailedConnect(string: String) {
                    actionView.onFailed(string)
                }

                override fun onFailed(exception: Exception) {
                    exception.message?.let { actionView.onFailed(it) }
                }

            } )
    }
}