package com.example.apptern101homework.ui.fragment.news.search

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.apptern101homework.R
import com.example.apptern101homework.base.BaseFragment
import com.example.apptern101homework.base.listener.RvItemClickListener
import com.example.apptern101homework.databinding.FragmentSearchNewsBinding
import com.example.apptern101homework.domain.uimodel.Article
import com.example.apptern101homework.ui.fragment.news.search.adapter.SearchedArticlesAdapter
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
                observeSearchedNews(searchQuery)
            }

            override fun afterTextChanged(s: Editable?) {
            }
        })
    }

    private fun observeSearchedNews(searchQuery: String) {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.searchedArticles.observe(viewLifecycleOwner) { pagingData ->
                    pagingData?.let {
                        launch {
                            searchArticlesAdapter.submitData(pagingData)
                        }
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