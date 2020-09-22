package com.mehranj73.moviedb.ui.movie

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.bumptech.glide.RequestManager
import com.mehranj73.moviedb.R
import com.mehranj73.moviedb.data.model.MovieEntity
import com.mehranj73.moviedb.ui.movie.state.MovieStateEvent.MovieDetailEvent
import com.mehranj73.moviedb.util.StateMessageCallback
import com.mehranj73.moviedb.util.originalPosterUrl
import com.mehranj73.moviedb.util.w154PosterUrl
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.movie_detail_fragment.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import javax.inject.Inject

private const val TAG = "MovieDetailFragment"

@ExperimentalCoroutinesApi
@FlowPreview
@AndroidEntryPoint
class MovieDetailFragment(

) : BaseMovieFragment(R.layout.movie_detail_fragment) {

    @Inject
    lateinit var requestManager: RequestManager

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as AppCompatActivity).supportActionBar?.setDisplayShowTitleEnabled(true)
        setHasOptionsMenu(true)
        viewModel.setStateEvent(MovieDetailEvent)

        subscribeObservers()

    }

    private fun subscribeObservers() {

        viewModel.viewState.observe(viewLifecycleOwner, Observer { viewState ->
            viewState?.movieDetailFields?.let { movieDetailFields ->
                movieDetailFields.movieEntity?.let {
                    setMovieDetail(it)
                }
            }
        })

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

    }

    private fun setMovieDetail(movieEntity: MovieEntity) {
        title_textView.text = movieEntity.title
        score_textView.text = movieEntity.vote_average.toString()
        overview_textView.text = movieEntity.overview
        release_date_textView.text = movieEntity.release_date

        requestManager
            .load(movieEntity.poster_path.originalPosterUrl())
            .thumbnail(requestManager.load(movieEntity.poster_path.w154PosterUrl()))
            .into(poster_imageView)

        movieEntity.revenue?.let {
            revenu_textView.text = it.toString()
        }
        movieEntity.budget?.let {
            budget_textView.text = it.toString()
        }
        movieEntity.status?.let {
            revenu_textView.text = it
        }
        movieEntity.runtime?.let {
            runtime_textView.text = it.toString()
        }


    }
}