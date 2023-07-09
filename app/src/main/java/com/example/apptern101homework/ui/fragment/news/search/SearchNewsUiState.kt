package com.example.apptern101homework.ui.fragment.news.search

import androidx.paging.PagingData
import com.example.apptern101homework.domain.uimodel.Article

data class SearchNewsUiState(
    val error: String? = null,
    val loading: Boolean = false,
    val searchedNews: PagingData<Article>? = null
)