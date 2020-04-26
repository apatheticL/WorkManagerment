package com.nhatle.workmangement.ui.main.action.report.add

import com.nhatle.workmangement.data.model.UserActionReport
import com.nhatle.workmangement.data.model.response.UserActionReportResponse

interface AddActionReportContract {
    interface View{
        fun interSuccess()
        fun insertFail(error:String)
    }
    interface Present{
        fun insertReport(userActionReportResponse: UserActionReport)
    }
}