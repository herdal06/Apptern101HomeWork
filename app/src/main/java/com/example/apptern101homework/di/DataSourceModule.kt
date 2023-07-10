package com.example.apptern101homework.di

import com.example.apptern101homework.data.datasource.ArticleDataSource
import com.example.apptern101homework.data.local.datasource.ArticleLocalDataSource
import com.example.apptern101homework.data.remote.datasource.ArticleRemoteDataSource
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@[Module InstallIn(SingletonComponent::class)]
abstract class DataSourceModule {

    @Binds
    abstract fun bindArticleRemoteDataSource(articleRemoteDataSource: ArticleRemoteDataSource): ArticleDataSource.Remote

    @Binds
    abstract fun bindArticleLocalDataSource(articleLocalDataSource: ArticleLocalDataSource): ArticleDataSource.Local
}