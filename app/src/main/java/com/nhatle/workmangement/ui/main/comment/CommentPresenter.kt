package com.nhatle.workmangement.ui.main.comment

import com.nhatle.workmangement.data.OnDataLoadedCallback
import com.nhatle.workmangement.data.model.Comment
import com.nhatle.workmangement.data.model.response.CommentResponse
import com.nhatle.workmangement.data.reponsitory.remote.CommentRemoteRepository

class CommentPresenter(
    val view: CommentContract.View
    , val repository: CommentRemoteRepository
) :
    CommentContract.Presenter {
    override fun addComment(comment: Comment) {
        repository.sendComment(comment,
            object : OnDataLoadedCallback<Comment> {
            override fun onSuccess(data: Comment) {

            }

            override fun onSuccess() {
                view.insertSuccess()
            }

            override fun onFailedConnect(string: String) {
                view.onFail(string)
            }

            override fun onFailed(exception: Exception) {
                view.onFail(exception.message.toString())
            }

        })
    }

    override fun getAllComment(actionId: Int) {
        repository.getAllComment(actionId,
            object : OnDataLoadedCallback<List<CommentResponse>> {
            override fun onSuccess(data: List<CommentResponse>) {
                view.getAllComment(data)
            }

            override fun onSuccess() {

            }

            override fun onFailedConnect(string: String) {
                view.onFail(string)
            }

            override fun onFailed(exception: Exception) {
                view.onFail(exception.message.toString())
            }

        })
    }

    override fun deleteComment(commentId: Int, profileId: Int) {
        repository.deleteComment(commentId, profileId,
            object : OnDataLoadedCallback<Boolean> {
            override fun onSuccess(data: Boolean) {

            }

            override fun onSuccess() {
                view.insertSuccess()
            }

            override fun onFailedConnect(string: String) {
                view.onFail(string)
            }

            override fun onFailed(exception: Exception) {
                view.onFail(exception.message.toString())
            }

        })
    }
}