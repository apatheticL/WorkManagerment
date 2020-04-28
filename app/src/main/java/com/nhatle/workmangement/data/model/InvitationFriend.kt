package com.nhatle.workmangement.data.model

data class InvitationFriend(
    var friendId:Int,
    var senderId:Int,
    var receiverId:Int,
    var isAccept:Int,
    var createdTime: String?
)