package com.nhatle.workmangement.ui.main.action.report.update

import com.nhatle.workmangement.data.model.UserActionReport
import com.nhatle.workmangement.data.model.response.UserActionReportResponse

interface UpdateActionReportContract {
    interface View{
        fun updateSuccess()
        fun updateFail(error:String)
    }
    interface Present{
        fun updateReport(userActionReportResponse: UserActionReport)
    }
}