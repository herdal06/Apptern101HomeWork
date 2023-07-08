package com.example.apptern101homework.ui.fragment.news.favorites.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.example.apptern101homework.base.listener.RvItemClickListener
import com.example.apptern101homework.databinding.ItemArticleBinding
import com.example.apptern101homework.domain.uimodel.Article

class FavoriteArticlesAdapter(
    private val articleClickListener: RvItemClickListener<Article?>
) : ListAdapter<Article, FavoriteArticlesViewHolder>(diffCallback) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteArticlesViewHolder {
        return FavoriteArticlesViewHolder(
            ItemArticleBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ), articleClickListener
        )
    }

    override fun onBindViewHolder(holder: FavoriteArticlesViewHolder, position: Int) {
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