package com.example.zenpath.ui.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.zenpath.data.model.CategoriesResponse
import com.example.zenpath.data.repository.CategoriesRepository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

open class CategoriesViewModel(
    private val repository: CategoriesRepository = CategoriesRepository()
) : ViewModel() {

    private val _categoriesResponse = MutableLiveData<CategoriesResponse>()
    val categoriesResponse: LiveData<CategoriesResponse> get() = _categoriesResponse

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> get() = _error

    fun fetchAllCategories(token: String) {
        Log.d("CategoriesViewModel", "Fetching categories with token: $token")

        repository.getAllCategories(token).enqueue(object : Callback<CategoriesResponse> {
            override fun onResponse(
                call: Call<CategoriesResponse>,
                response: Response<CategoriesResponse>
            ) {
                if (response.isSuccessful && response.body() != null) {
                    Log.d("CategoriesViewModel", "Categories loaded successfully: ${response.body()}")
                    _categoriesResponse.postValue(response.body())
                } else {
                    val msg = "Failed: ${response.code()} - ${response.message()}"
                    Log.e("CategoriesViewModel", msg)
                    _error.postValue(msg)
                }
            }

            override fun onFailure(call: Call<CategoriesResponse>, t: Throwable) {
                val msg = "Error: ${t.message}"
                Log.e("CategoriesViewModel", msg, t)
                _error.postValue(msg)
            }
        })
    }
}