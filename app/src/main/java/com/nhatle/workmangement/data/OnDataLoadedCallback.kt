package com.nhatle.workmangement.data

import com.nhatle.workmangement.data.model.response.UserTeamResponse
import java.lang.Exception

interface OnDataLoadedCallback<T> {
    fun onSuccess(data: T)
    fun onSuccess()
    fun onFailedConnect(string: String)
    fun onFailed(exception: Exception)
}