package com.example.zenpath.utils

import android.content.Context
import android.util.Log
import com.example.zenpath.data.local.PrefManager

object UserSessionUtil {
    fun saveUserSession(context: Context, token: String, userName: String) {
        val prefManager = PrefManager(context)
        Log.d("UserSessionUtil", "Saving token: $token")
        Log.d("UserSessionUtil", "Saving userName: $userName")
        prefManager.savePrefValue(PrefManager.HashToken, token)
        prefManager.savePrefValue(PrefManager.UserName, userName)
        prefManager.createLoginSession()
    }
}
