package com.example.apptern101homework.ui.fragment.news.favorites

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.apptern101homework.base.BaseFragment
import com.example.apptern101homework.base.listener.RvItemClickListener
import com.example.apptern101homework.databinding.FragmentFavoriteNewsBinding
import com.example.apptern101homework.domain.uimodel.Article
import com.example.apptern101homework.ui.fragment.news.favorites.adapter.FavoriteArticlesAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class FavoriteNewsFragment : BaseFragment<FragmentFavoriteNewsBinding>() {

    private val viewModel: FavoriteNewsVM by viewModels()
    private lateinit var favoriteArticlesAdapter: FavoriteArticlesAdapter
    override fun initialize() {
        prepareFavoriteArticleAdapter()
        observeFavoriteArticles()
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
    }

    private fun observeFavoriteArticles() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.favoriteArticles.observe(viewLifecycleOwner) { articles ->
                    favoriteArticlesAdapter.submitList(articles)
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