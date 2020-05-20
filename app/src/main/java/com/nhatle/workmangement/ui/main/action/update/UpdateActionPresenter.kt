package com.nhatle.workmangement.ui.main.action.update

import com.nhatle.workmangement.data.OnDataLoadedCallback
import com.nhatle.workmangement.data.model.Action
import com.nhatle.workmangement.data.model.ActionSmall
import com.nhatle.workmangement.data.reponsitory.remote.ActionRemoteRepository
import com.nhatle.workmangement.data.reponsitory.remote.ActionSmallRemoteRepository
import java.lang.Exception

class UpdateActionPresenter(
    val view: UpdateActionContract.View,
    val repository: ActionRemoteRepository,
    val actionSmallRepository:ActionSmallRemoteRepository
) :UpdateActionContract.Presenter{
    override fun updateAction(action: Action) {
        repository.updateAction(action,object :OnDataLoadedCallback<Action>{
            override fun onSuccess(data: Action) {

            }

            override fun onSuccess() {
                view.updateSuccess()
            }

            override fun onFailedConnect(string: String) {
                view.onFail(string)
            }

            override fun onFailed(exception: Exception) {
                view.onFail(exception.message.toString())
            }
        })
    }

    override fun getAllActionSmall(actionId: Int) {
        actionSmallRepository.getAllActionSmall(actionId,
        object :OnDataLoadedCallback<List<ActionSmall>>{
            override fun onSuccess(data: List<ActionSmall>) {
                view.getAllActionSmall(data)
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

    override fun updateActionSmall(actionSmall: ActionSmall) {
        actionSmallRepository.updateActionSmall(actionSmall,object :OnDataLoadedCallback<Boolean>{
            override fun onSuccess(data: Boolean) {
                if (data) {
                    view.updateActionSmallSuccess()
                }
            }

            override fun onSuccess() {

            }

            override fun onFailedConnect(string: String) {

            }

            override fun onFailed(exception: Exception) {
                view.onFail(exception.message.toString())
            }

        })
    }

    override fun insertActionSmall(list: List<ActionSmall>) {
        actionSmallRepository.insertActionSmall(list,object :OnDataLoadedCallback<Boolean>{
            override fun onSuccess(data: Boolean) {

            }

            override fun onSuccess() {
               view.insertActionSmallSuccess()
            }

            override fun onFailedConnect(string: String) {

            }

            override fun onFailed(exception: Exception) {
                view.onFail(exception.message.toString())
            }

        })
    }

    override fun delateActionSmall(actionId: Int) {
       actionSmallRepository.deleteActionSmall(actionId,object :OnDataLoadedCallback<Boolean>{
           override fun onSuccess(data: Boolean) {

           }

           override fun onSuccess() {
               view.deleteActionSmallSuccess()
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