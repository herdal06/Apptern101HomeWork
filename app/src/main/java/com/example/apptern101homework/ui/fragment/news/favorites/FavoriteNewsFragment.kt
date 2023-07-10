package com.example.apptern101homework.ui.fragment.news.favorites

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.apptern101homework.R
import com.example.apptern101homework.base.BaseFragment
import com.example.apptern101homework.base.listener.RvItemClickListener
import com.example.apptern101homework.databinding.FragmentFavoriteNewsBinding
import com.example.apptern101homework.domain.uimodel.Article
import com.example.apptern101homework.ui.fragment.news.favorites.adapter.FavoriteArticlesAdapter
import com.example.apptern101homework.utils.ext.gone
import com.example.apptern101homework.utils.ext.show
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class FavoriteNewsFragment : BaseFragment<FragmentFavoriteNewsBinding>() {

    private val viewModel: FavoriteNewsVM by viewModels()
    private lateinit var favoriteArticlesAdapter: FavoriteArticlesAdapter
    override fun initialize() {
        prepareFavoriteArticleAdapter()
        observeUiState()
        swipeToDelete()

    }

    override fun inflateBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): FragmentFavoriteNewsBinding {
        return FragmentFavoriteNewsBinding.inflate(inflater, container, false)
    }

    private fun prepareFavoriteArticleAdapter() {
        favoriteArticlesAdapter = FavoriteArticlesAdapter(recyclerViewItemClickListener)
        binding?.rvFavoriteNews?.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding?.rvFavoriteNews?.adapter = favoriteArticlesAdapter
    }

    private val recyclerViewItemClickListener = object :
        RvItemClickListener<Article?> {
        override fun onClick(item: Article?) {
            item?.let { navigateToArticleDetailFragment(it) }
        }

        override fun onLongClick(item: Article?) {
            val newList = favoriteArticlesAdapter.currentList.map {
                if (item?.id == it.id) {
                    it.copy(isExpanded = !it.isExpanded)
                } else {
                    it
                }
            }
            favoriteArticlesAdapter.submitList(newList)
        }
    }

    private fun observeUiState() {
        viewModel.loadFavoriteArticles()
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.observe(viewLifecycleOwner) { uiState ->
                    uiState.loading.let { isLoading ->
                        if (isLoading) {
                            binding?.pbFavNews?.show()
                        } else {
                            binding?.pbFavNews?.gone()
                        }
                    }

                    uiState.error?.let { errorMessage ->
                        Toast.makeText(requireContext(), errorMessage, Toast.LENGTH_SHORT).show()
                    }

                    uiState.favoriteNews?.let { articles ->
                        favoriteArticlesAdapter.submitList(articles)
                    }
                }
            }
        }
        collectEvents()
    }

    private fun swipeToDelete() {
        ItemTouchHelper(object :
            ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.bindingAdapterPosition
                val item = favoriteArticlesAdapter.currentList[position]
                viewModel.onItemSwiped(item)
            }
        }).attachToRecyclerView(binding?.rvFavoriteNews)
    }

    private fun collectEvents() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.favArticlesEvent.collectLatest { event ->
                    when (event) {
                        is FavoriteNewsVM.FavoriteArticlesEvent.ShowUndoDeleteItemMessage -> {
                            Snackbar.make(
                                requireView(),
                                getString(R.string.article_deleted),
                                Snackbar.LENGTH_LONG
                            )
                                .setAction(getString(R.string.undo)) {
                                    viewModel.onUndoDeleteClick(event.article)
                                }.show()
                        }
                    }
                }
            }
        }
    }

    private fun navigateToArticleDetailFragment(article: Article) {
        findNavController().navigate(
            FavoriteNewsFragmentDirections
                .actionFavoriteNewsFragmentToArticleDetailFragment(article)
        )
    }
}