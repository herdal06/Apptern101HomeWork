package com.example.apptern101homework.domain.uimodel

import android.os.Parcelable
import com.example.apptern101homework.data.local.entity.ArticleEntity
import kotlinx.parcelize.Parcelize

@Parcelize
data class Article(
    val author: String?,
    val url: String?,
    val title: String?,
    val content: String?,
    val urlToImage: String?,
    val description: String?,
    val publishedAt: String?,
) : Parcelable

fun Article.toEntity(): ArticleEntity =
    ArticleEntity(
        author = author,
        url = url,
        title = title,
        content = content,
        urlToImage = urlToImage,
        description = description,
        publishedAt = publishedAt,
    )
