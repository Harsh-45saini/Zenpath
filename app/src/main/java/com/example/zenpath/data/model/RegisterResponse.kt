package com.example.zenpath.data.model

data class RegisterResponse(
    val status: Boolean,
    val message: String,
    val data: RegisterData
)