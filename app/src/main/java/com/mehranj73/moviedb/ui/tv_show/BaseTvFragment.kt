package com.mehranj73.moviedb.ui.tv_show

import android.os.Bundle
import android.view.View
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import com.mehranj73.moviedb.R
import dagger.hilt.android.AndroidEntryPoint


abstract class BaseTvFragment(
    @LayoutRes
    private val layoutRes: Int
) : Fragment(layoutRes) {


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //setupActionBarWithNavController(R.id.tvFragment, activity as AppCompatActivity)
    }



    private fun setupActionBarWithNavController(fragmentId: Int, activity: AppCompatActivity){
        val appBarConfiguration = AppBarConfiguration(setOf(fragmentId))
        NavigationUI.setupActionBarWithNavController(
            activity,
            findNavController(),
            appBarConfiguration
        )
    }

}