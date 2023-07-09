package com.example.apptern101homework.ui.fragment.news.favorites

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.apptern101homework.di.IoDispatcher
import com.example.apptern101homework.domain.repository.ArticleRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class FavoriteNewsVM @Inject constructor(
    private val articleRepository: ArticleRepository,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) : ViewModel() {

    private var _uiState = MutableLiveData<FavoriteNewsUiState>()
    val uiState: LiveData<FavoriteNewsUiState> get() = _uiState

    init {
        loadFavoriteArticles()
    }

    private fun loadFavoriteArticles() {
        viewModelScope.launch {
            _uiState.value = FavoriteNewsUiState(loading = true)

            try {
                withContext(ioDispatcher) {
                    val articles = articleRepository.getAll().firstOrNull()
                    _uiState.postValue(FavoriteNewsUiState(favoriteNews = articles))
                }
            } catch (e: Exception) {
                _uiState.postValue(FavoriteNewsUiState(error = e.message))
            }
        }
    }
}