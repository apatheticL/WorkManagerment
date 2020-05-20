package com.nhatle.workmangement.data.source

import com.nhatle.workmangement.data.OnDataLoadedCallback
import com.nhatle.workmangement.data.model.UserActionReport
import com.nhatle.workmangement.data.model.response.UserActionReportResponse

interface UserActionReportDataSource {
    interface  Remote{
        fun getAllReport(actionId:Int,callback: OnDataLoadedCallback<List<UserActionReportResponse>>)
        fun insertReport(userActionReport: UserActionReport,callback: OnDataLoadedCallback<UserActionReport>)
        fun updateReport(userActionReport: UserActionReport,callback: OnDataLoadedCallback<UserActionReport>)
        fun deleteReport(reportId:Int,callback: OnDataLoadedCallback<Boolean>)
    }

}