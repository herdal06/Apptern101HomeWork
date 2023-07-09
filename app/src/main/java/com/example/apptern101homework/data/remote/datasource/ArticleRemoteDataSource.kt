package com.example.apptern101homework.data.remote.datasource

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.apptern101homework.data.datasource.ArticleDataSource
import com.example.apptern101homework.data.remote.dto.ArticleDto
import com.example.apptern101homework.data.remote.pagingsource.ArticlePagingSource
import com.example.apptern101homework.data.remote.service.ArticleService
import com.example.apptern101homework.di.IoDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class ArticleRemoteDataSource @Inject constructor(
    private val articleService: ArticleService,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) : ArticleDataSource.Remote {
    override fun searchNews(searchQuery: String): Flow<PagingData<ArticleDto>> {
        return Pager(
            config = PagingConfig(
                pageSize = 10,
                prefetchDistance = 2,
                maxSize = PagingConfig.MAX_SIZE_UNBOUNDED,
                jumpThreshold = Int.MIN_VALUE,
                enablePlaceholders = true
            ),
            pagingSourceFactory = { ArticlePagingSource(articleService, searchQuery) }
        ).flow
            .flowOn(ioDispatcher)
    }
}