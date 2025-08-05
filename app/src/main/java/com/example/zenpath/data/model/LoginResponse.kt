package com.example.zenpath.data.model

data class LoginResponse(
    val status: Boolean,
    val message: String,
    val data: LoginData
)