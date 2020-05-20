package com.nhatle.workmangement.until

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import com.nhatle.workmangement.data.model.UserProfile

class ShareUntil {
    companion object {
        @JvmStatic
        fun saveUserProfile(context: Context, userProfile: UserProfile) {
            val sharedPreferences: SharedPreferences =
                context.getSharedPreferences(
                    SHARE, Context.MODE_PRIVATE
                )
            sharedPreferences.edit()
                .putString(USER, Gson().toJson(userProfile))
                .apply()
        }

        @JvmStatic
        fun getUserProfile(context: Context): UserProfile? {
            val userString: String? =
                context.getSharedPreferences(SHARE, Context.MODE_PRIVATE)
                    .getString(USER, null)
            if (userString != null) {
                return Gson().fromJson(userString, UserProfile::class.java)
            }
            return null
        }

        @JvmStatic
        fun clearProfile(context: Context) {
            context.getSharedPreferences(SHARE, Context.MODE_PRIVATE)
                .edit().putString(USER, null).apply()
        }
    }
}