package com.example.zenpath.ui.viewmodel

import android.content.Context
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.zenpath.data.model.LoginRequest
import com.example.zenpath.data.model.LoginResponse
import com.example.zenpath.data.repository.AuthRepository
import com.example.zenpath.utils.UserSessionUtil
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginViewModel(
    private val repository: AuthRepository = AuthRepository()
) : ViewModel() {

    private val _username = MutableStateFlow("")
    val username: StateFlow<String> = _username

    private val _password = MutableStateFlow("")
    val password: StateFlow<String> = _password

    var loginStatus = mutableStateOf("")
        private set

    private val _isLoginSuccessful = MutableStateFlow(false)
    val isLoginSuccessful: StateFlow<Boolean> = _isLoginSuccessful

    fun onUsernameChanged(newVal: String) {
        _username.value = newVal
    }

    fun onPasswordChanged(newVal: String) {
        _password.value = newVal
    }

    fun validateCredentials(): Boolean {
        return username.value.isNotBlank() && password.value.isNotBlank()
    }

    fun loginUser(context: Context) {
        val request = LoginRequest(email = username.value.trim(), password = password.value.trim())
        Log.d("LoginAPI", "Sending login request: $request")

        val call = repository.loginUser(request)

        call.enqueue(object : Callback<LoginResponse> {
            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                Log.d("LoginAPI", "Received response: ${response.code()}")

                if (response.isSuccessful && response.body()?.status == true) {
                    val token = response.body()?.data?.token ?: ""
                    val user = response.body()?.data?.user
                    val userName = user?.name ?: ""
                    val userEmail = user?.email ?: ""

                    loginStatus.value = "Login Success: $token"
                    _isLoginSuccessful.value = true
                    Log.d("LoginViewModel", "Loaded full name: $userName")
                    UserSessionUtil.saveUserSession(context, token, userName, userEmail)

                } else {
                    val errorBody = response.errorBody()?.string()
                    val message = response.body()?.message ?: errorBody ?: "Unknown error"
                    loginStatus.value = "Login Failed: $message"
                    _isLoginSuccessful.value = false
                }
            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                loginStatus.value = "Login Error: ${t.message}"
                _isLoginSuccessful.value = false
                Log.e("LoginAPI", "Failure: ${t.message}", t)
            }
        })
    }
}