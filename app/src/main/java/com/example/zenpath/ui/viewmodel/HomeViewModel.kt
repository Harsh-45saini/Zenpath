package com.example.zenpath.ui.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.zenpath.data.local.PrefManager
import com.example.zenpath.data.model.Category
import com.example.zenpath.data.model.DashboardData
import com.example.zenpath.data.model.DashboardResponse
import com.example.zenpath.data.repository.DashboardRepository
import com.example.zenpath.utils.NetworkConnectivityObserver
import com.example.zenpath.utils.NetworkStatus
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeViewModel(
    application: Application
) : AndroidViewModel(application) {

    private val repository = DashboardRepository()
    private val connectivityObserver = NetworkConnectivityObserver(application)

    private val _categories = MutableStateFlow<List<Category>>(emptyList())
    val categories: StateFlow<List<Category>> = _categories

    private val _dashboardData = MutableStateFlow<DashboardData?>(null)
    val dashboardData: StateFlow<DashboardData?> = _dashboardData

    private val _firstName = MutableStateFlow("User")
    val firstName: StateFlow<String> = _firstName

    private val _isConnected = MutableStateFlow(true)
    val isConnected: StateFlow<Boolean> = _isConnected

    private val _navigateTo = MutableSharedFlow<String>()
    val navigateTo: SharedFlow<String> = _navigateTo

    init {
        observeNetwork()
    }

    fun loadDashboard(prefManager: PrefManager) {
        val token = prefManager.getToken()
        Log.d("loadDashboard", "Token used: $token")

        repository.getDashboardData(token).enqueue(object : Callback<DashboardResponse> {
            override fun onResponse(call: Call<DashboardResponse>, response: Response<DashboardResponse>) {
                if (response.isSuccessful) {
                    _dashboardData.value = response.body()?.data

                    val categories = response.body()?.data?.latestCategories ?: emptyList()
                    _categories.value = categories
                    Log.d("loadDashboard", "Dashboard and ${categories.size} categories loaded")
                } else {
                    Log.e("loadDashboard", "API error: ${response.code()} - ${response.message()}")
                }
            }

            override fun onFailure(call: Call<DashboardResponse>, t: Throwable) {
                Log.e("loadDashboard", "Error fetching dashboard", t)
            }
        })
    }

    fun loadFirstName(prefManager: PrefManager) {
        viewModelScope.launch {
            val fullName = prefManager.getPrefValue(PrefManager.UserName).orEmpty()
            val name = fullName.split(" ").firstOrNull().orEmpty()
            _firstName.value = name
            Log.d("HomeViewModel", "First name loaded: $name")
        }
    }

    private fun observeNetwork() {
        viewModelScope.launch {
            connectivityObserver.networkStatus.collect { status ->
                _isConnected.value = status == NetworkStatus.Available
                Log.d("NetworkStatus", "Connected: ${_isConnected.value}")
            }
        }
    }

    fun onExploreMoreClicked() {
        viewModelScope.launch {
            _navigateTo.emit("most_popular")
        }
    }
}
