package com.example.zenpath.ui.viewmodel

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModel
import com.example.zenpath.data.local.PrefManager
import com.example.zenpath.data.model.RegisterRequest
import com.example.zenpath.data.model.RegisterResponse
import com.example.zenpath.data.repository.AuthRepository
import com.example.zenpath.utils.UserSessionUtil
import com.google.firebase.messaging.FirebaseMessaging
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegisterViewModel(
    private val repository: AuthRepository = AuthRepository()
) : ViewModel() {

    private val _fullName = MutableStateFlow("")
    val fullName: StateFlow<String> = _fullName

    private val _email = MutableStateFlow("")
    val email: StateFlow<String> = _email

    private val _password = MutableStateFlow("")
    val password: StateFlow<String> = _password

    val registerStatus = MutableStateFlow("")
    val isRegisterSuccessful = MutableStateFlow(false)

    fun onFullNameChanged(newVal: String) {
        _fullName.value = newVal
    }

    fun onEmailChanged(newVal: String) {
        _email.value = newVal
    }

    fun onPasswordChanged(newVal: String) {
        _password.value = newVal
    }

    fun validateRegisterFields(): Boolean {
        return fullName.value.isNotBlank() && email.value.isNotBlank() && password.value.isNotBlank()
    }

    fun registerUser(context: Context) {
        // Get FCM Token (asynchronously)
        FirebaseMessaging.getInstance().token
            .addOnCompleteListener { task ->
                val fcmToken = if (task.isSuccessful) {
                    task.result ?: "device_token"
                } else {
                    "device_token"
                }

                val request = RegisterRequest(
                    name = fullName.value,
                    email = email.value,
                    password = password.value,
                    device_type = "android",
                    device_token = fcmToken
                )

                val call = repository.registerUser(request)
                call.enqueue(object : Callback<RegisterResponse> {
                    override fun onResponse(
                        call: Call<RegisterResponse>,
                        response: Response<RegisterResponse>
                    ) {
                        if (response.isSuccessful && response.body()?.status == true) {
                            val token = response.body()?.data?.token ?: ""
                            val user = response.body()?.data?.user
                            val userName = user?.name ?: ""
                            val userEmail = user?.email ?: ""

                            registerStatus.value = "Register Success"
                            isRegisterSuccessful.value = true
                            Log.d("RegisterViewModel", "Loaded full name: $userName")

                            UserSessionUtil.saveUserSession(context, token, userName, userEmail)

                            Toast.makeText(context, "Registration successful!", Toast.LENGTH_SHORT).show()
                        } else {
                            val message = response.body()?.message ?: "Registration failed"
                            registerStatus.value = "Register Failed: $message"
                            isRegisterSuccessful.value = false
                            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
                        }
                    }

                    override fun onFailure(call: Call<RegisterResponse>, t: Throwable) {
                        registerStatus.value = "Register Error: ${t.localizedMessage}"
                        isRegisterSuccessful.value = false
                        Toast.makeText(context, registerStatus.value, Toast.LENGTH_SHORT).show()
                        Log.e("RegisterAPI", "Failure: ${t.message}", t)
                    }
                })
            }
    }
}