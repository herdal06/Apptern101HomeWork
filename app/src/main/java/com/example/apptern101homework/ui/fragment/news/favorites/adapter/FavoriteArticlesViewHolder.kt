package com.example.apptern101homework.ui.fragment.news.favorites.adapter

import androidx.recyclerview.widget.RecyclerView
import com.example.apptern101homework.base.listener.RvItemClickListener
import com.example.apptern101homework.databinding.ItemArticleBinding
import com.example.apptern101homework.domain.uimodel.Article
import com.example.apptern101homework.utils.ext.loadImage

class FavoriteArticlesViewHolder(
    private val binding: ItemArticleBinding,
    private val articleClickListener: RvItemClickListener<Article?>
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(article: Article) = binding.apply {
        tvTitle.text = article.title
        tvContent.text = article.content
        ivArticle.loadImage(article.urlToImage)

        root.setOnClickListener {
            articleClickListener.onClick(article)
        }

        root.setOnLongClickListener {
            articleClickListener.onLongClick(article)
            true
        }
    }

    fun bindExpanded(article: Article) {
        binding.tvContent.maxLines = if (article.isExpanded) 10 else 2
    }
}