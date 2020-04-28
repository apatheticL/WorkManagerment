package com.nhatle.workmangement.data.model.response

data class FriendResponse(
    var id: Int, var friendId: Int
    ,var friendName: String
    , var friendAvatar: String
    , var friendUsername: String,
    var phoneNumber: String
)