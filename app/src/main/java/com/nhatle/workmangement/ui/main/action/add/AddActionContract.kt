package com.nhatle.workmangement.ui.main.action.add

import com.nhatle.workmangement.data.model.Action
import com.nhatle.workmangement.data.model.ActionSmall
import com.nhatle.workmangement.data.model.UserActionSmall

interface AddActionContract {
    interface View{
        fun insertActionSuccess(action:Action)
        fun insetFail(error:String)
        fun insertActionSmallSuccess(actionSmall: ActionSmall)

    }
    interface Presenter{
        fun insertAction(action:Action)
        fun insertActionSmall(actionSmall: ActionSmall)
    }
}