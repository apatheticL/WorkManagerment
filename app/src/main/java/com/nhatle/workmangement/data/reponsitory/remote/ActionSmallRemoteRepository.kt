package com.nhatle.workmangement.data.reponsitory.remote

import com.nhatle.workmangement.data.OnDataLoadedCallback
import com.nhatle.workmangement.data.model.ActionSmall
import com.nhatle.workmangement.data.source.ActionSmallDataSource
import com.nhatle.workmangement.data.source.remote.ActionSmallRemoteDataSource

class ActionSmallRemoteRepository(private val dataSource: ActionSmallRemoteDataSource) :
    ActionSmallDataSource.Remote {
    override fun getAllActionSmall(
        actionId: Int,
        callback: OnDataLoadedCallback<List<ActionSmall>>
    ) {
        dataSource.getAllActionSmall(actionId = actionId, callback = callback)
    }

    override fun insertActionSmall(
        list: List<ActionSmall>,
        callback: OnDataLoadedCallback<Boolean>
    ) {
        dataSource.insertActionSmall(list, callback = callback)
    }

    override fun updateActionSmall(
        actionSmall: ActionSmall,
        callback: OnDataLoadedCallback<Boolean>
    ) {
        dataSource.updateActionSmall(actionSmall,callback)
    }

    override fun deleteActionSmall(actionSmallId: Int, callback: OnDataLoadedCallback<Boolean>) {
        dataSource.deleteActionSmall(actionSmallId = actionSmallId, callback = callback)
    }

    override fun getAllActionSmallOnActionOfUserById(
        actionId: Int,
        profileId: Int,
        callback: OnDataLoadedCallback<List<ActionSmall>>
    ) {
        dataSource.getAllActionSmallOnActionOfUserById(actionId, profileId, callback)
    }


}