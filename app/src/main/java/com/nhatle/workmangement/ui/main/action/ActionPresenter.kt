package com.nhatle.workmangement.ui.main.action

import com.nhatle.workmangement.data.OnDataLoadedCallback
import com.nhatle.workmangement.data.model.response.ActionResponse
import com.nhatle.workmangement.data.model.response.UserTeamResponse
import com.nhatle.workmangement.data.reponsitory.remote.ActionRemoteRepository
import com.nhatle.workmangement.data.reponsitory.remote.UserProfileRemoteRepository
import java.lang.Exception

class ActionPresenter(var actionView:ActionContract.View,
                      var repository: ActionRemoteRepository):ActionContract.Presenter {
    override fun getAllActionIsMember(profileId: Int) {
        repository.getAllActionUserMember(profileId = profileId,
            callback = object : OnDataLoadedCallback<ArrayList<ActionResponse>>{

                override fun onSuccess() {

                }

                override fun onFailedConnect(string: String) {
                    actionView.loadFailed(string)
                }

                override fun onFailed(exception: Exception) {
                    actionView.loadFailed(exception.message!!)
                }

                override fun onSuccess(data: ArrayList<ActionResponse>) {
                    actionView.loadAllActionByUserMember(data as ArrayList)
                }

            })
    }

}