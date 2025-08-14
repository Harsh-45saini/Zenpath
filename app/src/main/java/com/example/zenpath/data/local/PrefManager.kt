package com.example.zenpath.data.local

import android.content.Context
import android.content.SharedPreferences
import android.util.Log

class PrefManager(context: Context) {
    private val pref: SharedPreferences
    private val editor: SharedPreferences.Editor

    fun createLoginSession() {
        editor.putBoolean(IS_LOGIN, true)
        editor.commit()
        editor.apply()
    }

    fun getUserName(): String {
        val name = getPrefValue(UserName) ?: ""
        Log.d("PrefManager", "Retrieved name: $name")
        return name
    }

    fun getUserEmail(): String {
        val email = getPrefValue(UserEmail) ?: ""
        Log.d("PrefManager", "Retrieved email: $email")
        return email
    }

    fun deletePrefData() {
        editor.clear()
        editor.commit()
        editor.apply()
    }

    fun getToken(): String {
        val token = getPrefValue(HashToken) ?: ""
        Log.d("PrefManager", "Retrieved token: $token")
        return token
    }

    fun getPrefValue(key: String?): String? {
        var returnValue: String? = ""
        try {
            returnValue = pref.getString(key, "")
        } catch (ex: Exception) {
            ex.printStackTrace()
        }
        return returnValue
    }

    fun checkLogin(): Boolean {
        return pref.getBoolean(IS_LOGIN, false)
    }


    fun savePrefValue(key: String, value: String?) {
        editor.putString(key, value)
//        editor.commit()
        editor.apply()
    }

    companion object {

        var CustomerID = "customer_id"
        const val UserName = "user_name"
        const val UserEmail = "user_email"
        var CustomerLastName = "customer_last_name"
        var CustomerMobileNumber = "customer_mobilenumber"
        var CustomerEmail = "customer_email"
        var CustomerImage = "customer_image"
        var HashToken = "hashtoken"
        var LanguageId = "languageId"
        var Latititude = "latititude"
        var Longitude = "longitude"
        var LocationName = "locationname"
        var LocationID = "locationId"
        var LocationStateId = "locationstateid"
        var LocationAddress = "locationAddress"
        var KYCStatus = "kycstatus"

        var LangMaster = "languagemaster"
        private const val PREF_NAME = "MeraForm"
        private const val IS_LOGIN = "IsLoggedIn"
    }

    init {
        val pRIVATEMODE = 0
        pref = context.getSharedPreferences(PREF_NAME, pRIVATEMODE)
        editor = pref.edit()
    }
}