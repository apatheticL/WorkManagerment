package com.nhatle.workmangement.ui.main.action.report.update

import com.nhatle.workmangement.data.OnDataLoadedCallback
import com.nhatle.workmangement.data.model.UserActionReport
import com.nhatle.workmangement.data.model.response.UserActionReportResponse
import com.nhatle.workmangement.data.reponsitory.remote.UserActionReportRepository
import java.lang.Exception

class UpdateReportPresenter(
    val view: UpdateActionReportContract.View,
    val repository: UserActionReportRepository
) :UpdateActionReportContract.Present{
    override fun updateReport(userActionReportResponse: UserActionReport) {
        repository.updateReport(userActionReport = userActionReportResponse,
        callback = object :OnDataLoadedCallback<UserActionReport>{
            override fun onSuccess(data: UserActionReport) {

            }

            override fun onSuccess() {
                view.updateSuccess()
            }

            override fun onFailedConnect(string: String) {
                view.updateFail(string)
            }

            override fun onFailed(exception: Exception) {
                exception.message?.let { view.updateFail(it) }
            }

        })
    }
}