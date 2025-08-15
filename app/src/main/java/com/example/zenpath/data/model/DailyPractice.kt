    package com.example.zenpath.data.model

    data class DailyPracticeResponse(
        val status: Boolean,
        val message: String,
        val data: List<DailyPractice>
    )

    data class DailyPractice(
        val id: Int,
        val category_id: Int,
        val title: String,
        val description: String,
        val media_type: String,
        val media_url: String,
        val total_length: Double,
        val total_view_count: Int,
        val statusFlag: Int,
        val created_at: String,
        val updated_at: String,
        val created_user: String,
        val updated_user: String,
        val cover_image: String,
        val emotionId: Int,
        val singerId: Int,
        val goalIds: List<Int>,
        val content_type: String,
        val category_name: String,
        val emotion_title: String,
        val emotion_icon: String,
        val singer_name: String,
        val goals: List<Goal>
    )

    data class Goal(
        val id: Int,
        val title: String
    )