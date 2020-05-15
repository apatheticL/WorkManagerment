package com.nhatle.workmangement.ui.main.user_action_small.dialog

import com.nhatle.workmangement.data.model.ActionSmall

interface DialogActionSmallContract {
    interface View{
        fun loadFailed(error:String)
        fun getAllActionSmall(data: List<ActionSmall>)

    }
    interface Presenter{
        fun getAllActionSmallByAction(actionId: Int)
    }
}