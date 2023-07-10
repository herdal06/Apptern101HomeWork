package com.example.apptern101homework.ui.fragment.news.search.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import com.example.apptern101homework.base.listener.RvItemClickListener
import com.example.apptern101homework.databinding.ItemArticleBinding
import com.example.apptern101homework.domain.uimodel.Article

class SearchedArticlesAdapter(
    private val articleClickListener: RvItemClickListener<Article?>
) : PagingDataAdapter<Article, SearchedArticlesViewHolder>(diffCallback) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchedArticlesViewHolder {
        return SearchedArticlesViewHolder(
            ItemArticleBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ), articleClickListener
        )
    }

    override fun onBindViewHolder(holder: SearchedArticlesViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it) }
    }

    companion object {
        private val diffCallback = object : DiffUtil.ItemCallback<Article>() {
            override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {
                return oldItem.author == newItem.author
            }

            override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean {
                return oldItem == newItem
            }
        }
    }
}