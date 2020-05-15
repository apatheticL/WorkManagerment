package com.nhatle.workmangement.ui.main.action.add.actionSmall

import com.nhatle.workmangement.data.OnDataLoadedCallback
import com.nhatle.workmangement.data.model.ActionSmall
import com.nhatle.workmangement.data.model.UserActionSmall
import com.nhatle.workmangement.data.model.response.UserTeamResponse
import com.nhatle.workmangement.data.reponsitory.remote.ActionRemoteRepository
import com.nhatle.workmangement.data.reponsitory.remote.ActionSmallRemoteRepository
import com.nhatle.workmangement.data.reponsitory.remote.UserActionSmallRepository
import retrofit2.Callback
import java.lang.Exception

class AddUserActionSmallPresenter(
    val view: AddUserActionSmallContract.View,
    val repository: UserActionSmallRepository,
    private val actionSmallRepository: ActionSmallRemoteRepository

) : AddUserActionSmallContract.Presenter {
    override fun getAllActionSmall(actionId: Int) {
        actionSmallRepository.getAllActionSmall(actionId,
            object : OnDataLoadedCallback<List<ActionSmall>> {
                override fun onSuccess(data: List<ActionSmall>) {
                    view.showAllActionSmall(data)
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

    override fun insertUserActionSmall(userActionSmall: UserActionSmall) {
        repository.addUserActionSmall(userActionSmall,
            object : OnDataLoadedCallback<UserActionSmall> {
                override fun onSuccess(data: UserActionSmall) {

                }

                override fun onSuccess() {
                    view.insertUserActionSmallSuccess()
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