package com.nhatle.workmangement.ui.main.action.report

import com.nhatle.workmangement.data.model.response.UserActionReportResponse

interface UserActionReportContract {
    interface View{
        fun loadAllActionReport(listAction:List<UserActionReportResponse>)
        fun reloadData(boolean: Boolean)
        fun loadFailed(error:String)
    }
    interface Presenter{
        fun getAllUserActionRemoteByAction(actionId: Int)
        fun deleteUserActionReport(reportId:Int)
    }
}