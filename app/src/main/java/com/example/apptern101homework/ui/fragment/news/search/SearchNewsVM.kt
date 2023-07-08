package com.example.apptern101homework.ui.fragment.news.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.apptern101homework.domain.repository.ArticleRepository
import com.example.apptern101homework.domain.uimodel.Article
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchNewsVM @Inject constructor(
    private val articleRepository: ArticleRepository
) : ViewModel() {

    private var _searchedArticles = MutableLiveData<PagingData<Article>?>()
    val searchedArticles: LiveData<PagingData<Article>?> get() = _searchedArticles

    fun searchArticles(searchQuery: String) = viewModelScope.launch {
        articleRepository.searchNews(searchQuery)
            .cachedIn(viewModelScope)
            .collect {
                _searchedArticles.postValue(it)
            }
    }
}