package com.example.apptern101homework.ui.fragment.news.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.apptern101homework.base.BaseFragment
import com.example.apptern101homework.databinding.FragmentSearchNewsBinding

class SearchNewsFragment : BaseFragment<FragmentSearchNewsBinding>() {

    override fun initialize() {

    }

    override fun inflateBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): FragmentSearchNewsBinding {
        return FragmentSearchNewsBinding.inflate(inflater, container, false)
    }
}