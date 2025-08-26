package com.example.zenpath.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.zenpath.data.repository.DailyPracticeRepository

class DailyPracticeViewModelFactory(
    private val repository: DailyPracticeRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DailyPracticeViewModel::class.java)) {
            return DailyPracticeViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
