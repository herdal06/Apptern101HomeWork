package com.example.apptern101homework.ui.activity

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.apptern101homework.domain.repository.ArticleRepository
import com.example.apptern101homework.domain.uimodel.Article
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainVM @Inject constructor(
    private val articleRepository: ArticleRepository
) : ViewModel() {

    fun addToFavorites(article: Article) = viewModelScope.launch {
        try {
            articleRepository.insert(article)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}