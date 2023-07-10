package com.example.apptern101homework.data.datasource

import androidx.paging.PagingData
import com.example.apptern101homework.data.local.entity.ArticleEntity
import com.example.apptern101homework.data.remote.dto.ArticleDto
import kotlinx.coroutines.flow.Flow

interface ArticleDataSource {
    interface Remote {
        fun searchNews(searchQuery: String): Flow<PagingData<ArticleDto>>
    }

    interface Local {
        suspend fun insert(article: ArticleEntity)
        suspend fun delete(article: ArticleEntity)
        fun getAll(): Flow<List<ArticleEntity>>
    }
}