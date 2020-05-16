package com.nhatle.workmangement.data.source.remote

import com.nhatle.workmangement.data.OnDataLoadedCallback
import com.nhatle.workmangement.data.model.Action
import com.nhatle.workmangement.data.model.response.ActionResponse
import com.nhatle.workmangement.data.model.response.BaseResponse
import com.nhatle.workmangement.data.model.response.UserTeamResponse
import com.nhatle.workmangement.data.source.ActionDataSource
import com.nhatle.workmangement.until.api.UserService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ActionRemoteDataSource private constructor(var userService: UserService) :
    ActionDataSource.Remote {
    override fun getAllActionUserMember(
        profileId: Int,
        callback: OnDataLoadedCallback<List<ActionResponse>>
    ) {
        userService.getAllWorkWithUserIsMember(profileId)
            .enqueue(object : Callback<ArrayList<ActionResponse>> {
                override fun onFailure(call: Call<ArrayList<ActionResponse>>, t: Throwable) {
                    callback.onFailed(t as Exception)
                }

                override fun onResponse(
                    call: Call<ArrayList<ActionResponse>>,
                    response: Response<ArrayList<ActionResponse>>
                ) {
                    if (response.body() == null) {
                        callback.onFailedConnect("not content")
                    }
                    response.body()?.let { callback.onSuccess(it) }
                }

            })
    }

    override fun insertAction(action: Action, callback: OnDataLoadedCallback<Action>) {
        userService.addWork(action).enqueue(object : Callback<BaseResponse<Action>> {
            override fun onFailure(call: Call<BaseResponse<Action>>, t: Throwable) {
                callback.onFailed(t as Exception)
            }

            override fun onResponse(
                call: Call<BaseResponse<Action>>,
                response: Response<BaseResponse<Action>>
            ) {
                if (response.body()!!.status == 0) {
                    callback.onFailedConnect("not content")
                }
                callback.onSuccess(response.body()!!.data)
//                response.body()?.let { callback.onSuccess() }
            }

        })
    }

    override fun deleteAction(
        actionId: Int,
        profileId: Int,
        callback: OnDataLoadedCallback<Boolean>
    ) {
        userService.deleteWork(actionId,profileId).enqueue(object : Callback<Boolean> {
            override fun onFailure(call: Call<Boolean>, t: Throwable) {
                callback.onFailed(t as Exception)
            }

            override fun onResponse(call: Call<Boolean>, response: Response<Boolean>) {
                callback.onSuccess(response.body()!!)
            }

        })
    }

    override fun updateAction(action: Action, callback: OnDataLoadedCallback<Action>) {
        userService.updateWork(action).enqueue(object : Callback<BaseResponse<Action>> {
            override fun onFailure(call: Call<BaseResponse<Action>>, t: Throwable) {
                callback.onFailed(t as Exception)
            }

            override fun onResponse(
                call: Call<BaseResponse<Action>>,
                response: Response<BaseResponse<Action>>
            ) {
                if (response.body()!!.status == 0) {
                    callback.onFailedConnect(response.message())
                }
                callback.onSuccess()
            }

        })
    }

    override fun getAllMemberOnAction(
        actionId: Int,
        idTeam: Int,
        callback: OnDataLoadedCallback<List<UserTeamResponse>>
    ) {
        userService.getAllMemberOnActionInGroup(groupMakeAction = idTeam, actionId = actionId)
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


    companion object {
        private var instance: ActionRemoteDataSource? = null

        @JvmStatic
        fun getInstance(userService: UserService): ActionRemoteDataSource =
            instance ?: ActionRemoteDataSource(userService).also { instance = it }
    }
}