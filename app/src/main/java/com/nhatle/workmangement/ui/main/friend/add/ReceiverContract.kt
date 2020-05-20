package com.nhatle.workmangement.ui.main.friend.add

import android.service.restrictions.RestrictionsReceiver
import com.nhatle.workmangement.data.model.InvitationFriend
import com.nhatle.workmangement.data.model.UserProfile

interface ReceiverContract {
    interface View {
        fun showAllUserNotFriend(listUser :List<UserProfile>)
        fun onFailLoad(string: String)
        fun sendInvitationSuccess(profileId: Int)
        fun sendSuccess()
    }
    interface Presenter{
        fun getAllUserNotFriend(profileId:Int)
        fun sendInvitationFriend(invitationFriend: InvitationFriend)
        fun  deleteReceiver(senderId: Int,receiverId:Int)
    }
}