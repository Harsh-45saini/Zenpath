    package com.example.zenpath.ui.viewmodel

    import androidx.lifecycle.ViewModel
    import androidx.lifecycle.viewModelScope
    import com.example.zenpath.data.model.CategoryDto
    import com.example.zenpath.data.model.CategoryResponse
    import com.example.zenpath.data.model.DailyPractice
    import com.example.zenpath.data.repository.DailyPracticeRepository
    import kotlinx.coroutines.flow.MutableStateFlow
    import kotlinx.coroutines.flow.StateFlow
    import kotlinx.coroutines.launch
    import retrofit2.awaitResponse

    class DailyPracticeViewModel(
        private val repository: DailyPracticeRepository
    ) : ViewModel() {

        private val _uiState = MutableStateFlow<DailyPracticeUiState>(DailyPracticeUiState.Loading)
        val uiState: StateFlow<DailyPracticeUiState> = _uiState

        private val _categoryState = MutableStateFlow<CategoryUiState>(CategoryUiState.Loading)
        val categoryState: StateFlow<CategoryUiState> = _categoryState

        fun  fetchCategoryDetails(token: String, categoryId: Int) {
            viewModelScope.launch {
                try {
                    val response = repository.getCategoryById(token, categoryId).awaitResponse()
                    if (response.isSuccessful && response.body() != null) {
                        _categoryState.value = CategoryUiState.Success(response.body()!!.data)
                    } else {
                        _categoryState.value = CategoryUiState.Error("Error: ${response.code()}")
                    }
                } catch (e: Exception) {
                    _categoryState.value = CategoryUiState.Error(e.localizedMessage ?: "Unknown error")
                }
            }
        }

        fun fetchDailyPractices(token: String,categoryId: Int) {
            viewModelScope.launch {
                try {
                    val response = repository.getDailyPracticesByCategory(token , categoryId).awaitResponse()
                    if (response.isSuccessful && response.body() != null) {
                        _uiState.value = DailyPracticeUiState.Success(response.body()!!.data)
                    } else {
                        _uiState.value = DailyPracticeUiState.Error("Error: ${response.code()}")
                    }
                } catch (e: Exception) {
                    _uiState.value = DailyPracticeUiState.Error(e.localizedMessage ?: "Unknown error")
                }
            }
        }
    }

    sealed class DailyPracticeUiState {
        object Loading : DailyPracticeUiState()
        data class Success(val data: List<DailyPractice>) : DailyPracticeUiState()
        data class Error(val message: String) : DailyPracticeUiState()
    }

    sealed class CategoryUiState {
        object Loading : CategoryUiState()
        data class Success(val category: CategoryDto) : CategoryUiState()
        data class Error(val message: String) : CategoryUiState()
    }
