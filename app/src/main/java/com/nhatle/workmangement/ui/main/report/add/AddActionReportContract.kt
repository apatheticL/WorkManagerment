package com.nhatle.workmangement.ui.main.report.add

import com.nhatle.workmangement.data.model.ActionSmall
import com.nhatle.workmangement.data.model.UserActionReport

interface AddActionReportContract {
    interface View{
        fun interSuccess()
        fun insertFail(error:String)
        fun getAllActionSmall(list: List<ActionSmall>)
        fun loadFail(error: String)
    }
    interface Present{
        fun insertReport(userActionReportResponse: UserActionReport)
        fun getAllActionSmall(actionId:Int,profileId:Int)
    }
}