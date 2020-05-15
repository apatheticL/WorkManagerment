package com.nhatle.workmangement.ui.main.report

import com.nhatle.workmangement.data.OnDataLoadedCallback
import com.nhatle.workmangement.data.model.response.UserActionReportResponse
import com.nhatle.workmangement.data.reponsitory.remote.UserActionReportRepository
import java.lang.Exception

class UserActionReportPresenter(private val view: UserActionReportContract.View,
private val repository:UserActionReportRepository) :UserActionReportContract.Presenter{

    override fun getAllUserActionRemoteByAction(actionId: Int) {
        repository.getAllReport(actionId=actionId,
             callback = object : OnDataLoadedCallback<List<UserActionReportResponse>> {
                override fun onSuccess(data: List<UserActionReportResponse>) {
                    view.loadAllActionReport(data)
                }

                override fun onSuccess() {
                }

                override fun onFailedConnect(string: String) {
                    view.loadFailed(string)
                }


                override fun onFailed(exception: Exception) {
                    exception.message?.let { view.loadFailed(it) }
                }
            })
    }

    override fun deleteUserActionReport(reportId: Int) {
        repository.deleteReport(reportId = reportId,
            callback =  object : OnDataLoadedCallback<Boolean> {
            override fun onSuccess(data: Boolean) {

            }

            override fun onSuccess() {
                view.reloadData()
            }

            override fun onFailedConnect(string: String) {
                view.loadFailed(string)
            }

            override fun onFailed(exception: Exception) {
                exception.message?.let { view.loadFailed(it) }
            }

        })
    }
}