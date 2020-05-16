package com.nhatle.workmangement.data.model.response

data class UserTeamResponse(
    var profileId: Int,
    var groupId: Int,
    var avatar: String,
    var fullName: String,
    var address: String?,
    var phoneNumber: String,
    var email: String
)