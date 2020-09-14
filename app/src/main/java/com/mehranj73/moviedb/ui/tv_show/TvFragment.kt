package com.mehranj73.moviedb.ui.tv_show

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.findNavController
import com.mehranj73.moviedb.R
import kotlinx.android.synthetic.main.fragment_tv.*


class TvFragment : BaseTvFragment(R.layout.fragment_tv) {


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as AppCompatActivity).supportActionBar?.setDisplayShowTitleEnabled(false)
        setHasOptionsMenu(true)

        go_to_tv_detail.setOnClickListener {
            findNavController().navigate(R.id.action_TvFragment_to_tvDetailFragment)
        }

    }


}