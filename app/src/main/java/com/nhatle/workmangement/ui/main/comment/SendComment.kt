package com.nhatle.workmangement.ui.main.comment

import com.nhatle.workmangement.data.model.response.CommentResponse

interface SendComment {
    fun sendData(data:CommentResponse,position:Int)
}