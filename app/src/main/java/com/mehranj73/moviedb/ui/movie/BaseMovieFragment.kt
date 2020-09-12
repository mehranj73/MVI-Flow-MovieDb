package com.mehranj73.moviedb.ui.movie

import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview


@FlowPreview
@ExperimentalCoroutinesApi
abstract class BaseMovieFragment(
    @LayoutRes
    private val layoutRes: Int
): Fragment() {

    val viewModel: MovieViewModel by viewModels()



}