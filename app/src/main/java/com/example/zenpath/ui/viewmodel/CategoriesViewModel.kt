package com.example.zenpath.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.zenpath.data.model.CategoriesResponse
import com.example.zenpath.data.repository.CategoriesRepository
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CategoriesViewModel(
    private val repository: CategoriesRepository = CategoriesRepository()
) : ViewModel() {

    private val _categoriesResponse = MutableLiveData<CategoriesResponse>()
    val categoriesResponse: LiveData<CategoriesResponse> get() = _categoriesResponse

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> get() = _error

    fun fetchAllCategories(token: String) {
        viewModelScope.launch {
            repository.getAllCategories(token).enqueue(object : Callback<CategoriesResponse> {
                override fun onResponse(
                    call: Call<CategoriesResponse>,
                    response: Response<CategoriesResponse>
                ) {
                    if (response.isSuccessful) {
                        response.body()?.let {
                            _categoriesResponse.postValue(it)
                        } ?: run {
                            _error.postValue("Empty response from server")
                        }
                    } else {
                        _error.postValue("Failed to fetch categories: ${response.code()}")
                    }
                }

                override fun onFailure(call: Call<CategoriesResponse>, t: Throwable) {
                    _error.postValue("Error: ${t.message}")
                }
            })
        }
    }
}