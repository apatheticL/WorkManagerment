package com.nhatle.workmangement.ui.main.user_action_small.user

import com.nhatle.workmangement.data.OnDataLoadedCallback
import com.nhatle.workmangement.data.model.response.UserActionSmallResponse
import com.nhatle.workmangement.data.reponsitory.remote.UserActionSmallRepository
import java.lang.Exception

class MyActionSmallPresenter(private val view: MyActionSmallContrac.View,
                             private val repository: UserActionSmallRepository
): MyActionSmallContrac.Presenter {
    override fun getAllActionSmallOfUser(actionId: Int, profileId: Int) {
        repository.getAllActionSmallByUser(actionId = actionId,profileId = profileId,
            callback = object : OnDataLoadedCallback<List<UserActionSmallResponse>> {
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