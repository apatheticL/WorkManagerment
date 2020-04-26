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
    override fun addMemberOnTeam(userTeam: UserTeam, callback: OnDataLoadedCallback<UserTeam>) {
        userService.addMemberForGroup(userTeam=userTeam).enqueue(
            object :Callback<BaseResponse<UserTeam>>{
                override fun onFailure(call: Call<BaseResponse<UserTeam>>, t: Throwable) {
                    callback.onFailed(exception = t as Exception)
                }

                override fun onResponse(
                    call: Call<BaseResponse<UserTeam>>,
                    response: Response<BaseResponse<UserTeam>>
                ) {
                   if (response.body()!!.status==0){
                       callback.onFailedConnect("can not add  member ${response.message()}")
                   }
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