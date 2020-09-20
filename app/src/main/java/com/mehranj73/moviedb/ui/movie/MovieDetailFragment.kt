package com.mehranj73.moviedb.ui.movie

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.Observer
import com.mehranj73.moviedb.R
import com.mehranj73.moviedb.data.model.Movie
import com.mehranj73.moviedb.ui.movie.state.MovieStateEvent.MovieDetailEvent
import com.mehranj73.moviedb.util.StateMessageCallback
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview

private const val TAG = "MovieDetailFragment"

@ExperimentalCoroutinesApi
@FlowPreview
class MovieDetailFragment(

) : BaseMovieFragment(R.layout.movie_detail_fragment) {


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        subscribeObservers()
        viewModel.setStateEvent(MovieDetailEvent)

    }

    private fun subscribeObservers() {

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