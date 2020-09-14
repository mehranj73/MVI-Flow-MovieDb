package com.mehranj73.moviedb.ui.movie


import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import com.mehranj73.moviedb.R
import com.mehranj73.moviedb.ui.movie.state.MovieStateEvent
import com.mehranj73.moviedb.util.StateMessageCallback
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview

private const val TAG = "MovieFragment"


@ExperimentalCoroutinesApi
@FlowPreview
@AndroidEntryPoint
class MovieFragment(

) : BaseMovieFragment(R.layout.movie_fragment) {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        subscribeObservers()
        viewModel.setStateEvent(MovieStateEvent.NowPlayingEvent())

    }


    private fun subscribeObservers(){

        viewModel.viewState.observe(viewLifecycleOwner, Observer { viewState ->
                if (viewState != null){
                    Log.d(TAG, "subscribeObservers: ${viewState.movies?.get(0)?.original_title}")

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