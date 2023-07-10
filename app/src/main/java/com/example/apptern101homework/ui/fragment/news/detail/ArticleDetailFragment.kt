package com.example.apptern101homework.ui.fragment.news.detail

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.apptern101homework.R
import com.example.apptern101homework.base.BaseFragment
import com.example.apptern101homework.databinding.FragmentArticleDetailBinding
import com.example.apptern101homework.domain.uimodel.Article
import com.example.apptern101homework.utils.ext.convertToDayMonthYearFormat
import com.example.apptern101homework.utils.ext.loadImage
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ArticleDetailFragment : BaseFragment<FragmentArticleDetailBinding>() {

    private val args: ArticleDetailFragmentArgs by navArgs()

    private val viewModel: ArticleDetailVM by viewModels()

    private fun getArticle() = args.article

    override fun initialize() {
        prepareUI(getArticle())
        goToSourceScreen(getArticle().url)
        ibShareClickListener()
        ibFavoriteClickListener()
        ibBackClickListener()
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
            tvDescription.text = article.description
            tvAuthorName.text = article.author ?: getString(R.string.no_author)
            tvPublishedAt.text = article.publishedAt?.convertToDayMonthYearFormat()
            ivArticle.loadImage(article.urlToImage)
        }
    }

    private fun ibFavoriteClickListener() = binding?.ibFavorite?.setOnClickListener {
        saveArticle()
    }

    private fun ibBackClickListener() = binding?.ibBack?.setOnClickListener {
        findNavController().navigateUp()
    }

    private fun ibShareClickListener() = binding?.ibShare?.setOnClickListener {
        shareArticle(getArticle().url)
    }

    private fun saveArticle() {
        viewModel.addToFavorites(getArticle())
        showToast(getString(R.string.article_added_to_db))
    }

    private fun shareArticle(url: String?) {
        val shareIntent = Intent(Intent.ACTION_SEND)
        shareIntent.type = "text/plain"
        shareIntent.putExtra(Intent.EXTRA_TEXT, url)
        startActivity(Intent.createChooser(shareIntent, getString(R.string.share_article)))
    }

    private fun goToSourceScreen(newsUrl: String?) = binding?.apply {
        btnGoToSource.setOnClickListener {
            findNavController().navigate(
                ArticleDetailFragmentDirections
                    .actionArticleDetailFragmentToNewsSourceFragment(newsUrl)
            )
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT)
            .show()
    }
}