package com.example.apptern101homework.di

import com.example.apptern101homework.data.repository.ArticleRepositoryImpl
import com.example.apptern101homework.domain.repository.ArticleRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@[Module InstallIn(SingletonComponent::class)]
abstract class RepositoryModule {

    @Binds
    abstract fun bindArticleRepository(articleRepositoryImpl: ArticleRepositoryImpl): ArticleRepository
}