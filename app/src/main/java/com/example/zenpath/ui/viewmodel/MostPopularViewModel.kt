package com.example.zenpath.ui.viewmodel

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.zenpath.data.local.PrefManager
import com.example.zenpath.data.model.DailyPractice
import com.example.zenpath.data.model.DailyPracticeResponse
import com.example.zenpath.data.repository.MostPopularRepository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MostPopularViewModel(context: Context) : ViewModel() {

    // Initialize PrefManager and Repository
    private val prefManager = PrefManager(context)
    private val repository = MostPopularRepository(context)

    // LiveData for API response
    private val _musicList = MutableLiveData<List<DailyPractice>>()
    val musicList: LiveData<List<DailyPractice>> get() = _musicList

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> get() = _error

    // Fetch all daily practices
    fun fetchMostPopularAll() {
        repository.getAllDailyPractices().enqueue(object : Callback<DailyPracticeResponse> {
            override fun onResponse(
                call: Call<DailyPracticeResponse>,
                response: Response<DailyPracticeResponse>
            ) {
                if (response.isSuccessful && response.body() != null) {
                    val body = response.body()!!
                    Log.d("API_RESPONSE", "Success: $body")

                    if (body.status) {
                        _musicList.postValue(body.data)
                    } else {
                        _error.postValue(body.message)
                    }
                } else {
                    val errorText = try {
                        response.errorBody()?.string()
                    } catch (e: Exception) {
                        "Error reading errorBody: ${e.message}"
                    }
                    Log.d("API_RESPONSE", "Failed: $errorText")
                    _error.postValue("Failed: ${response.code()} - $errorText")
                }
            }

            override fun onFailure(call: Call<DailyPracticeResponse>, t: Throwable) {
                Log.d("API_RESPONSE", "Failed: ${t.message}")
                _error.postValue("Error: ${t.message}")
            }
        })
    }
}