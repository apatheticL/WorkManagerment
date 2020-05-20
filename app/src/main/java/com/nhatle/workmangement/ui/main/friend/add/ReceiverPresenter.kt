package com.nhatle.workmangement.ui.main.friend.add

import com.nhatle.workmangement.data.OnDataLoadedCallback
import com.nhatle.workmangement.data.model.InvitationFriend
import com.nhatle.workmangement.data.model.UserProfile
import com.nhatle.workmangement.data.reponsitory.remote.FriendRepository
import java.lang.Exception

class ReceiverPresenter(
    val view: ReceiverContract.View,
    val repository: FriendRepository
) :ReceiverContract.Presenter{
    override fun getAllUserNotFriend(profileId: Int) {
        repository.getAllUserNotFriend(profileId,object :OnDataLoadedCallback<List<UserProfile>>{
            override fun onSuccess(data: List<UserProfile>) {
                view.showAllUserNotFriend(data)
            }

            override fun onSuccess() {

            }

            override fun onFailedConnect(string: String) {
                view.onFailLoad(string)
            }

            override fun onFailed(exception: Exception) {
              view.onFailLoad(exception.message.toString())
            }

        })
    }

    override fun sendInvitationFriend(invitationFriend: InvitationFriend) {
        repository.senderFriend(invitationFriend,object :OnDataLoadedCallback<InvitationFriend>{
            override fun onSuccess(data: InvitationFriend) {
                view.sendInvitationSuccess(data.receiverId)
            }

            override fun onSuccess() {

            }

            override fun onFailedConnect(string: String) {
                view.onFailLoad(string)
            }

            override fun onFailed(exception: Exception) {
                view.onFailLoad(exception.message.toString())
            }

        })
    }

    override fun deleteReceiver(senderId: Int, receiverId: Int) {
        repository.cancelInvitationFriend(senderId,receiverId
            ,object :OnDataLoadedCallback<Boolean>{
            override fun onFailed(exception: Exception) {
                view.onFailLoad(exception.message.toString())
            }

            override fun onFailedConnect(string: String) {
                view.onFailLoad(string)
            }

            override fun onSuccess() {
                view.sendSuccess()
            }

            override fun onSuccess(data: Boolean) {

            }
        })
    }
}