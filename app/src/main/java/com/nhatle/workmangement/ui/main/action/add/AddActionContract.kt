package com.nhatle.workmangement.ui.main.action.add

import com.nhatle.workmangement.data.model.Action
import com.nhatle.workmangement.data.model.ActionSmall
import com.nhatle.workmangement.data.model.UserActionSmall

interface AddActionContract {
    interface View{
        fun insertActionSuccess(action:Action)
        fun insetFail(error:String)
        fun insertActionSmallSuccess()
        fun deleteFailed(string: String)
        fun delete()

    }
    interface Presenter{
        fun insertAction(action:Action)
        fun insertActionSmall(list: List<ActionSmall>)
        fun deleteGroup(groupId:Int)
    }
}