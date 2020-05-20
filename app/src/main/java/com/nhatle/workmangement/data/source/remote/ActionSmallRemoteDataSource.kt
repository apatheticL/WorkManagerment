package com.nhatle.workmangement.data.source.remote

import com.nhatle.workmangement.data.OnDataLoadedCallback
import com.nhatle.workmangement.data.model.ActionSmall
import com.nhatle.workmangement.data.model.response.BaseResponse
import com.nhatle.workmangement.data.source.ActionSmallDataSource
import com.nhatle.workmangement.until.api.UserService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.Exception

class ActionSmallRemoteDataSource private constructor(var userService: UserService) :
    ActionSmallDataSource.Remote {
    override fun getAllActionSmall(
        actionId: Int,
        callback: OnDataLoadedCallback<List<ActionSmall>>
    ) {
        userService.getAllActionSmallByAction(actionId).enqueue(
            object :Callback<ArrayList<ActionSmall>>{
                override fun onFailure(call: Call<ArrayList<ActionSmall>>, t: Throwable) {
                    callback.onFailed( t as Exception)
                }

                override fun onResponse(
                    call: Call<ArrayList<ActionSmall>>,
                    response: Response<ArrayList<ActionSmall>>
                ) {
                    if (response.body()?.size==0){
                        callback.onFailedConnect("not data")
                    }
                    response.body()?.let { callback.onSuccess(it) }
                }

            }
        )
    }

    override fun insertActionSmall(
        list: List<ActionSmall>,
        callback: OnDataLoadedCallback<Boolean>
    ) {
        userService.addActionSmall(list).enqueue(object :Callback<List<BaseResponse<ActionSmall>>>{
            override fun onFailure(call: Call<List<BaseResponse<ActionSmall>>>, t: Throwable) {
                callback.onFailed(t as Exception)
            }

            override fun onResponse(
                call: Call<List<BaseResponse<ActionSmall>>>,
                response: Response<List<BaseResponse<ActionSmall>>>
            ) {
                if (response.body()==null){
                    callback.onFailedConnect(response.message())
                }
                callback.onSuccess()
            }

        })
    }

    override fun updateActionSmall(
        actionSmall: ActionSmall,
        callback: OnDataLoadedCallback<Boolean>
    ) {
        userService.updateActionSmall(actionSmall =actionSmall ).enqueue(object :Callback<BaseResponse<ActionSmall>>{
            override fun onFailure(call: Call<BaseResponse<ActionSmall>>, t: Throwable) {
                callback.onFailed(t as Exception)
            }

            override fun onResponse(
                call: Call<BaseResponse<ActionSmall>>,
                response: Response<BaseResponse<ActionSmall>>
            ) {
                callback.onSuccess(true)
            }

        })
    }

    override fun deleteActionSmall(actionSmallId: Int, callback: OnDataLoadedCallback<Boolean>) {
        userService.deleteActionSmall(actionSmallId).enqueue(object :Callback<Boolean>{
            override fun onFailure(call: Call<Boolean>, t: Throwable) {
                callback.onFailed(t as Exception)
            }

            override fun onResponse(call: Call<Boolean>, response: Response<Boolean>) {
                if (response.body()==false){
                    callback.onFailedConnect(response.message())
                }
                callback.onSuccess()
            }

        })
    }

    override fun getAllActionSmallOnActionOfUserById(
        actionId: Int,
        profileId: Int,
        callback: OnDataLoadedCallback<List<ActionSmall>>
    ) {
        userService.getAllActionSmallOnActionOfUser(actionId, profileId).enqueue(object :Callback<ArrayList<ActionSmall>>{
            override fun onFailure(call: Call<ArrayList<ActionSmall>>, t: Throwable) {
                callback.onFailed(t as Exception)
            }

            override fun onResponse(
                call: Call<ArrayList<ActionSmall>>,
                response: Response<ArrayList<ActionSmall>>
            ) {
                if (response.body()!!.isEmpty()){
                    callback.onFailedConnect("Khong cos du lieu")
                }
                else{
                    callback.onSuccess(response.body()!!)
                }
            }

        })
    }

    companion object {
        private var instance: ActionSmallRemoteDataSource? = null

        @JvmStatic
        fun getInstance(userService: UserService): ActionSmallRemoteDataSource =
            instance ?: ActionSmallRemoteDataSource(userService).also { instance = it }
    }
}