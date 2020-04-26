package com.nhatle.workmangement.data.source

import com.nhatle.workmangement.data.OnDataLoadedCallback
import com.nhatle.workmangement.data.model.UserActionSmall
import com.nhatle.workmangement.data.model.response.UserActionSmallResponse
import com.nhatle.workmangement.data.model.response.UserTeamResponse

interface UserActionSmallDataSource {
    interface Remote{
        fun getALlUserActionSmall(actionId:Int,callback: OnDataLoadedCallback<List<UserActionSmallResponse>>)
        fun getAllActionSmallByUser(actionId: Int,profileId: Int,
                                    callback: OnDataLoadedCallback<List<UserActionSmallResponse>>)
        fun addUserActionSmall(userActionSmall: UserActionSmall,callback: OnDataLoadedCallback<UserActionSmall>)
        fun deleteUserActionSmall(groupId:Int,profileId:Int,actionSmallId:Int,callback: OnDataLoadedCallback<Boolean>)
        fun updateUserActionSmall(userActionSmall: UserActionSmall,callback: OnDataLoadedCallback<UserActionSmall>)
        fun getAllMemberOnActionInGroup(groupId: Int,actionId: Int,callback: OnDataLoadedCallback<List<UserTeamResponse>>)
    }
    interface Local{
        fun getALlUserActionSmall(actionId:Int):List<UserActionSmallResponse>
        fun addUserActionSmall(userActionSmall: UserActionSmall)
        fun deleteUserActionSmall(groupId:Int,profileId:Int,actionSmallId:Int)
        fun updateUserActionSmall(userActionSmall: UserActionSmall)
    }
}