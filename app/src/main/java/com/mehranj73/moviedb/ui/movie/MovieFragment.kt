package com.mehranj73.moviedb.ui.movie


import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import com.bumptech.glide.request.RequestOptions
import com.mehranj73.moviedb.R
import com.mehranj73.moviedb.data.model.Movie
import com.mehranj73.moviedb.ui.movie.MovieAdapter.Interaction
import com.mehranj73.moviedb.ui.movie.state.MovieStateEvent
import com.mehranj73.moviedb.util.StateMessageCallback
import com.mehranj73.moviedb.util.TopSpacingItemDecoration
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.movie_fragment.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import javax.inject.Inject

private const val TAG = "MovieFragment"


@ExperimentalCoroutinesApi
@FlowPreview
class MovieFragment(
) : BaseMovieFragment(R.layout.movie_fragment), Interaction {

    private var requestManager: RequestManager? = null

    private lateinit var movieAdapter: MovieAdapter

    @Inject
    lateinit var requestOptions: RequestOptions


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as AppCompatActivity).supportActionBar?.setDisplayShowTitleEnabled(false)
        setHasOptionsMenu(true)

        setupGlide()
        initRecyclerView()
        viewModel.setStateEvent(MovieStateEvent.NowPlayingEvent)
        subscribeObservers()


    }


    private fun subscribeObservers() {

        viewModel.viewState.observe(viewLifecycleOwner, Observer { viewState ->
            if (viewState != null) {
                movieAdapter.apply {
                    viewState.movies?.let {
                        preloadGlideImages(
                            requestManager = requestManager as RequestManager,
                            list = it
                        )
                    }
                    differ.submitList(viewState.movies)

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

    private fun setupGlide() {
        val requestOptions = RequestOptions
            .placeholderOf(R.drawable.default_image)
            .error(R.drawable.default_image)

        activity?.let {
            requestManager = Glide.with(it)
                .applyDefaultRequestOptions(requestOptions)
        }
    }

    private fun initRecyclerView() {
        movieAdapter = MovieAdapter(
            requestManager as RequestManager,
            this
        )
        movieRecyclerView.apply {
            layoutManager = LinearLayoutManager(this@MovieFragment.context)
            val topSpacingItemDecoration = TopSpacingItemDecoration(30)
            removeItemDecoration(topSpacingItemDecoration)
            addItemDecoration(topSpacingItemDecoration)
            adapter = movieAdapter
        }

    }

    override fun onItemSelected(position: Int, item: Movie) {
        viewModel.setMovieId(item.id)
        findNavController().navigate(R.id.action_movieFragment_to_movieDetailFragment)
    }


    override fun onDestroyView() {
        super.onDestroyView()
        // clear references (can leak memory)
        movieRecyclerView.adapter = null
        requestManager = null
    }

}