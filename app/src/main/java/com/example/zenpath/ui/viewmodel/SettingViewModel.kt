package com.example.zenpath.ui.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import com.example.zenpath.data.local.PrefManager

class SettingViewModel : ViewModel() {

    fun logout(context: Context, onLogoutSuccess: () -> Unit) {
        val prefManager = PrefManager(context)
        prefManager.deletePrefData()
        onLogoutSuccess() // callback to navigate to Auth screen
    }
}
