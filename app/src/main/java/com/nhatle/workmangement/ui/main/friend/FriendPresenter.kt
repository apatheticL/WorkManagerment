package com.nhatle.workmangement.ui.main.friend

import com.nhatle.workmangement.data.OnDataLoadedCallback
import com.nhatle.workmangement.data.model.response.FriendResponse
import com.nhatle.workmangement.data.reponsitory.remote.FriendRepository
import java.lang.Exception

class FriendPresenter(
    val view: FriendContract.View,
    val repository: FriendRepository
) :FriendContract.Presenter{
    override fun getAllFriend(profileId: Int) {
        repository.getAllFriendByUser(profileId,object :OnDataLoadedCallback<List<FriendResponse>>{
            override fun onSuccess(data: List<FriendResponse>) {
                view.showAllFriendOfUser(data)
            }

            override fun onSuccess() {

            }

            override fun onFailedConnect(string: String) {
                view.loadFail(string)
            }

            override fun onFailed(exception: Exception) {
                view.loadFail(exception.message.toString())
            }
        })
    }

}