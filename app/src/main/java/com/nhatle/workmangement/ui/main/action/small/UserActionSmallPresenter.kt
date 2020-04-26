package com.nhatle.workmangement.ui.main.action.small

import com.nhatle.workmangement.data.OnDataLoadedCallback
import com.nhatle.workmangement.data.model.response.UserActionSmallResponse
import com.nhatle.workmangement.data.model.response.UserTeamResponse
import com.nhatle.workmangement.data.reponsitory.remote.ActionRemoteRepository
import com.nhatle.workmangement.data.reponsitory.remote.UserActionSmallRepository
import java.lang.Exception

class UserActionSmallPresenter(private val view:UserActionSmallContract.View,
                               private val repository: UserActionSmallRepository
):UserActionSmallContract.Presenter {
    override fun getAllUserActionSmallByAction(actionId: Int) {
        repository.getALlUserActionSmall(actionId=actionId,
        callback = object :OnDataLoadedCallback<List<UserActionSmallResponse>>{
            override fun onSuccess(data: List<UserActionSmallResponse>) {
                view.loadAllActionSmall(data)
            }

            override fun onSuccess() {

            }

            override fun onFailedConnect(string: String) {
                view.loadFailed(string)
            }

            override fun onFailed(exception: Exception) {
                exception.message?.let { view.loadFailed(it) }
            }

        })
    }

    override fun getAllActionSmallOfUser(actionId: Int, profileId: Int) {
        repository.getAllActionSmallByUser(actionId = actionId,profileId = profileId,
        callback = object :OnDataLoadedCallback<List<UserActionSmallResponse>>{
            override fun onSuccess(data: List<UserActionSmallResponse>) {
                view.loadAllActionSmallByUser(data)
            }

            override fun onSuccess() {

            }

            override fun onFailedConnect(string: String) {
                view.loadMyActionFailed(string)
            }

            override fun onFailed(exception: Exception) {
                exception.message?.let { view.loadMyActionFailed(error = it) }
            }

        })
    }

}