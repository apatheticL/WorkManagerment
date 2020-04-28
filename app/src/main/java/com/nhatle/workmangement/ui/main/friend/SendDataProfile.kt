package com.nhatle.workmangement.ui.main.friend

import com.nhatle.workmangement.data.model.response.FriendResponse

interface SendDataProfile {
    interface Friend{
        fun sendData(data:FriendResponse)
    }
    interface ReceiverFriend{
        fun sendDataToAccept(position:Int,data: FriendResponse)
        fun sendDataToDeleteInvitation(position:Int,data: FriendResponse)
    }
    interface FriendAdd{
        fun sendDataToAdd(position:Int,data: FriendResponse)
        fun sendDataToDeleteAdd(position:Int,data: FriendResponse)
    }
}