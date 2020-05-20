package com.nhatle.workmangement.until

import com.nhatle.workmangement.data.model.UserProfile


class CommonData private constructor() {

    var profile: UserProfile? = null
    companion object {
        private  var  instance :CommonData?=null
        @JvmStatic
        fun getInstance ():CommonData{
            if (instance==null){
                instance = CommonData()
            }
            return instance as CommonData
        }

    }
}