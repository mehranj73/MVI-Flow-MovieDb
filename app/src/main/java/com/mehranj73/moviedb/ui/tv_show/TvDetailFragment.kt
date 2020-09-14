package com.mehranj73.moviedb.ui.tv_show

import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.navigation.fragment.findNavController
import com.mehranj73.moviedb.R
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class TvDetailFragment: BaseTvFragment(R.layout.tv_detail_fragment) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<Button>(R.id.go_to_tv_episodes).setOnClickListener {
            findNavController().navigate(R.id.action_tvDetailFragment_to_tvEpisodesFragment)
        }
    }

}