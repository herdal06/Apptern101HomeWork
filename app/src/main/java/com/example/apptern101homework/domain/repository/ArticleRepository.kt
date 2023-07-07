package com.example.apptern101homework.domain.repository

import androidx.paging.PagingData
import com.example.apptern101homework.domain.uimodel.Article
import kotlinx.coroutines.flow.Flow

interface ArticleRepository {
    fun searchNews(searchQuery: String): Flow<PagingData<Article>>
}