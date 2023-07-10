package com.example.apptern101homework.ui.fragment.news.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.example.apptern101homework.di.IoDispatcher
import com.example.apptern101homework.domain.repository.ArticleRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class SearchNewsVM @Inject constructor(
    private val articleRepository: ArticleRepository,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) : ViewModel() {

    private var _uiState = MutableLiveData<SearchNewsUiState>()
    val uiState: LiveData<SearchNewsUiState> get() = _uiState

    fun searchArticles(searchQuery: String) = viewModelScope.launch {
        _uiState.value = SearchNewsUiState(loading = true)

        try {
            withContext(ioDispatcher) {
                articleRepository.searchNews(searchQuery)
                    .cachedIn(viewModelScope)
                    .collectLatest { articles ->
                        withContext(Dispatchers.Main) {
                            _uiState.value = SearchNewsUiState(searchedNews = articles)
                        }
                    }
            }
        } catch (e: Exception) {
            withContext(Dispatchers.Main) {
                _uiState.value = SearchNewsUiState(error = e.message)
            }
        }
    }
}