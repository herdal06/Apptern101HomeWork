package com.example.apptern101homework.data.remote.service

import com.example.apptern101homework.BuildConfig
import com.example.apptern101homework.data.remote.dto.ArticleResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ArticleService {

    @GET("v2/everything")
    suspend fun searchNews(
        @Query("q") searchQuery: String,
        @Query("apiKey") apiKey: String = BuildConfig.API_KEY,
        @Query("page") page: Int,
        @Query("pageSize") pageSize: Int = 20,
        @Query("language") language: String = "tr"
    ): ArticleResponse?
}