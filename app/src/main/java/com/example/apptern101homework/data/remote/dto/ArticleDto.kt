package com.example.apptern101homework.data.remote.dto

import com.example.apptern101homework.domain.uimodel.Article

data class ArticleDto(
    val author: String?,
    val content: String?,
    val description: String?,
    val publishedAt: String?,
    val title: String?,
    val url: String?,
    val urlToImage: String?
)

fun ArticleDto.toDomain(): Article =
    Article(
        author = author,
        url = url,
        title = title,
        content = content,
        urlToImage = urlToImage,
        description = description,
        publishedAt = publishedAt
    )