package com.nhatle.workmangement.data.model.response

data class UserActionReportResponse(
    var reportId: Int,
    var userActionSmallId: Int,
    val profileId:Int,
    var actionId: Int,
    var avatar: String,
    var fullName: String,
    var actionActual: String,
    var actionNext: String,
    var actionIssua: String,
    var timeReport: String
)