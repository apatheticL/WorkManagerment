package com.nhatle.workmangement.data.model.response

class BaseResponse<T>(var status:Int,var message:String,var data:T)