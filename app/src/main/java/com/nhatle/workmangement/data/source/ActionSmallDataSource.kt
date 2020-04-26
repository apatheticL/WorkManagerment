package com.nhatle.workmangement.data.source

import com.nhatle.workmangement.data.OnDataLoadedCallback
import com.nhatle.workmangement.data.model.ActionSmall

interface ActionSmallDataSource {
    interface Remote{
        fun getAllActionSmall(actionId: Int,callback: OnDataLoadedCallback<List<ActionSmall>>)
        fun insertActionSmall(actionSmall: ActionSmall,callback: OnDataLoadedCallback<ActionSmall>)
        fun deleteActionSmall(actionSmallId:Int,callback: OnDataLoadedCallback<Boolean>)

    }
    interface Local{
        fun getAllActionSmall():List<ActionSmall>
        fun insertActionSmall(actionSmall: ActionSmall)
        fun deleteActionSmall(actionSmallId:Int)

    }
}