package com.nhatle.workmangement.data.reponsitory.remote

import com.nhatle.workmangement.data.OnDataLoadedCallback
import com.nhatle.workmangement.data.model.UserActionReport
import com.nhatle.workmangement.data.model.response.UserActionReportResponse
import com.nhatle.workmangement.data.source.UserActionReportDataSource
import com.nhatle.workmangement.data.source.remote.UserActionReportRemoteDataSource

class UserActionReportRepository(val dataSource: UserActionReportRemoteDataSource) :
    UserActionReportDataSource.Remote {
    override fun getAllReport(
        actionId: Int,
        callback: OnDataLoadedCallback<List<UserActionReportResponse>>
    ) {
        dataSource.getAllReport(actionId = actionId, callback = callback)
    }

    override fun insertReport(
        userActionReport: UserActionReport,
        callback: OnDataLoadedCallback<UserActionReport>
    ) {
        dataSource.insertReport(userActionReport = userActionReport, callback = callback)
    }

    override fun updateReport(
        userActionReport: UserActionReport,
        callback: OnDataLoadedCallback<UserActionReport>
    ) {
        dataSource.updateReport(userActionReport = userActionReport, callback = callback)
    }

    override fun deleteReport(reportId: Int, callback: OnDataLoadedCallback<Boolean>) {
        dataSource.deleteReport(reportId = reportId, callback = callback)
    }
}