package com.example.apptern101homework.ui.fragment.news.source

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.webkit.WebViewClient
import androidx.navigation.fragment.navArgs
import com.example.apptern101homework.base.BaseFragment
import com.example.apptern101homework.databinding.FragmentNewsSourceBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NewsSourceFragment : BaseFragment<FragmentNewsSourceBinding>() {

    private val args: NewsSourceFragmentArgs by navArgs()
    private fun getNewsUrl() = args.newsUrl

    override fun initialize() {
        setupWebView()
    }

    override fun inflateBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): FragmentNewsSourceBinding {
        return FragmentNewsSourceBinding.inflate(inflater, container, false)
    }

    private fun setupWebView() = binding?.apply {
        webViewNewsSource.apply {
            webViewClient = WebViewClient()
            getNewsUrl()?.let { loadUrl(it) }
        }
    }
}