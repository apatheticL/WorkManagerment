package com.nhatle.workmangement.data.source.remote

import com.nhatle.workmangement.data.OnDataLoadedCallback
import com.nhatle.workmangement.data.model.UserTeam
import com.nhatle.workmangement.data.model.response.BaseResponse
import com.nhatle.workmangement.data.model.response.UserTeamResponse
import com.nhatle.workmangement.data.reponsitory.remote.UserTeamRepository
import com.nhatle.workmangement.data.source.UserTeamDataSource
import com.nhatle.workmangement.until.api.UserService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.Exception

class UserTeamRemoteDataSource private constructor(var userService: UserService):
UserTeamDataSource.Remote{

    override fun addMemberOnTeam(
        list: List<UserTeam>,
        callback: OnDataLoadedCallback<List<UserTeam>>
    ) {
        userService.addMemberForGroup(listUserTeam = list).enqueue(
            object :Callback<List<BaseResponse<UserTeam>>>{
                override fun onFailure(call: Call<List<BaseResponse<UserTeam>>>, t: Throwable) {
                    callback.onFailed(exception = t as Exception)
                }

                override fun onResponse(
                    call: Call<List<BaseResponse<UserTeam>>>,
                    response: Response<List<BaseResponse<UserTeam>>>
                ) {
                    callback.onSuccess()
                }

            }
        )
    }

    override fun deleteMemberOnTeam(userTeam: UserTeam, callback: OnDataLoadedCallback<Boolean>) {
       userService.deleteMemberOnGroup(userTeam=userTeam).enqueue(object :Callback<Boolean>{
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



    companion object{
        private var instance:UserTeamRemoteDataSource?=null
        @JvmStatic
        fun getInstance(userService: UserService):UserTeamRemoteDataSource=
            instance?:UserTeamRemoteDataSource(userService).also { instance = it }
    }
}