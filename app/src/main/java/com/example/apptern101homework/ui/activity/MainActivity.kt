package com.example.apptern101homework.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.apptern101homework.R
import com.example.apptern101homework.databinding.ActivityMainBinding
import com.example.apptern101homework.utils.ext.gone
import com.example.apptern101homework.utils.ext.setVisibility
import com.example.apptern101homework.utils.ext.show
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupNavigation()
        ibBackClickListener()
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

    private fun ibBackClickListener() = binding.ibBack.setOnClickListener {
        onBackPressedDispatcher.onBackPressed()
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

            if (destinationId == R.id.articleDetailFragment) {
                layoutToolbar.gone()
            }
        }
    }

    override fun onBackPressed() {
        val currentDestination = navController.currentDestination
        if (currentDestination?.id == R.id.articleDetailFragment || currentDestination?.id == R.id.newsSourceFragment) {
            navController.popBackStack()
        } else {
            super.onBackPressed()
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = this.findNavController(R.id.nav_host_fragment_activity_main)
        return navController.navigateUp()
    }
}