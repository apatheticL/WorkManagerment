package com.nhatle.workmangement.ui.main.report.update

import com.nhatle.workmangement.data.model.UserActionReport

interface UpdateActionReportContract {
    interface View{
        fun updateSuccess()
        fun updateFail(error:String)
    }
    interface Present{
        fun updateReport(userActionReportResponse: UserActionReport)
    }
}