package com.nhatle.workmangement.ui.main.user_action_small.dialog

import com.nhatle.workmangement.data.OnDataLoadedCallback
import com.nhatle.workmangement.data.model.ActionSmall
import com.nhatle.workmangement.data.reponsitory.remote.ActionSmallRemoteRepository
import java.lang.Exception

class DialogActionSmallPresenter(private val view :DialogActionSmallContract.View,
                                private val actionSmallRepository: ActionSmallRemoteRepository
) :DialogActionSmallContract.Presenter{
    override fun getAllActionSmallByAction(actionId: Int) {
        actionSmallRepository.getAllActionSmall(actionId,
            object : OnDataLoadedCallback<List<ActionSmall>> {
                override fun onSuccess(data: List<ActionSmall>) {
                    view.getAllActionSmall(data)
                }

                override fun onSuccess() {

                }

                override fun onFailedConnect(string: String) {
                    view.loadFailed(string)
                }

                override fun onFailed(exception: Exception) {
                    view.loadFailed(exception.message.toString())
                }

            })
    }

}