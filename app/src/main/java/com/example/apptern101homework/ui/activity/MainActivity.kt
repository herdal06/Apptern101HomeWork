package com.example.apptern101homework.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.apptern101homework.R
import com.example.apptern101homework.base.listener.FavoriteButtonClickListener
import com.example.apptern101homework.databinding.ActivityMainBinding
import com.example.apptern101homework.domain.uimodel.Article
import com.example.apptern101homework.ui.fragment.news.detail.ArticleDetailFragment
import com.example.apptern101homework.utils.ext.gone
import com.example.apptern101homework.utils.ext.setVisibility
import com.example.apptern101homework.utils.ext.show
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), FavoriteButtonClickListener {
    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController

    private var favoriteButtonClickListener: FavoriteButtonClickListener? = null
    private var currentArticle: Article? = null

    private val viewModel: MainVM by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupNavigation()
        setupClickListeners()
    }

    override fun onResume() {
        super.onResume()
        setFavoriteButtonClickListener()
    }

    override fun onFavoriteButtonClicked(article: Article?) {
        article?.let { viewModel.addToFavorites(it) }
        Toast.makeText(this, "Article added to favorites!", Toast.LENGTH_SHORT).show()
    }

    private fun setFavoriteButtonClickListener() {
        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        val currentFragment = navController.currentDestination?.id

        if (currentFragment == R.id.articleDetailFragment) {
            val fragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment_activity_main)?.childFragmentManager?.fragments?.firstOrNull()
            if (fragment is ArticleDetailFragment) {
                favoriteButtonClickListener = fragment
                currentArticle = fragment.getArticle()
            }
        } else {
            favoriteButtonClickListener = null
            currentArticle = null
        }
    }

    private fun setupNavigation() {
        val navView: BottomNavigationView = binding.navView

        navController = findNavController(R.id.nav_host_fragment_activity_main)
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.searchNewsFragment, R.id.favoriteNewsFragment
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        val noBottomNavigationIds = listOf(R.id.articleDetailFragment, R.id.newsSourceFragment)
        navController.addOnDestinationChangedListener { _, destination, _ ->
            if (noBottomNavigationIds.contains(destination.id)) {
                navView.gone()
            } else {
                navView.show()
            }
            updateToolbar(destination.id)
        }
    }

    private fun setupClickListeners() {
        binding.ibBack.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

        binding.ibFavorite.setOnClickListener {
            currentArticle?.let { article ->
                favoriteButtonClickListener?.onFavoriteButtonClicked(article)
            }
        }
    }

    private fun updateToolbar(destinationId: Int) {
        binding.apply {
            supportActionBar?.setDisplayHomeAsUpEnabled(destinationId != R.id.searchNewsFragment)
            tvScreenTitle.text = when (destinationId) {
                R.id.searchNewsFragment -> getString(R.string.title_search_screen)
                R.id.favoriteNewsFragment -> getString(R.string.title_favorites_screen)
                R.id.articleDetailFragment -> ""
                R.id.newsSourceFragment -> getString(R.string.title_news_source_screen)
                else -> ""
            }

            ibBack.setVisibility(destinationId == R.id.articleDetailFragment || destinationId == R.id.newsSourceFragment)
            ibShare.setVisibility(destinationId == R.id.articleDetailFragment)
            ibFavorite.setVisibility(destinationId == R.id.articleDetailFragment)
        }
    }

    override fun onBackPressed() {
        val currentDestination = navController.currentDestination
        if (currentDestination?.id == R.id.articleDetailFragment || currentDestination?.id == R.id.newsSourceFragment) {
            navController.popBackStack()
        } else {
            super.onBackPressed()
        }
        setFavoriteButtonClickListener()
    }

    override fun onSupportNavigateUp(): Boolean {
        setFavoriteButtonClickListener()
        val navController = this.findNavController(R.id.nav_host_fragment_activity_main)
        return navController.navigateUp()
    }
}