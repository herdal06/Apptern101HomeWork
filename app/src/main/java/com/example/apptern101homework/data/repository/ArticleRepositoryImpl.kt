package com.example.apptern101homework.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.example.apptern101homework.data.remote.dto.toDomain
import com.example.apptern101homework.data.remote.pagingsource.ArticlePagingSource
import com.example.apptern101homework.data.remote.service.ArticleService
import com.example.apptern101homework.domain.repository.ArticleRepository
import com.example.apptern101homework.domain.uimodel.Article
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ArticleRepositoryImpl @Inject constructor(
    private val articleService: ArticleService,
) : ArticleRepository {
    override fun searchNews(searchQuery: String): Flow<PagingData<Article>> {
        return Pager(
            config = PagingConfig(
                pageSize = 10,
                prefetchDistance = 2,
                maxSize = PagingConfig.MAX_SIZE_UNBOUNDED,
                jumpThreshold = Int.MIN_VALUE,
                enablePlaceholders = true
            ),
            pagingSourceFactory = { ArticlePagingSource(articleService, searchQuery) }
        ).flow.map { pagingData ->
            pagingData.map {
                it.toDomain()
            }
        }
    }
}