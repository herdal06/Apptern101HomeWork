package com.example.apptern101homework.ui.fragment.news.search

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.PagingData
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.apptern101homework.R
import com.example.apptern101homework.base.BaseFragment
import com.example.apptern101homework.base.listener.RvItemClickListener
import com.example.apptern101homework.databinding.FragmentSearchNewsBinding
import com.example.apptern101homework.domain.uimodel.Article
import com.example.apptern101homework.ui.fragment.news.search.adapter.SearchedArticlesAdapter
import com.example.apptern101homework.utils.ext.gone
import com.example.apptern101homework.utils.ext.show
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SearchNewsFragment : BaseFragment<FragmentSearchNewsBinding>() {

    private lateinit var searchArticlesAdapter: SearchedArticlesAdapter
    private val viewModel: SearchNewsVM by viewModels()

    override fun initialize() {
        prepareSearchArticlesAdapter()
        observeSearchInput()
    }

    override fun inflateBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): FragmentSearchNewsBinding {
        return FragmentSearchNewsBinding.inflate(inflater, container, false)
    }

    private fun prepareSearchArticlesAdapter() {
        searchArticlesAdapter = SearchedArticlesAdapter(recyclerViewItemClickListener)
        binding?.rvSearchedNews?.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding?.rvSearchedNews?.adapter = searchArticlesAdapter
    }

    private fun observeSearchInput() = binding?.apply {
        etSearchNews.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val searchQuery = s.toString()
                if (searchQuery.isNotEmpty()) {
                    observeUiState(searchQuery)
                } else {
                    searchArticlesAdapter.submitData(lifecycle, PagingData.empty())
                }
            }

            override fun afterTextChanged(s: Editable?) {
            }
        })
    }

    private fun observeUiState(searchQuery: String) {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.uiState.observe(viewLifecycleOwner) { uiState ->
                uiState.loading.let { isLoading ->
                    if (isLoading) {
                        binding?.pbSearchedNews?.show()
                        binding?.rvSearchedNews?.gone()
                    } else {
                        binding?.pbSearchedNews?.gone()
                        binding?.rvSearchedNews?.show()
                    }
                }

                uiState.error?.let { errorMessage ->
                    Toast.makeText(requireContext(), errorMessage, Toast.LENGTH_SHORT).show()
                }

                uiState.searchedNews?.let { pagingData ->
                    launch {
                        searchArticlesAdapter.submitData(pagingData)
                    }
                }
            }
        }
        viewModel.searchArticles(searchQuery)
    }

    private val recyclerViewItemClickListener = object : RvItemClickListener<Article?> {
        override fun onClick(item: Article?) {
            item?.let { navigateToArticleDetailFragment(it) }
        }

        override fun onLongClick(item: Article?) {
            // expand tvContent on long click item
        }
    }

    private fun navigateToArticleDetailFragment(article: Article) {
        val bundle = Bundle().apply {
            putParcelable("article", article)
        }
        findNavController().navigate(
            R.id.action_searchNewsFragment_to_articleDetailFragment,
            bundle
        )
    }
}