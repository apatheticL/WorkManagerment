package com.nhatle.workmangement.data.model

data class Action(
    var actionId: Int, var actionName: String, var creatorId: Int,
    var groupId: Int, var timeStart: String?, var timeEnd: String,
    var createdTime: String?, var actionStatus: String?
    , var description: String?
)