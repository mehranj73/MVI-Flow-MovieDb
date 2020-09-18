package com.mehranj73.moviedb.ui

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import com.bumptech.glide.RequestManager
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.mehranj73.moviedb.R
import com.mehranj73.moviedb.ui.movie.MovieDetailFragment
import com.mehranj73.moviedb.ui.tv_show.TvDetailFragment
import com.mehranj73.moviedb.ui.tv_show.TvEpisodesFragment
import com.mehranj73.moviedb.util.BottomNavController
import com.mehranj73.moviedb.util.BottomNavController.*
import com.mehranj73.moviedb.util.setUpNavigation
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : BaseActivity(),
    NavGraphProvider,
    OnNavigationGraphChanged,
    OnNavigationReselectedListener {

    @Inject
    lateinit var requestManager: RequestManager

    private lateinit var bottomNavigationView: BottomNavigationView

    private val bottomNavController: BottomNavController by lazy(LazyThreadSafetyMode.NONE) {
        BottomNavController(
            this,
            R.id.main_fragments_container,
            R.id.menu_movie,
            this,
            this
        )

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(tool_bar)

        bottomNavigationView = findViewById(R.id.bottom_navigation_view)
        bottomNavigationView.setUpNavigation(bottomNavController, this)
        if (savedInstanceState == null) {
            bottomNavController.setupBottomNavigationBackStack(null)
            bottomNavController.onNavigationItemSelected()
        }
    }


    override fun getNavGraphId(itemId: Int): Int = when (itemId) {
        R.id.menu_movie -> {
            R.navigation.nav_movie
        }
        R.id.menu_tv_show -> {
            R.navigation.nav_tv
        }
        R.id.menu_trending -> {
            R.navigation.nav_trending
        }

        else -> {
            R.navigation.nav_movie
        }

    }

    override fun onGraphChange() {

    }

    @FlowPreview
    @ExperimentalCoroutinesApi
    override fun onReselectNavItem(navController: NavController, fragment: Fragment) {
        when (fragment) {
            is MovieDetailFragment -> {
                navController.navigate(R.id.action_movieDetailFragment_to_movieFragment)
            }
            is TvDetailFragment -> {
                navController.navigate(R.id.action_tvDetailFragment_to_tvFragment)

            }
            is TvEpisodesFragment -> {
                navController.navigate(R.id.action_tvEpisodesFragment_to_tvFragment)

            }

            else -> {

            }

        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {
            android.R.id.home -> onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }


    override fun displayProgressBar(isLoading: Boolean) {
        if(isLoading){
            animation_progressBar.visibility = View.VISIBLE
        }
        else{
            animation_progressBar.visibility = View.GONE
        }
    }

    override fun expandAppBar() {

    }


    override fun onBackPressed() = bottomNavController.onBackPressed()
}