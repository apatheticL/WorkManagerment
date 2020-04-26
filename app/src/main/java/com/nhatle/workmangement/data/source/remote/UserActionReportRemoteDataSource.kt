package com.nhatle.workmangement.data.source.remote


import com.nhatle.workmangement.data.OnDataLoadedCallback
import com.nhatle.workmangement.data.model.UserActionReport
import com.nhatle.workmangement.data.model.response.BaseResponse
import com.nhatle.workmangement.data.model.response.UserActionReportResponse
import com.nhatle.workmangement.data.source.UserActionReportDataSource
import com.nhatle.workmangement.until.api.UserService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.Exception

class UserActionReportRemoteDataSource private constructor(var userService: UserService)
    :UserActionReportDataSource.Remote{
    override fun getAllReport(
        actionId: Int,
        callback: OnDataLoadedCallback<List<UserActionReportResponse>>
    ) {
        userService.getAllReportOnAction(actionId).enqueue(
            object :Callback<ArrayList<UserActionReportResponse>>{
                override fun onFailure(
                    call: Call<ArrayList<UserActionReportResponse>>,
                    t: Throwable
                ) {
                    callback.onFailed(exception = t as Exception)
                }

                override fun onResponse(
                    call: Call<ArrayList<UserActionReportResponse>>,
                    response: Response<ArrayList<UserActionReportResponse>>
                ) {
                    if (response.body()==null){
                        callback.onFailedConnect("not data")
                    }
                    response.body()?.let { callback.onSuccess(it) }
                }

            }
        )
    }

    override fun insertReport(
        userActionReport: UserActionReport,
        callback: OnDataLoadedCallback<UserActionReport>
    ) {
        userService.addReportOnAction(userActionReport).enqueue(
            object :Callback<BaseResponse<UserActionReport>>{
                override fun onFailure(call: Call<BaseResponse<UserActionReport>>, t: Throwable) {
                    callback.onFailed(exception = t as Exception)
                }

                override fun onResponse(
                    call: Call<BaseResponse<UserActionReport>>,
                    response: Response<BaseResponse<UserActionReport>>
                ) {
                    if (response.body()!!.status==0){
                        callback.onFailedConnect("can not insert ${response.message()}")
                    }
                    callback.onSuccess()
                }

            }
        )
    }

    override fun updateReport(
        userActionReport: UserActionReport,
        callback: OnDataLoadedCallback<UserActionReport>
    ) {
        userService.updateReportOnAction(userActionReport=userActionReport).enqueue(
            object :Callback<BaseResponse<UserActionReport>>{
                override fun onFailure(call: Call<BaseResponse<UserActionReport>>, t: Throwable) {
                    callback.onFailed(exception = t as Exception)
                }

                override fun onResponse(
                    call: Call<BaseResponse<UserActionReport>>,
                    response: Response<BaseResponse<UserActionReport>>
                ) {
                    if (response.body()!!.status==0){
                        callback.onFailedConnect("can not update ${response.message()}")
                    }
                    callback.onSuccess()
                }

            })
    }

    override fun deleteReport(reportId: Int, callback: OnDataLoadedCallback<Boolean>) {
        userService.deleteReportOnAction(reportId).enqueue(
            object :Callback<Boolean>{
                override fun onFailure(call: Call<Boolean>, t: Throwable) {
                    callback.onFailed(exception = t as Exception)
                }

                override fun onResponse(
                    call: Call<Boolean>,
                    response: Response<Boolean>
                ) {
                    if (response.body() == false){
                        callback.onFailedConnect("can not delete ${response.message()}")
                    }
                    callback.onSuccess()
                }

            })
    }
    companion object{
        private var instance: UserActionReportRemoteDataSource?=null
        @JvmStatic
        fun getInstance(userService: UserService): UserActionReportRemoteDataSource =
            instance ?: UserActionReportRemoteDataSource(userService).also { instance =it }
    }
}