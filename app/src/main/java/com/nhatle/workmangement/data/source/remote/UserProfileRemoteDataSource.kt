package com.nhatle.workmangement.data.source.remote

import com.nhatle.workmangement.data.model.UserProfile
import com.nhatle.workmangement.data.model.response.BaseResponse
import com.nhatle.workmangement.data.model.response.LoginResponse
import com.nhatle.workmangement.data.model.response.RegisterResponse
import com.nhatle.workmangement.data.OnDataLoadedCallback
import com.nhatle.workmangement.data.source.UserProfileDataSource
import com.nhatle.workmangement.until.api.UserService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserProfileRemoteDataSource private constructor(
    var userService: UserService
) : UserProfileDataSource.Remote {
    override fun login(
        userName: String,
        password: String,
        callback: OnDataLoadedCallback<UserProfile>
    ) {
        val login = LoginResponse(userName, password)
        userService.login(login).enqueue(object : Callback<BaseResponse<UserProfile>> {
            override fun onFailure(call: Call<BaseResponse<UserProfile>>, t: Throwable) {
                callback.onFailed(t as Exception)
            }

            override fun onResponse(
                call: Call<BaseResponse<UserProfile>>,
                response: Response<BaseResponse<UserProfile>>
            ) {
                if (response.body()?.status == 0) {
                    callback.onFailedConnect(response.body()?.message!!)
                } else {
                    callback.onSuccess(response.body()!!.data)
                }
            }

        })
    }

    override fun registerAccount(
        registerResponse: RegisterResponse,
        callback: OnDataLoadedCallback<UserProfile>
    ) {
        userService.register(registerResponse)
            .enqueue(object : Callback<BaseResponse<UserProfile>> {
                override fun onFailure(call: Call<BaseResponse<UserProfile>>, t: Throwable) {
                    callback.onFailed(t as Exception)
                }

                override fun onResponse(
                    call: Call<BaseResponse<UserProfile>>,
                    response: Response<BaseResponse<UserProfile>>
                ) {
                    if (response.body()?.status == 0) {
                        callback.onFailedConnect(response.body()!!.message)
                    } else {
                        callback.onSuccess(response.body()!!.data)
                    }
                }

            })
    }

    override fun updateProfile(
        userProfile: UserProfile,
        callback: OnDataLoadedCallback<UserProfile>
    ) {
        userService.updateProfile(userProfile)
            .enqueue(object : Callback<BaseResponse<UserProfile>> {
                override fun onFailure(call: Call<BaseResponse<UserProfile>>, t: Throwable) {
                    callback.onFailed(t as Exception)
                }

                override fun onResponse(
                    call: Call<BaseResponse<UserProfile>>,
                    response: Response<BaseResponse<UserProfile>>
                ) {
                    if (response.body()!!.status == 0) {
                        callback.onFailedConnect(response.body()!!.message)
                    } else {
                        callback.onSuccess(response.body()!!.data)
                    }
                }

            })
    }

    override fun getInfoProfile(profileId: Int, callback: OnDataLoadedCallback<UserProfile>) {
        userService.getInfoProfile(profileId).enqueue(object :Callback<UserProfile>{
            override fun onFailure(call: Call<UserProfile>, t: Throwable) {
                callback.onFailed(t as java.lang.Exception)
            }

            override fun onResponse(call: Call<UserProfile>, response: Response<UserProfile>) {
                response.body()?.let { callback.onSuccess(it) }
            }
        })
    }

    companion object{
        private var instance :UserProfileRemoteDataSource?=null

        @JvmStatic
        fun getInstance(userService: UserService):UserProfileRemoteDataSource =
            instance?:UserProfileRemoteDataSource(userService).also { instance = it }
    }

}