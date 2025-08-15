package com.example.zenpath.data.model

data class TermsAndConditionsResponse(
    val status: Boolean,
    val message: String,
    val data: TermsData
)

data class TermsData(
    val id: Int,
    val detail: String,
    val statusFlag: Int,
    val created_at: String,
    val updated_at: String,
    val created_user: String,
    val updated_user: String
)
