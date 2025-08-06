package com.example.zenpath.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.zenpath.data.local.PrefManager

class ProfileViewModel(application: Application) : AndroidViewModel(application) {

    private val prefManager = PrefManager(application)

    private val _userFullName = MutableLiveData<String>()
    val userFullName: LiveData<String> = _userFullName

    private val _userEmail = MutableLiveData<String>()
    val userEmail: LiveData<String> = _userEmail

    init {
        // Load user info from shared prefs
        _userFullName.value = prefManager.getUserName() // e.g., "Ashiqur Rehman"
        _userEmail.value = prefManager.getUserEmail()   // e.g., "ashikxql@gmail.com"
    }
}
