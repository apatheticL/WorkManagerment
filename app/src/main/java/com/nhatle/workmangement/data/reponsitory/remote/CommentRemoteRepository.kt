package com.nhatle.workmangement.data.reponsitory.remote

import com.nhatle.workmangement.data.OnDataLoadedCallback
import com.nhatle.workmangement.data.model.Comment
import com.nhatle.workmangement.data.model.response.CommentResponse
import com.nhatle.workmangement.data.source.CommentDataSource
import com.nhatle.workmangement.data.source.remote.CommentRemoteDataSource

class CommentRemoteRepository(private val dataSource: CommentRemoteDataSource) : CommentDataSource {
    override fun sendComment(comment: Comment, callback: OnDataLoadedCallback<Comment>) {
        dataSource.sendComment(comment = comment, callback = callback)
    }

    override fun deleteComment(
        commentId: Int,
        profileId: Int,
        callback: OnDataLoadedCallback<Boolean>
    ) {
        dataSource.deleteComment(commentId = commentId, profileId = profileId, callback = callback)
    }

    override fun getAllComment(
        actionId: Int,
        callback: OnDataLoadedCallback<List<CommentResponse>>
    ) {
        dataSource.getAllComment(actionId = actionId,callback = callback)
    }
}