package com.example.apptern101homework.base.listener

import com.example.apptern101homework.domain.uimodel.Article

interface FavoriteButtonClickListener {
    fun onFavoriteButtonClicked(article: Article?)
}