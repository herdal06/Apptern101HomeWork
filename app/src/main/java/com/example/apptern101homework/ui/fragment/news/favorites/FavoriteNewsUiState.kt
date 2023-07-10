package com.example.apptern101homework.ui.fragment.news.favorites

import com.example.apptern101homework.domain.uimodel.Article

data class FavoriteNewsUiState(
    val error: String? = null,
    val loading: Boolean = false,
    val favoriteNews: List<Article>? = null
)