package com.nhatle.workmangement.data.reponsitory.remote

import com.nhatle.workmangement.data.OnDataLoadedCallback
import com.nhatle.workmangement.data.model.InvitationFriend
import com.nhatle.workmangement.data.model.UserProfile
import com.nhatle.workmangement.data.model.response.FriendResponse
import com.nhatle.workmangement.data.source.FriendDataSource
import com.nhatle.workmangement.data.source.remote.FriendRemoteDataSource

class FriendRepository(val dataSource: FriendRemoteDataSource):FriendDataSource{
    override fun getAllFriendByUser(
        profileId: Int,
        callback: OnDataLoadedCallback<List<FriendResponse>>
    ) {
        dataSource.getAllFriendByUser(profileId, callback)
    }

    override fun getAllUserNotFriend(
        profileId: Int,
        callback: OnDataLoadedCallback<List<UserProfile>>
    ) {
       dataSource.getAllUserNotFriend(profileId, callback)
    }

    override fun acceptedFriend(
        invitationFriend: InvitationFriend,
        callback: OnDataLoadedCallback<Boolean>
    ) {
        acceptedFriend(invitationFriend, callback)
    }

    override fun senderFriend(
        invitationFriend: InvitationFriend,
        callback: OnDataLoadedCallback<InvitationFriend>
    ) {
        senderFriend(invitationFriend,callback)
    }

    override fun getAllUserSenderFriend(
        profileId: Int,
        callback: OnDataLoadedCallback<List<FriendResponse>>
    ) {
        dataSource.getAllUserSenderFriend(profileId, callback)
    }

    override fun deleteInvitationFriend(friendId: Int, callback: OnDataLoadedCallback<Boolean>) {
        dataSource.deleteInvitationFriend(friendId,callback)
    }

    override fun cancelInvitationFriend(
        senderId: Int,
        receiverId: Int,
        callback: OnDataLoadedCallback<Boolean>
    ) {
        dataSource.cancelInvitationFriend(senderId,receiverId,callback)
    }

}