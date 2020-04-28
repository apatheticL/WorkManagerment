package com.nhatle.workmangement.data.model.response

data class CommentResponse(
    var commentId: Int,
    var profileId: Int,
    var groupId: Int,
    var fullName: String,
    var avatar: String,
    var actionId: Int,
    var content: String,
    var typeContent: Int,
    var createdTime: String?
)