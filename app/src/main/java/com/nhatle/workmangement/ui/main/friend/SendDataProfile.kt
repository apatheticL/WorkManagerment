package com.nhatle.workmangement.ui.main.friend

import com.nhatle.workmangement.data.model.UserProfile
import com.nhatle.workmangement.data.model.response.FriendResponse

interface SendDataProfile {
    interface Friend{
        fun sendData(data:Int)
    }
    interface ReceiverFriend{
        fun sendData(id:Int)
        fun sendDataToAccept(position:Int,data: FriendResponse)
        fun sendDataToDeleteInvitation(position:Int,data: FriendResponse)
    }
    interface FriendAdd{
        fun sendDataToAdd(data: UserProfile)
        fun sendDataToDeleteAdd(position:Int,data: UserProfile)
        fun deleteItem(position: Int)
        fun showNotification(string: String)
        fun sendData(id:Int)
    }
}