package com.example.apptern101homework.ui.fragment.news.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.apptern101homework.domain.repository.ArticleRepository
import com.example.apptern101homework.domain.uimodel.Article
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ArticleDetailVM @Inject constructor(
    private val articleRepository: ArticleRepository
) : ViewModel() {

    fun addToFavorites(article: Article?) = viewModelScope.launch {
        try {
            article?.let { articleRepository.insert(it) }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}