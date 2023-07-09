package com.example.apptern101homework.data.repository

import androidx.paging.PagingData
import androidx.paging.map
import com.example.apptern101homework.data.datasource.ArticleDataSource
import com.example.apptern101homework.data.local.entity.toDomain
import com.example.apptern101homework.data.remote.dto.toDomain
import com.example.apptern101homework.domain.repository.ArticleRepository
import com.example.apptern101homework.domain.uimodel.Article
import com.example.apptern101homework.domain.uimodel.toEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ArticleRepositoryImpl @Inject constructor(
    private val remote: ArticleDataSource.Remote,
    private val local: ArticleDataSource.Local
) : ArticleRepository {
    override fun searchNews(searchQuery: String): Flow<PagingData<Article>> {
        return remote.searchNews(searchQuery).map { pagingData ->
            pagingData.map {
                it.toDomain()
            }
        }
    }

    override suspend fun insert(article: Article) {
        local.insert(article.toEntity())
    }

    override suspend fun delete(article: Article) {
        local.insert(article.toEntity())
    }

    override fun getAll(): Flow<List<Article>> {
        return local.getAll().map { articles ->
            articles.map { it.toDomain() }
        }
    }
}
