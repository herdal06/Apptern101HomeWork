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

    override fun onBindViewHolder(
        holder: FavoriteArticlesViewHolder,
        position: Int,
        payloads: MutableList<Any>
    ) {
        if(payloads.isNotEmpty()) {
            if(payloads[0] == EXPANDED_PAYLOAD) {
                holder.bindExpanded(getItem(position))
            }
        } else {
            super.onBindViewHolder(holder, position, payloads)
        }
    }


    companion object {
        private val diffCallback = object : DiffUtil.ItemCallback<Article>() {
            override fun areItemsTheSame(
                oldItem: Article,
                newItem: Article
            ): Boolean { // tekrar oncreate
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(
                oldItem: Article,
                newItem: Article
            ): Boolean { // view kalır. onbind eder.
                return oldItem == newItem
            }

            override fun getChangePayload(
                oldItem: Article,
                newItem: Article
            ): Any? { // expand animation
                return if(oldItem.isExpanded != newItem.isExpanded)
                    EXPANDED_PAYLOAD else super.getChangePayload(oldItem, newItem)
            }
        }
        const val EXPANDED_PAYLOAD = 1
    }
}