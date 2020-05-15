package com.nhatle.workmangement.data.source.remote

import com.nhatle.workmangement.data.OnDataLoadedCallback
import com.nhatle.workmangement.data.model.UserActionSmall
import com.nhatle.workmangement.data.model.response.BaseResponse
import com.nhatle.workmangement.data.model.response.UserActionSmallResponse
import com.nhatle.workmangement.data.model.response.UserTeamResponse
import com.nhatle.workmangement.data.source.UserActionSmallDataSource
import com.nhatle.workmangement.until.api.UserService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.Exception

class UserActionSmallRemoteDataSource private constructor(var userService: UserService):
    UserActionSmallDataSource.Remote {
    override fun getALlUserActionSmall(
        actionId: Int,
        callback: OnDataLoadedCallback<List<UserActionSmallResponse>>
    ) {
        userService.getAllUserMakeActionSmall(actionId).enqueue(
            object :Callback<ArrayList<UserActionSmallResponse>>{
                override fun onFailure(
                    call: Call<ArrayList<UserActionSmallResponse>>,
                    t: Throwable
                ) {
                    callback.onFailed( exception = t as Exception)
                }

                override fun onResponse(
                    call: Call<ArrayList<UserActionSmallResponse>>,
                    response: Response<ArrayList<UserActionSmallResponse>>
                ) {
                    if (response.body()==null){
                        callback.onFailedConnect("not data }")
                    }
                    response.body()?.let { callback.onSuccess(data = it) }
                }

            }
        )
    }

    override fun getAllActionSmallByUser(
        actionId: Int,
        profileId: Int,
        callback: OnDataLoadedCallback<List<UserActionSmallResponse>>
    ) {
        userService.getAllActionSmallOfUser(actionId = actionId,profileId = profileId).enqueue(
            object :Callback<ArrayList<UserActionSmallResponse>>{

                override fun onFailure(
                    call: Call<ArrayList<UserActionSmallResponse>>,
                    t: Throwable
                ) {
                    callback.onFailed(exception = t as Exception)                }

                override fun onResponse(
                    call: Call<ArrayList<UserActionSmallResponse>>,
                    response: Response<ArrayList<UserActionSmallResponse>>
                ) {
                    if (response.body()==null){
                        callback.onFailedConnect("not data")
                    }
                    response.body()?.let { callback.onSuccess(it) }
                }

            }
        )
    }

    override fun addUserActionSmall(
        userActionSmall: UserActionSmall,
        callback: OnDataLoadedCallback<UserActionSmall>
    ) {
        userService.addUserActionSmall(userActionSmall=userActionSmall).enqueue(
            object :Callback<BaseResponse<UserActionSmall>>{
                override fun onFailure(
                    call: Call<BaseResponse<UserActionSmall>>,
                    t: Throwable
                ) {
                    callback.onFailed(exception = t  as Exception)
                }

                override fun onResponse(
                    call: Call<BaseResponse<UserActionSmall>>,
                    response: Response<BaseResponse<UserActionSmall>>
                ) {
                    callback.onSuccess( response.body()!!.data)
                }

            }
        )
    }


    override fun deleteUserActionSmall(
        userActionSmallId: Int,
        callback: OnDataLoadedCallback<Boolean>
    ) {
        userService.deleteUserActionSmall(userActionSmallId=userActionSmallId).enqueue(object :Callback<Boolean>{
            override fun onFailure(call: Call<Boolean>, t: Throwable) {
                callback.onFailed(exception = t as Exception)
            }

            override fun onResponse(call: Call<Boolean>, response: Response<Boolean>) {
                if (response.body()==false){
                    callback.onFailedConnect("can not delete")
                }
                callback.onSuccess()
            }

        })
    }

    override fun updateUserActionSmall(
        userActionSmall: UserActionSmall,
        callback: OnDataLoadedCallback<UserActionSmall>
    ) {
        userService.updateUserActionSmall(userActionSmall = userActionSmall).enqueue(
            object :Callback<BaseResponse<UserActionSmall>>{
                override fun onFailure(call: Call<BaseResponse<UserActionSmall>>, t: Throwable) {
                    callback.onFailed(exception = t as Exception)
                }

                override fun onResponse(
                    call: Call<BaseResponse<UserActionSmall>>,
                    response: Response<BaseResponse<UserActionSmall>>
                ) {
                    if (response.body()!!.status==0){
                        callback.onFailedConnect("can not update ${response.message()}")
                    }
                    callback.onSuccess()
                }

            }
        )
    }

    override fun getAllMemberOnActionInGroup(
        groupId: Int,
        actionId: Int,
        callback: OnDataLoadedCallback<List<UserTeamResponse>>
    ) {
        userService.getAllMemberOnActionInGroup(groupMakeAction = groupId, actionId = actionId)
            .enqueue(object : Callback<ArrayList<UserTeamResponse>> {
                override fun onFailure(call: Call<ArrayList<UserTeamResponse>>, t: Throwable) {
                    callback.onFailed(t as Exception)
                }

                override fun onResponse(
                    call: Call<ArrayList<UserTeamResponse>>,
                    response: Response<ArrayList<UserTeamResponse>>
                ) {
                    if (response.body() == null) {
                        callback.onFailedConnect("not content")
                    }
                    response.body()?.let { callback.onSuccess(it) }
                }
            })
    }

    companion object{
        private var instance:UserActionSmallRemoteDataSource?=null
        @JvmStatic
        fun getInstance(userService: UserService):UserActionSmallRemoteDataSource =
            instance?:UserActionSmallRemoteDataSource(userService).also { instance=it }
    }
}