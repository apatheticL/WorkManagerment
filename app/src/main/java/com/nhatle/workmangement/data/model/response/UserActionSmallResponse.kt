package com.nhatle.workmangement.data.model.response

data class UserActionSmallResponse (
    var userActionSmallId:Int,
    var actionSmallName :String,
    var profileId:Int,
    var part: String,
    var timeStart: String,
    var timeEnd: String,
    var fullName: String,
    var avatar: String
    )