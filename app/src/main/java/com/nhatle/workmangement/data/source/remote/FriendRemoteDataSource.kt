package com.nhatle.workmangement.data.source.remote

import com.nhatle.workmangement.data.OnDataLoadedCallback
import com.nhatle.workmangement.data.model.InvitationFriend
import com.nhatle.workmangement.data.model.UserProfile
import com.nhatle.workmangement.data.model.response.BaseResponse
import com.nhatle.workmangement.data.model.response.FriendResponse
import com.nhatle.workmangement.data.source.FriendDataSource
import com.nhatle.workmangement.until.api.UserService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.Exception

class FriendRemoteDataSource private constructor(val userService: UserService):FriendDataSource {
    override fun getAllFriendByUser(
        profileId: Int,
        callback: OnDataLoadedCallback<List<FriendResponse>>
    ) {
        userService.getAllFriendByUser(profileId).enqueue(
            object :Callback<ArrayList<FriendResponse>>{
                override fun onFailure(call: Call<ArrayList<FriendResponse>>, t: Throwable) {
                    callback.onFailed(exception = t as Exception)
                }

                override fun onResponse(
                    call: Call<ArrayList<FriendResponse>>,
                    response: Response<ArrayList<FriendResponse>>
                ) {
                    if (response.body()==null){
                        callback.onFailedConnect("not data")
                    }
                    response.body()?.let { callback.onSuccess(it) }
                }

            })
    }

    override fun getAllUserNotFriend(
        profileId: Int,
        callback: OnDataLoadedCallback<List<UserProfile>>
    ) {
        userService.getAllNotFriend(profileId).enqueue(
            object :Callback<ArrayList<UserProfile>>{
                override fun onFailure(call: Call<ArrayList<UserProfile>>, t: Throwable) {
                    callback.onFailed( t as Exception)
                }

                override fun onResponse(
                    call: Call<ArrayList<UserProfile>>,
                    response: Response<ArrayList<UserProfile>>
                ) {
                    if (response.body()==null){
                        callback.onFailedConnect("not data")
                    }
                    response.body()?.let { callback.onSuccess(it) }
                }

            }
        )
    }

    override fun acceptedFriend(
        invitationFriend: InvitationFriend,
        callback: OnDataLoadedCallback<Boolean>
    ) {
        userService.acceptedFriend(invitationFriend).enqueue(object :Callback<Boolean>{
            override fun onFailure(call: Call<Boolean>, t: Throwable) {
                callback.onFailed(t as Exception)
            }

            override fun onResponse(call: Call<Boolean>, response: Response<Boolean>) {
                if (response.body()==false){
                    callback.onFailedConnect("can not accept")
                }
                callback.onSuccess()
            }

        })
    }

    override fun senderFriend(
        invitationFriend: InvitationFriend,
        callback: OnDataLoadedCallback<InvitationFriend>
    ) {
        userService.senderFriend(invitationFriend).enqueue(
            object :Callback<BaseResponse<InvitationFriend>>{
                override fun onFailure(call: Call<BaseResponse<InvitationFriend>>, t: Throwable) {
                    callback.onFailed(t as Exception)
                }

                override fun onResponse(
                    call: Call<BaseResponse<InvitationFriend>>,
                    response: Response<BaseResponse<InvitationFriend>>
                ) {
                    if (response.body()==null){
                        callback.onFailedConnect("can not add friend")
                    }
                    callback.onSuccess(response.body()!!.data)
                }
            }
        )
    }

    override fun getAllUserSenderFriend(
        profileId: Int,
        callback: OnDataLoadedCallback<List<FriendResponse>>
    ) {
        userService.getAllUserSenderFriend(profileId).enqueue(
            object :Callback<ArrayList<FriendResponse>>{
                override fun onFailure(call: Call<ArrayList<FriendResponse>>, t: Throwable) {
                    callback.onFailed(t as Exception)
                }

                override fun onResponse(
                    call: Call<ArrayList<FriendResponse>>,
                    response: Response<ArrayList<FriendResponse>>
                ) {
                    if (response.body()==null){
                        callback.onFailedConnect("not data")
                    }
                    response.body()?.let { callback.onSuccess(it) }
                }
            }
        )
    }

    override fun deleteInvitationFriend(friendId: Int, callback: OnDataLoadedCallback<Boolean>) {
        userService.deleteInvitationFriend(friendId).enqueue(object :Callback<Boolean>{
            override fun onFailure(call: Call<Boolean>, t: Throwable) {
                callback.onFailed( t as Exception)
            }

            override fun onResponse(call: Call<Boolean>, response: Response<Boolean>) {
                if (response.body()==false){
                    callback.onFailedConnect("can not delete")
                }
                callback.onSuccess()
            }

        })
    }

    override fun cancelInvitationFriend(
        senderId: Int,
        receiverId: Int,
        callback: OnDataLoadedCallback<Boolean>
    ) {
        userService.cancelInvitationFriend(senderId,receiverId).enqueue(object :Callback<Boolean>{
            override fun onFailure(call: Call<Boolean>, t: Throwable) {
                callback.onFailed(t as Exception)
            }

            override fun onResponse(call: Call<Boolean>, response: Response<Boolean>) {
                if (response.body()==false){
                    callback.onFailedConnect("can not delete invitation")
                }
                callback.onSuccess()
            }
        })
    }

    companion object{
        private var instance:FriendRemoteDataSource?=null
        @JvmStatic
        fun getInStance(userService: UserService):FriendRemoteDataSource =
            instance?:FriendRemoteDataSource(userService).also { instance = it }
    }
}