package com.nhatle.workmangement.data.model

data class Comment (var commentId:Int,val profileId:Int,var actionId:Int,var groupId:Int,var content:String,var createdTime:String)