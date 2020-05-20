package com.nhatle.workmangement.data.source

import com.nhatle.workmangement.data.OnDataLoadedCallback
import com.nhatle.workmangement.data.model.Comment
import com.nhatle.workmangement.data.model.response.CommentResponse

interface CommentDataSource {
    fun sendComment(comment:Comment,callback: OnDataLoadedCallback<Comment>)
    fun deleteComment(commentId:Int, profileId:Int,callback: OnDataLoadedCallback<Boolean>)
    fun getAllComment(actionId:Int,callback: OnDataLoadedCallback<List<CommentResponse>>)
}