package com.example.zenpath.data.model

data class CategoriesResponse(
    val status: Boolean,
    val message: String,
    val data: List<Categories>
)

data class Categories(
    val id: Int,
    val name: String,
    val icon: String, // URL string from API
    val detail_image: String,
    val detail_title: String,
    val detail_description: String
    // add other fields as needed
)
