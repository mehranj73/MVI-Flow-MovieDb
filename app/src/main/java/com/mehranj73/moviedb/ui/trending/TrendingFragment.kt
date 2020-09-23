package com.mehranj73.moviedb.ui.trending

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import com.bumptech.glide.request.RequestOptions
import com.mehranj73.moviedb.R
import com.mehranj73.moviedb.ui.movie.MovieAdapter
import com.mehranj73.moviedb.ui.trending.state.TrendingStateEvent.GetAllTrending
import com.mehranj73.moviedb.util.StateMessageCallback
import com.mehranj73.moviedb.util.TopSpacingItemDecoration
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.movie_fragment.*
import kotlinx.android.synthetic.main.movie_fragment.movieRecyclerView
import kotlinx.android.synthetic.main.trending_fragment.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import javax.inject.Inject

private const val TAG = "TrendingFragment"

@FlowPreview
@AndroidEntryPoint
@ExperimentalCoroutinesApi
class TrendingFragment(

) : BaseTrendingFragment(R.layout.trending_fragment) {

    private var requestManager: RequestManager? = null

    private lateinit var trendingAdapter: TrendingAdapter

    @Inject
    lateinit var requestOptions: RequestOptions


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as AppCompatActivity).supportActionBar?.setDisplayShowTitleEnabled(true)
        viewModel.setStateEvent(GetAllTrending)
        setupGlide()
        initRecyclerView()
        subscribeObservers()

    }

    private fun subscribeObservers(){

        viewModel.viewState.observe(viewLifecycleOwner, Observer { viewState ->
            if (viewState != null) {
                viewState.allTrending?.let {
                    trendingAdapter.apply {
                            preloadGlideImages(
                                requestManager = requestManager as RequestManager,
                                list = it
                            )
                            differ.submitList(it)

                        }
                    }
                }
            }

        )

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
                            Log.d(TAG, "removeMessageFromStack: ${it.response.message}")

                        }
                    }
                )
            }


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
        trendingAdapter = TrendingAdapter(
            requestManager as RequestManager
        )
        trendingRecyclerView.apply {
            layoutManager = LinearLayoutManager(this@TrendingFragment.context)
            val topSpacingItemDecoration = TopSpacingItemDecoration(30)
            removeItemDecoration(topSpacingItemDecoration)
            addItemDecoration(topSpacingItemDecoration)
            adapter = trendingAdapter
        }

    }


}