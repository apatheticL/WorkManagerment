package com.nhatle.workmangement.data.source

import com.nhatle.workmangement.data.OnDataLoadedCallback
import com.nhatle.workmangement.data.model.InvitationFriend
import com.nhatle.workmangement.data.model.UserProfile
import com.nhatle.workmangement.data.model.response.FriendResponse

interface FriendDataSource {
    fun getAllFriendByUser(profileId:Int,callback: OnDataLoadedCallback<List<FriendResponse>>)
    fun getAllUserNotFriend(profileId: Int,callback: OnDataLoadedCallback<List<UserProfile>>)
    fun acceptedFriend(invitationFriend: InvitationFriend,callback: OnDataLoadedCallback<Boolean>)
    fun senderFriend(invitationFriend: InvitationFriend,callback: OnDataLoadedCallback<InvitationFriend>)
    fun getAllUserSenderFriend(profileId: Int,callback: OnDataLoadedCallback<List<FriendResponse>>)
    fun deleteInvitationFriend(friendId: Int,callback: OnDataLoadedCallback<Boolean>)
}