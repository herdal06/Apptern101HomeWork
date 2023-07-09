package com.example.apptern101homework.data.local.datasource

import com.example.apptern101homework.data.datasource.ArticleDataSource
import com.example.apptern101homework.data.local.dao.ArticleDao
import com.example.apptern101homework.data.local.entity.ArticleEntity
import com.example.apptern101homework.di.IoDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ArticleLocalDataSource @Inject constructor(
    private val articleDao: ArticleDao,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) : ArticleDataSource.Local {
    override suspend fun insert(article: ArticleEntity) {
        withContext(ioDispatcher) {
            articleDao.insert(article)
        }
    }

    override suspend fun delete(article: ArticleEntity) {
        withContext(ioDispatcher) {
            articleDao.delete(article)
        }
    }

    override fun getAll(): Flow<List<ArticleEntity>> {
        return articleDao.getAll().flowOn(ioDispatcher)
    }
}