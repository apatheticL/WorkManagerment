package com.nhatle.workmangement.ui.main.action.add

import com.nhatle.workmangement.data.OnDataLoadedCallback
import com.nhatle.workmangement.data.model.Action
import com.nhatle.workmangement.data.model.ActionSmall
import com.nhatle.workmangement.data.model.UserActionSmall
import com.nhatle.workmangement.data.reponsitory.remote.ActionRemoteRepository
import com.nhatle.workmangement.data.reponsitory.remote.ActionSmallRemoteRepository
import com.nhatle.workmangement.data.reponsitory.remote.TeamRemoteRepository
import com.nhatle.workmangement.data.reponsitory.remote.UserActionSmallRepository
import java.lang.Exception

class AddActionPresenter(
    val view: AddActionContract.View,
    val actionRepository: ActionRemoteRepository,
    val actionSmallRepository: ActionSmallRemoteRepository,
    val teamRemoteRepository: TeamRemoteRepository
):AddActionContract.Presenter {
    override fun insertAction(action: Action) {
        actionRepository.insertAction(action,object :OnDataLoadedCallback<Action>{
            override fun onSuccess(data: Action) {
                view.insertActionSuccess(action=data)
            }

            override fun onSuccess() {
            }

            override fun onFailedConnect(string: String) {
                view.insetFail(string)
            }

            override fun onFailed(exception: Exception) {
                view.insetFail(exception.message.toString())
            }

        })
    }

    override fun insertActionSmall(list: List<ActionSmall>) {
        actionSmallRepository.insertActionSmall(list,
        object :OnDataLoadedCallback<Boolean>{
            override fun onSuccess(data: Boolean) {

            }

            override fun onSuccess() {
                view.insertActionSmallSuccess()
            }

            override fun onFailedConnect(string: String) {
                view.insetFail(string)
            }

            override fun onFailed(exception: Exception) {
                view.insetFail(exception.message.toString())
            }

        })
    }

    override fun deleteGroup(groupId: Int) {
        teamRemoteRepository.deleteTeam(groupId,object :OnDataLoadedCallback<Boolean>{
            override fun onSuccess(data: Boolean) {

            }

            override fun onSuccess() {
                view.delete()
            }

            override fun onFailedConnect(string: String) {
                view.deleteFailed(string)
            }

            override fun onFailed(exception: Exception) {
                view.deleteFailed(exception.message.toString())
            }

        })
    }


}