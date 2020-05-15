package com.nhatle.workmangement.until

import com.nhatle.workmangement.data.model.Action
import com.nhatle.workmangement.data.model.response.ActionResponse

class CommonAction {
    var action: ActionResponse? = null
    companion object {
        private  var  instance :CommonAction?=null
        @JvmStatic
        fun getInstance ():CommonAction{
            if (instance==null){
                instance = CommonAction()
            }
            return instance as CommonAction
        }
    }
}