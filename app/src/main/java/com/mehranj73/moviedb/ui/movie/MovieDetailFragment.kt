package com.mehranj73.moviedb.ui.movie

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import com.bumptech.glide.RequestManager
import com.bumptech.glide.request.RequestOptions
import com.mehranj73.moviedb.R
import com.mehranj73.moviedb.data.remote.RetrofitService
import com.mehranj73.moviedb.data.repository.MovieRepositoryImpl
import com.mehranj73.moviedb.ui.movie.state.MovieStateEvent
import com.mehranj73.moviedb.ui.movie.state.MovieStateEvent.MovieDetailEvent
import com.mehranj73.moviedb.util.StateMessageCallback
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val TAG = "MovieDetailFragment"
@ExperimentalCoroutinesApi
@FlowPreview
@AndroidEntryPoint
class MovieDetailFragment(

): BaseMovieFragment(R.layout.movie_detail_fragment) {

    @Inject
    lateinit var retrofitService: RetrofitService


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.setStateEvent(MovieDetailEvent)
        Log.d(TAG, "onViewCreated: ${viewModel.getMovieId()}")
        subscribeObservers()




    }

    private fun subscribeObservers(){

        viewModel.viewState.observe(viewLifecycleOwner, Observer { viewState ->
            viewState.movieDetailFields.let { movieDetailFields ->
                movieDetailFields.movie?.let {
                    Log.d(TAG, "subscribeObservers: ${it.title}")
                }


            }

            viewModel.numActiveJobs.observe(viewLifecycleOwner, {
                uiCommunicationListener.displayProgressBar(viewModel.areAnyJobsActive())
            })

            viewModel.stateMessage.observe(viewLifecycleOwner, Observer { stateMessage ->
                stateMessage?.let {
                    uiCommunicationListener.onResponseReceived(
                        response = it.response,
                        stateMessageCallback = object : StateMessageCallback {
                            override fun removeMessageFromStack() {
                                viewModel.clearStateMessage()
                            }
                        }
                    )
                }


            })
        })


    }
}