package com.example.apptern101homework.ui.fragment.news.detail

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.apptern101homework.base.BaseFragment
import com.example.apptern101homework.base.listener.FavoriteButtonClickListener
import com.example.apptern101homework.databinding.FragmentArticleDetailBinding
import com.example.apptern101homework.domain.uimodel.Article
import com.example.apptern101homework.utils.ext.loadImage
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ArticleDetailFragment : BaseFragment<FragmentArticleDetailBinding>(), FavoriteButtonClickListener {

    private val args: ArticleDetailFragmentArgs by navArgs()

    private val viewModel: ArticleDetailVM by viewModels()

    private lateinit var favoriteButtonClickListener: FavoriteButtonClickListener

    fun getArticle() = args.article

    override fun onAttach(context: Context) {
        super.onAttach(context)
        favoriteButtonClickListener = context as FavoriteButtonClickListener
    }

    override fun initialize() {
        prepareUI(getArticle())
        goToSourceScreen(getArticle().url)

        binding?.ibShare?.setOnClickListener {
            shareArticle(getArticle().url)
        }

        binding?.ibFavorite?.setOnClickListener {
            favoriteButtonClickListener.onFavoriteButtonClicked(getArticle())
        }
    }

    override fun inflateBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): FragmentArticleDetailBinding {
        return FragmentArticleDetailBinding.inflate(inflater, container, false)
    }

    private fun prepareUI(article: Article?) = binding?.apply {
        article?.let {
            tvArticleTitle.text = article.title
            ivArticle.loadImage(article.urlToImage)
        }
    }

    private fun shareArticle(url: String?) {
        val shareIntent = Intent(Intent.ACTION_SEND)
        shareIntent.type = "text/plain"
        shareIntent.putExtra(Intent.EXTRA_TEXT, url)
        startActivity(Intent.createChooser(shareIntent, "Makaleyi Paylaş"))
    }

    private fun goToSourceScreen(newsUrl: String?) = binding?.apply {
        btnGoToSource.setOnClickListener {
            findNavController().navigate(
                ArticleDetailFragmentDirections
                    .actionArticleDetailFragmentToNewsSourceFragment(newsUrl)
            )
        }
    }

    override fun onFavoriteButtonClicked(article: Article?) {
        favoriteButtonClickListener.onFavoriteButtonClicked(article)
    }
}