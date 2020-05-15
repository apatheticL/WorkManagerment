package com.nhatle.workmangement.ui.main.report.add

import com.nhatle.workmangement.data.OnDataLoadedCallback
import com.nhatle.workmangement.data.model.ActionSmall
import com.nhatle.workmangement.data.model.UserActionReport
import com.nhatle.workmangement.data.reponsitory.remote.ActionSmallRemoteRepository
import com.nhatle.workmangement.data.reponsitory.remote.UserActionReportRepository
import com.nhatle.workmangement.data.reponsitory.remote.UserActionSmallRepository
import java.lang.Exception

class AddReportPresenter(
    val view: AddActionReportContract.View,
    val repository: UserActionReportRepository,
    val actionSmallRepository: ActionSmallRemoteRepository
) :AddActionReportContract.Present{
    override fun insertReport(userActionReportResponse: UserActionReport) {
        repository.insertReport(userActionReport = userActionReportResponse,
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

    override fun getAllActionSmall(actionId: Int, profileId: Int) {
        actionSmallRepository.getAllActionSmallOnActionOfUserById(actionId,profileId,object:OnDataLoadedCallback<List<ActionSmall>>{
            override fun onSuccess(data: List<ActionSmall>) {
                view.getAllActionSmall(data)
            }

            override fun onSuccess() {
            }

            override fun onFailedConnect(string: String) {
                view.loadFail(string)
            }

            override fun onFailed(exception: Exception) {
                view.loadFail(exception.message.toString())
            }

        })
    }
}