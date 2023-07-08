package com.example.apptern101homework.ui.fragment.news.favorites

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.apptern101homework.domain.repository.ArticleRepository
import com.example.apptern101homework.domain.uimodel.Article
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoriteNewsVM @Inject constructor(
    private val articleRepository: ArticleRepository
) : ViewModel() {

    private var _favoriteArticles = MutableLiveData<List<Article>?>()
    val favoriteArticles: LiveData<List<Article>?> get() = _favoriteArticles

    init {
        loadFavoriteArticles()
    }

    private fun loadFavoriteArticles() {
        viewModelScope.launch {
            articleRepository.getAll().collect { articles ->
                _favoriteArticles.postValue(articles)
            }
        }
    }
}
