package com.example.zenpath.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.zenpath.data.repository.CategoriesRepository
import com.example.zenpath.data.local.PrefManager

class CategoriesViewModelFactory(
    private val repository: CategoriesRepository,
    private val prefManager: PrefManager
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CategoriesViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return CategoriesViewModel(repository, prefManager) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
