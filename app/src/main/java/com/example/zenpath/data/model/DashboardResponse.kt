package com.example.zenpath.data.model

data class DashboardResponse(
    val status: Boolean,
    val message: String,
    val data: DashboardData
)

data class DashboardData(
    val latestCategories: List<Category>,
    val latestPractice: Practice,
    val topPractices: List<Practice>
)

data class Category(
    val id: Int,
    val name: String,
    val icon: String,
    val detail_image: String,
    val detail_title: String,
    val detail_description: String
)

data class Practice(
    val id: Int,
    val title: String,
    val description: String,
    val coverImage: String,
    val total_view_count: Int? = null // Optional, not present in latestPractice
)
