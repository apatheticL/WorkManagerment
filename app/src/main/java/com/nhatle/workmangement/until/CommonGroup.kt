package com.nhatle.workmangement.until

import com.nhatle.workmangement.data.model.Team

class CommonGroup {
    var action: Team? = null
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