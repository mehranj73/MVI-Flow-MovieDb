package com.mehranj73.moviedb.ui.tv_show

import android.os.Bundle
import android.util.Log

import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import com.bumptech.glide.request.RequestOptions
import com.mehranj73.moviedb.R
import com.mehranj73.moviedb.data.model.MovieEntity
import com.mehranj73.moviedb.data.model.TvEntity
import com.mehranj73.moviedb.ui.movie.MovieAdapter
import com.mehranj73.moviedb.ui.tv_show.state.TvStateEvent
import com.mehranj73.moviedb.util.StateMessageCallback
import com.mehranj73.moviedb.util.TopSpacingItemDecoration
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_tv.*
import kotlinx.android.synthetic.main.movie_fragment.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import javax.inject.Inject


private const val TAG = "TvFragment"
@ExperimentalCoroutinesApi
@FlowPreview
@AndroidEntryPoint
class TvFragment : BaseTvFragment(R.layout.fragment_tv), TvAdapter.Interaction {


    private var requestManager: RequestManager? = null

    private lateinit var tvAdapter: TvAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as AppCompatActivity).supportActionBar?.setDisplayShowTitleEnabled(true)
        setHasOptionsMenu(true)

        viewModel.setStateEvent(TvStateEvent.TvAiringTodayEvent)
        setupGlide()
        initRecyclerView()
        subscribeObservers()

    }


    private fun subscribeObservers() {

        viewModel.viewState.observe(viewLifecycleOwner, Observer { viewState ->
            if (viewState != null) {
                tvAdapter.apply {
                    viewState.tvs?.let {
                        preloadGlideImages(
                            requestManager = requestManager as RequestManager,
                            list = it
                        )
                        differ.submitList(it)
                        Log.d(TAG, "subscribeObservers: differ")
                    }
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
                            Log.d(TAG, "removeMessageFromStack: ${it.response.message}")

                        }
                    }
                )
            }


        })

    }

    private fun initRecyclerView() {
        tvAdapter = TvAdapter(
            requestManager as RequestManager,
            this
        )
        tvRecyclerView.apply {
            layoutManager = LinearLayoutManager(this@TvFragment.context)
            val topSpacingItemDecoration = TopSpacingItemDecoration(30)
            removeItemDecoration(topSpacingItemDecoration)
            addItemDecoration(topSpacingItemDecoration)
            adapter = tvAdapter
        }

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

    override fun onDestroyView() {
        super.onDestroyView()
        // clear references (can leak memory)
        tvRecyclerView.adapter = null
        requestManager = null
    }


    override fun onItemSelected(position: Int, item: TvEntity) {
        viewModel.setTvId(item.id)
        findNavController().navigate(R.id.action_TvFragment_to_tvDetailFragment)

    }

}