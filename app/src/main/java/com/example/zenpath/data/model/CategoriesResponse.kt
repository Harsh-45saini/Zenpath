package com.example.zenpath.data.model

// For list response (/api/categories)
data class CategoriesResponse(
    val status: Boolean,
    val message: String,
    val data: List<CategoryDto>
)

// For single category response (/api/categories/{id})
data class CategoryResponse(
    val status: Boolean,
    val message: String,
    val data: CategoryDto
)

// Category object itself
data class CategoryDto(
    val id: Int,
    val name: String,
    val icon: String,
    val detail_image: String? = null,
    val detail_title: String? = null,
    val detail_description: String? = null
)