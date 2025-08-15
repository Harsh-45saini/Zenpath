package com.example.zenpath.ui.viewmodel

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.zenpath.data.local.PrefManager
import com.example.zenpath.data.model.PrivacyPolicyResponse
import com.example.zenpath.data.model.TermsAndConditionsResponse
import com.example.zenpath.data.repository.SettingRepository
import retrofit2.Call
import retrofit2.Callback
import android.text.Html
import retrofit2.Response

class SettingViewModel : ViewModel() {

    private val repository = SettingRepository()

    val privacyPolicyLiveData = MutableLiveData<String>()
    val termsLiveData = MutableLiveData<String>()
    val errorLiveData = MutableLiveData<String>()

    fun logout(context: Context, onLogoutSuccess: () -> Unit) {
        val prefManager = PrefManager(context)
        prefManager.deletePrefData()
        onLogoutSuccess()
    }


    fun fetchPrivacyPolicy() {
        repository.getPrivacyPolicy().enqueue(object : Callback<PrivacyPolicyResponse> {
            override fun onResponse(
                call: Call<PrivacyPolicyResponse>,
                response: Response<PrivacyPolicyResponse>
            ) {
                if (response.isSuccessful && response.body()?.status == true) {
                    val htmlText = response.body()?.data?.detail ?: ""
                    val plainText = Html.fromHtml(htmlText, Html.FROM_HTML_MODE_LEGACY).toString()
                    privacyPolicyLiveData.value = plainText
                } else {
                    errorLiveData.value = response.body()?.message ?: "Failed to load privacy policy"
                }
            }

            override fun onFailure(call: Call<PrivacyPolicyResponse>, t: Throwable) {
                errorLiveData.value = t.message ?: "An error occurred"
            }
        })
    }

    fun fetchTermsAndConditions() {
        repository.getTermsAndConditions().enqueue(object : Callback<TermsAndConditionsResponse> {
            override fun onResponse(
                call: Call<TermsAndConditionsResponse>,
                response: Response<TermsAndConditionsResponse>
            ) {
                if (response.isSuccessful && response.body()?.status == true) {
                    val htmlText = response.body()?.data?.detail ?: ""
                    val plainText = Html.fromHtml(htmlText, Html.FROM_HTML_MODE_LEGACY).toString()
                    termsLiveData.value = plainText
                } else {
                    errorLiveData.value = response.body()?.message ?: "Failed to load terms"
                }
            }

            override fun onFailure(call: Call<TermsAndConditionsResponse>, t: Throwable) {
                errorLiveData.value = t.message ?: "An error occurred"
            }
        })
    }
}