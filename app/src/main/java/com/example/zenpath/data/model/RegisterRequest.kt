package com.example.zenpath.data.model

data class RegisterRequest(
    val name: String,
    val email: String,
    val password: String,
    val device_type: String,
    val device_token: String
)