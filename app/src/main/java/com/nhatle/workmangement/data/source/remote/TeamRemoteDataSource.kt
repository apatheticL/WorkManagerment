package com.nhatle.workmangement.data.source.remote

import com.nhatle.workmangement.data.OnDataLoadedCallback
import com.nhatle.workmangement.data.model.Team
import com.nhatle.workmangement.data.model.response.BaseResponse
import com.nhatle.workmangement.data.source.TeamDataSource
import com.nhatle.workmangement.until.api.UserService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.Exception

class TeamRemoteDataSource private constructor(var userService: UserService):TeamDataSource.Remote {
    override fun deleteTeam(temId: Int, callback: OnDataLoadedCallback<Boolean>) {
        userService.deleteGroup(groupId = temId).enqueue(
            object :Callback<Boolean>{
                override fun onFailure(call: Call<Boolean>, t: Throwable) {
                    callback.onFailed(t as Exception)
                }

                override fun onResponse(call: Call<Boolean>, response: Response<Boolean>) {
                    if (response.body()==false){
                        callback.onFailedConnect("can not delete + ${response.message()}")
                    }
                    callback.onSuccess()
                }

            }
        )
    }

    override fun addTeam(team: Team, callback: OnDataLoadedCallback<Team>) {
        userService.addGroup(team=team).enqueue(object :Callback<BaseResponse<Team>>{
            override fun onFailure(call: Call<BaseResponse<Team>>, t: Throwable) {
                callback.onFailed(exception = t as Exception)
            }

            override fun onResponse(
                call: Call<BaseResponse<Team>>,
                response: Response<BaseResponse<Team>>
            ) {
                if (response.body()!!.status==0){
                    callback.onFailedConnect(response.message())
                }
                callback.onSuccess(response.body()!!.data)
            }

        })
    }
    companion object{
        private var instance:TeamRemoteDataSource?=null
        @JvmStatic
        fun getInstance(userService: UserService):TeamRemoteDataSource=
            instance?:TeamRemoteDataSource(userService).also { instance=it }
    }
}