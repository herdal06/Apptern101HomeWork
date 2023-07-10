package com.example.apptern101homework.data.remote.dto

data class ArticleResponse(
    val articles: List<ArticleDto>?,
    val status: String?,
    val totalResults: Int?
)