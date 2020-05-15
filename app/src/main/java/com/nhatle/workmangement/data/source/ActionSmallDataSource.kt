package com.nhatle.workmangement.data.source

import com.nhatle.workmangement.data.OnDataLoadedCallback
import com.nhatle.workmangement.data.model.ActionSmall

interface ActionSmallDataSource {
    interface Remote{
        fun getAllActionSmall(actionId: Int,callback: OnDataLoadedCallback<List<ActionSmall>>)
        fun insertActionSmall(list: List<ActionSmall>,callback: OnDataLoadedCallback<Boolean>)
        fun deleteActionSmall(actionSmallId:Int,callback: OnDataLoadedCallback<Boolean>)
        fun getAllActionSmallOnActionOfUserById(actionId: Int,profileId:Int,callback: OnDataLoadedCallback<List<ActionSmall>>)
    }
}