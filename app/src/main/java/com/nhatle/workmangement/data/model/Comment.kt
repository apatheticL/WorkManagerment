package com.nhatle.workmangement.data.model

data class Comment(
    var commentId: Int,
    var profileId: Int,
    var actionId: Int,
    var groupId: Int,
    var content: String,
    var type:Int,
    var createdTime: String?
)