package com.nhatle.workmangement.data.model

data class UserProfile(
    var profileId:Int,
    var username:String, var avatar: String?, var fullName:String,
    var address:String,
    var phoneNumber:String,
    var email:String,
    var createdTime: String?
)