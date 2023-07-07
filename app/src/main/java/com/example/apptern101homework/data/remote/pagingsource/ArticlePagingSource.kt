package com.example.apptern101homework.data.remote.pagingsource

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.apptern101homework.data.remote.dto.ArticleDto
import com.example.apptern101homework.data.remote.service.ArticleService

class ArticlePagingSource(
    private val articleService: ArticleService,
    private val searchQuery: String
) : PagingSource<Int, ArticleDto>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ArticleDto> {
        val page = params.key ?: 1
        return try {
            val response = articleService.searchNews(searchQuery, page = page)
            val articles = response?.articles

            LoadResult.Page(
                data = articles!!, // ?
                prevKey = if (page == 1) null else page - 1,
                nextKey = if (articles.isEmpty()) null else page + 1
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, ArticleDto>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }
}