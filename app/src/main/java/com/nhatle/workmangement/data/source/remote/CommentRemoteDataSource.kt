package com.nhatle.workmangement.data.source.remote

import com.nhatle.workmangement.data.OnDataLoadedCallback
import com.nhatle.workmangement.data.model.Comment
import com.nhatle.workmangement.data.model.response.BaseResponse
import com.nhatle.workmangement.data.model.response.CommentResponse
import com.nhatle.workmangement.data.source.CommentDataSource
import com.nhatle.workmangement.until.api.UserService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.Exception

class CommentRemoteDataSource private constructor(var userService: UserService):CommentDataSource{
    override fun sendComment(comment: Comment, callback: OnDataLoadedCallback<Comment>) {
        userService.sendComment(comment).enqueue(object :Callback<BaseResponse<Comment>>{
            override fun onFailure(call: Call<BaseResponse<Comment>>, t: Throwable) {
                callback.onFailed( t as Exception)
            }

            override fun onResponse(
                call: Call<BaseResponse<Comment>>,
                response: Response<BaseResponse<Comment>>
            ) {
                if (response.body()!!.status==0){
                    callback.onFailedConnect(response.body()!!.message)
                }
                callback.onSuccess()
            }

        })
    }

    override fun deleteComment(
        commentId: Int,
        profileId: Int,
        callback: OnDataLoadedCallback<Boolean>
    ) {
       userService.deleteCommentOnAction(commentId,profileId).enqueue(object :Callback<Boolean>{
           override fun onFailure(call: Call<Boolean>, t: Throwable) {
               callback.onFailed(t as Exception)
           }

           override fun onResponse(call: Call<Boolean>, response: Response<Boolean>) {
               if (response.body()==false){
                   callback.onFailedConnect("can not insert")
               }
               callback.onSuccess()
           }

       })
    }

    override fun getAllComment(
        actionId: Int,
        callback: OnDataLoadedCallback<List<CommentResponse>>
    ) {
        userService.getAllCommentOnAction(actionId = actionId).enqueue(
            object :Callback<ArrayList<CommentResponse>>{
            override fun onFailure(call: Call<ArrayList<CommentResponse>>, t: Throwable) {
               callback.onFailed(t as Exception)
            }

            override fun onResponse(
                call: Call<ArrayList<CommentResponse>>,
                response: Response<ArrayList<CommentResponse>>
            ) {
                if (response.body()==null){
                    callback.onFailedConnect("not data")
                }
                response.body()?.let { callback.onSuccess(it) }
            }

        })
    }
    companion object{
        private var instance:CommentRemoteDataSource?=null
        @JvmStatic
        fun getInstance(userService: UserService) =
            instance?:CommentRemoteDataSource(userService).also { instance= it }
    }
}