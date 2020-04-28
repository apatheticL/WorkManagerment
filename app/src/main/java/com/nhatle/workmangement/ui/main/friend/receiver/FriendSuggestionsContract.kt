package com.nhatle.workmangement.ui.main.friend.receiver

import com.nhatle.workmangement.data.model.InvitationFriend
import com.nhatle.workmangement.data.model.response.FriendResponse

interface FriendSuggestionsContract {
    interface View{
        fun showListReceiver(listFriend:List<FriendResponse>)
        fun acceptSuccess()
        fun deleteSuccess()
        fun onFail(string: String)
    }
    interface Presenter{
        fun getAllReceiver(profileId:Int)
        fun sendAcceptInvitationFriend(invitationFriend: InvitationFriend)
        fun deleteReceiver(friendId: Int)
    }
}