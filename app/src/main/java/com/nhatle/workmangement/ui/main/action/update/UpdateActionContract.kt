package com.nhatle.workmangement.ui.main.action.update

import com.nhatle.workmangement.data.model.Action
import com.nhatle.workmangement.data.model.ActionSmall

interface UpdateActionContract {
    interface View{
        fun getAllActionSmall(list: List<ActionSmall>)
        fun updateSuccess()
        fun updateActionSmallSuccess()
        fun insertActionSmallSuccess()
        fun deleteActionSmallSuccess()
        fun onFail(string: String)
    }
    interface Presenter{
        fun updateAction(action:Action)
        fun getAllActionSmall(actionId:Int)
        fun updateActionSmall(actionSmall: ActionSmall)
        fun insertActionSmall(list: List<ActionSmall>)
        fun delateActionSmall(actionId: Int)
    }
}