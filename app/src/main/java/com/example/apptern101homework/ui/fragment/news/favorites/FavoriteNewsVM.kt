package com.example.apptern101homework.ui.fragment.news.favorites

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.apptern101homework.di.IoDispatcher
import com.example.apptern101homework.domain.repository.ArticleRepository
import com.example.apptern101homework.domain.uimodel.Article
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
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

    private val favArticlesEventChannel = Channel<FavoriteArticlesEvent>()
    val favArticlesEvent = favArticlesEventChannel.receiveAsFlow()

    private var deletedItemId: Int? = null

    fun loadFavoriteArticles() {
        _uiState.value = FavoriteNewsUiState(loading = true)
        viewModelScope.launch {
            try {
                withContext(ioDispatcher) {
                    articleRepository.getAll().collect { articles ->
                        withContext(Dispatchers.Main) {
                            _uiState.value = FavoriteNewsUiState(favoriteNews = articles)
                        }
                    }
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    _uiState.value = FavoriteNewsUiState(error = e.message)
                }
            }
        }
    }

    fun onItemSwiped(article: Article) = viewModelScope.launch {
        articleRepository.delete(article)
        deletedItemId = article.id
        favArticlesEventChannel.send(FavoriteArticlesEvent.ShowUndoDeleteItemMessage(article))
    }

    fun onUndoDeleteClick(article: Article) = viewModelScope.launch(Dispatchers.IO) {
        try {
            if (deletedItemId != null) {
                articleRepository.insert(article.copy(id = deletedItemId!!)) // ID'yi geri yükle
                // listeyi güncelle
                loadFavoriteArticles()
                Log.d("","deletedItemId: $deletedItemId")
                deletedItemId = null // Silinen öğe ID'sini sıfırla
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }


    sealed class FavoriteArticlesEvent {
        data class ShowUndoDeleteItemMessage(val article: Article) : FavoriteArticlesEvent()
    }
}