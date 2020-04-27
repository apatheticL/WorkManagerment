package com.nhatle.workmangement.ui.main.action.update

import com.nhatle.workmangement.data.OnDataLoadedCallback
import com.nhatle.workmangement.data.model.Action
import com.nhatle.workmangement.data.reponsitory.remote.ActionRemoteRepository
import java.lang.Exception

class UpdateActionPresenter(
    val view: UpdateActionContract.View,
    val repository: ActionRemoteRepository
) :UpdateActionContract.Presenter{
    override fun updateAction(action: Action) {
        repository.updateAction(action,object :OnDataLoadedCallback<Action>{
            override fun onSuccess(data: Action) {
                view.updateSuccess()
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