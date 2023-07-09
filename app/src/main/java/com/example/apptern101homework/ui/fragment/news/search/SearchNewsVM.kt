package com.example.apptern101homework.ui.fragment.news.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.example.apptern101homework.domain.repository.ArticleRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchNewsVM @Inject constructor(
    private val articleRepository: ArticleRepository
) : ViewModel() {

    private var _uiState = MutableLiveData<SearchNewsUiState>()
    val uiState: LiveData<SearchNewsUiState> get() = _uiState

    fun searchArticles(searchQuery: String) = viewModelScope.launch {
        _uiState.value = SearchNewsUiState(loading = true)

        try {
            articleRepository.searchNews(searchQuery)
                .cachedIn(viewModelScope)
                .collect { articles ->
                    _uiState.postValue(SearchNewsUiState(searchedNews = articles))
                }
        } catch (e: Exception) {
            _uiState.postValue(SearchNewsUiState(error = e.message))
        }
    }
}