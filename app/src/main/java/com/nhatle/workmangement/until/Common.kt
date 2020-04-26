package com.nhatle.workmangement.until

import android.app.Activity
import android.view.inputmethod.InputMethodManager
import com.google.gson.GsonBuilder
import com.nhatle.workmangement.until.api.UserService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object Common {
    fun getUserService(): UserService {
        GsonBuilder().setLenient().create()
        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        var userService: UserService = retrofit.create(UserService::class.java)
        return userService

    }
    fun hideKeyBoard(activity: Activity){
        var inputMethodManager : InputMethodManager = activity.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        var view  = activity.currentFocus;
        if(view!=null){
            inputMethodManager.hideSoftInputFromWindow(view.windowToken,0)
        }
    }
}