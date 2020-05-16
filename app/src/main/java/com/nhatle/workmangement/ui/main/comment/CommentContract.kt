package com.nhatle.workmangement.ui.main.comment

import com.nhatle.workmangement.data.OnDataLoadedCallback
import com.nhatle.workmangement.data.model.Comment
import com.nhatle.workmangement.data.model.response.CommentResponse

interface CommentContract {
    interface View{
        fun getAllComment(list: List<CommentResponse>)
        fun deleteSuccess()
        fun onFail(string: String)
        fun insertSuccess()
    }
    interface Presenter{
        fun addComment(comment:Comment)
        fun getAllComment(actionId:Int)
        fun deleteComment(commentId: Int,profileId:Int)
    }
}