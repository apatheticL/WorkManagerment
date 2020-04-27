package com.nhatle.workmangement.data.model.response

data class ActionResponse (
    var actionId:Int,
    var actionName:String,
    var creatorId:Int,
    var groupId:Int,
    var groupName:String,
    var nameCreator:String,
    var timeStart:String,
    var timeEnd:String,
    var description:String,
    var actionStatus:String,
    var createdTime:String,
    var numberDelay:Int,
    var numberActionMaked:Int,
    var numberFinish:Int
    )