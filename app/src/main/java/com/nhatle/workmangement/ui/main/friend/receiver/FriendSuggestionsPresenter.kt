package com.nhatle.workmangement.ui.main.friend.receiver

import com.nhatle.workmangement.data.OnDataLoadedCallback
import com.nhatle.workmangement.data.model.InvitationFriend
import com.nhatle.workmangement.data.model.response.FriendResponse
import com.nhatle.workmangement.data.reponsitory.remote.FriendRepository
import java.lang.Exception

class FriendSuggestionsPresenter(val view:FriendSuggestionsContract.View,
                                 val repository: FriendRepository):FriendSuggestionsContract.Presenter {
    override fun getAllReceiver(profileId: Int) {
        repository.getAllUserSenderFriend(profileId,object :OnDataLoadedCallback<List<FriendResponse>>{
            override fun onSuccess(data: List<FriendResponse>) {
                view.showListReceiver(data)
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

    override fun sendAcceptInvitationFriend(invitationFriend: InvitationFriend) {
        repository.acceptedFriend(invitationFriend,object :OnDataLoadedCallback<Boolean>{
            override fun onSuccess(data: Boolean) {

            }

            override fun onSuccess() {
                view.acceptSuccess()
            }

            override fun onFailedConnect(string: String) {
                view.onFail(string)
            }

            override fun onFailed(exception: Exception) {
                view.onFail(exception.message.toString())
            }
        })
    }

    override fun deleteReceiver(friendId: Int) {
        repository.deleteInvitationFriend(friendId,object :OnDataLoadedCallback<Boolean>{
            override fun onFailed(exception: Exception) {
                view.onFail(exception.message.toString())
            }

            override fun onFailedConnect(string: String) {
                view.onFail(string)
            }

            override fun onSuccess() {
                view.deleteSuccess()
            }

            override fun onSuccess(data: Boolean) {

            }
        })
    }

}