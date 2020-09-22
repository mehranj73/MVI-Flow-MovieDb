package com.mehranj73.moviedb.ui.trending

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.navGraphViewModels
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import com.mehranj73.moviedb.R
import com.mehranj73.moviedb.ui.UICommunicationListener
import com.mehranj73.moviedb.ui.movie.MovieViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview

private const val TAG = "BaseTrendingFragment"
@ExperimentalCoroutinesApi
@FlowPreview
@AndroidEntryPoint
abstract class BaseTrendingFragment(
    @LayoutRes
    private val layoutRes: Int
): Fragment(layoutRes) {


    val viewModel: TrendingViewModel by navGraphViewModels(R.id.trendingFragment) {

        defaultViewModelProviderFactory
    }


    lateinit var uiCommunicationListener: UICommunicationListener

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupActionBarWithNavController(R.id.trendingFragment, activity as AppCompatActivity)
        setupChannel()
    }

    private fun setupChannel() = viewModel.setupChannel()


    private fun setupActionBarWithNavController(fragmentId: Int, activity: AppCompatActivity){
        val appBarConfiguration = AppBarConfiguration(setOf(fragmentId))
        NavigationUI.setupActionBarWithNavController(
            activity,
            findNavController(),
            appBarConfiguration
        )
    }


    override fun onAttach(context: Context) {
        super.onAttach(context)
        try {
            uiCommunicationListener = context as UICommunicationListener
        } catch (e: ClassCastException) {
            Log.e(TAG, "$context must implement UICommunicationListener")
        }

    }
}