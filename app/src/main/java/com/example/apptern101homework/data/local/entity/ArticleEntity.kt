package com.example.apptern101homework.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.apptern101homework.domain.uimodel.Article

@Entity(tableName = "articles")
data class ArticleEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val urlToImage: String?,
    val title: String?,
    val author: String?,
    val content: String?,
    val description: String?,
    val publishedAt: String?,
    val url: String?,
)

fun ArticleEntity.toDomain(): Article =
    Article(
        author = author,
        url = url,
        title = title,
        content = content,
        urlToImage = urlToImage,
        description = description,
        publishedAt = publishedAt,
    )