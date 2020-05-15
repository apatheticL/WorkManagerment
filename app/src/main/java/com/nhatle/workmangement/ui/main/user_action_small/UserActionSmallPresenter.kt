package com.nhatle.workmangement.ui.main.user_action_small

import com.nhatle.workmangement.data.OnDataLoadedCallback
import com.nhatle.workmangement.data.model.response.UserActionSmallResponse
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

    override fun deleteUserActionSmall(userActionSmallId: Int) {
        repository.deleteUserActionSmall(userActionSmallId,object :OnDataLoadedCallback<Boolean>{
            override fun onSuccess(data: Boolean) {

            }

            override fun onSuccess() {
                view.deleteSuccess()
            }

            override fun onFailedConnect(string: String) {
                view.deleteFail(string)
            }

            override fun onFailed(exception: Exception) {
                view.deleteFail(exception.message.toString())
            }

        })
    }


}