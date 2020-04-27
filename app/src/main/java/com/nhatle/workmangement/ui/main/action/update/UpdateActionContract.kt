package com.nhatle.workmangement.ui.main.action.update

import com.nhatle.workmangement.data.model.Action

interface UpdateActionContract {
    interface View{
        fun updateSuccess()
        fun onFail(string: String)
    }
    interface Presenter{
        fun updateAction(action:Action)
    }
}