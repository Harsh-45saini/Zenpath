package com.example.zenpath.ui.viewmodel
import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.zenpath.ui.viewmodel.MostPopularViewModel

class MostPopularViewModelFactory(private val context: Context) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MostPopularViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MostPopularViewModel(context) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
