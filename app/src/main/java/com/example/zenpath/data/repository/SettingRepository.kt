package com.example.zenpath.data.repository

import com.example.zenpath.data.api.ApiClient
import com.example.zenpath.data.api.ApiInterface
import com.example.zenpath.data.model.PrivacyPolicyResponse
import com.example.zenpath.data.model.TermsAndConditionsResponse
import retrofit2.Call

class SettingRepository {
    private val apiService = ApiClient.getClient().create(ApiInterface::class.java)

    fun getPrivacyPolicy(): Call<PrivacyPolicyResponse> {
        return apiService.getPrivacyPolicy()
    }

    fun getTermsAndConditions(): Call<TermsAndConditionsResponse> {
        return apiService.getTermsOfService()
    }
}