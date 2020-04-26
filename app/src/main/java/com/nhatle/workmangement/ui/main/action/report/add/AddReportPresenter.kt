package com.nhatle.workmangement.ui.main.action.report.add

import com.nhatle.workmangement.data.OnDataLoadedCallback
import com.nhatle.workmangement.data.model.UserActionReport
import com.nhatle.workmangement.data.reponsitory.remote.UserActionReportRepository
import java.lang.Exception

class AddReportPresenter(
    val view: AddActionReportContract.View,
    val repository: UserActionReportRepository
) :AddActionReportContract.Present{
    override fun insertReport(userActionReportResponse: UserActionReport) {
        repository.updateReport(userActionReport = userActionReportResponse,
        callback = object :OnDataLoadedCallback<UserActionReport>{
            override fun onSuccess(data: UserActionReport) {

            }

            override fun onSuccess() {
                view.interSuccess()
            }

            override fun onFailedConnect(string: String) {
                view.insertFail(string)
            }

            override fun onFailed(exception: Exception) {
                exception.message?.let { view.insertFail(it) }
            }

        })
    }
}