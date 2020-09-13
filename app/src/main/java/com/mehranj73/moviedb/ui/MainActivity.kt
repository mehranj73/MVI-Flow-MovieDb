package com.mehranj73.moviedb.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import com.bumptech.glide.RequestManager
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.mehranj73.moviedb.R
import com.mehranj73.moviedb.ui.movie.MovieFragment
import com.mehranj73.moviedb.util.BottomNavController
import com.mehranj73.moviedb.util.BottomNavController.*
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : BaseActivity(),
    NavGraphProvider,
    OnNavigationGraphChanged,
    OnNavigationReselectedListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    @Inject
    lateinit var requestManager: RequestManager

    private lateinit var bottomNavigationView: BottomNavigationView

    private val bottomNavController: BottomNavController by lazy(LazyThreadSafetyMode.NONE){
        BottomNavController(
            this,
            R.id.nav_host_fragment_container,
            R.id.menu_nav_movie,
            this,
            this)

    }

    override fun getNavGraphId(itemId: Int): Int = when(itemId) {
        R.id.menu_nav_movie -> {
            R.navigation.nav_movie
        }
        else -> {
            R.navigation.nav_movie
        }

    }
    override fun onGraphChange() {

    }

    override fun onReselectNavItem(navController: NavController, fragment: Fragment) {
        when(fragment){
            is MovieFragment -> {
                navController.navigate(R.id.action_movieDetailFragment_to_movieFragment)
            }
            else -> {

            }

        }
    }


}