package com.nhatle.workmangement.ui.main.action.add

import com.nhatle.workmangement.data.OnDataLoadedCallback
import com.nhatle.workmangement.data.model.Action
import com.nhatle.workmangement.data.model.ActionSmall
import com.nhatle.workmangement.data.model.UserActionSmall
import com.nhatle.workmangement.data.reponsitory.remote.ActionRemoteRepository
import com.nhatle.workmangement.data.reponsitory.remote.ActionSmallRemoteRepository
import com.nhatle.workmangement.data.reponsitory.remote.UserActionSmallRepository
import java.lang.Exception

class AddActionPresenter(
    val view: AddActionContract.View,
    val actionRepository: ActionRemoteRepository,
    val actionSmallRepository: ActionSmallRemoteRepository
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

    override fun insertActionSmall(actionSmall: ActionSmall) {
        actionSmallRepository.insertActionSmall(actionSmall,
        object :OnDataLoadedCallback<ActionSmall>{
            override fun onSuccess(data: ActionSmall) {
                view.insertActionSmallSuccess(data)
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



}