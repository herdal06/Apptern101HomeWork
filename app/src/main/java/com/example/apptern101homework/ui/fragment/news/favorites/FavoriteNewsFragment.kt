package com.example.apptern101homework.ui.fragment.news.favorites

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.apptern101homework.base.BaseFragment
import com.example.apptern101homework.databinding.FragmentFavoriteNewsBinding

class FavoriteNewsFragment : BaseFragment<FragmentFavoriteNewsBinding>() {

    override fun initialize() {

    }

    override fun inflateBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): FragmentFavoriteNewsBinding {
        return FragmentFavoriteNewsBinding.inflate(inflater, container, false)
    }
}