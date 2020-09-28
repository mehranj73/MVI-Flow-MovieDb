package com.mehranj73.moviedb.ui.tv_show

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
import com.mehranj73.moviedb.data.model.MovieEntity
import com.mehranj73.moviedb.data.model.Season
import com.mehranj73.moviedb.data.model.TvEntity
import com.mehranj73.moviedb.ui.tv_show.state.TvStateEvent.TvDetailEvent
import com.mehranj73.moviedb.util.StateMessageCallback
import com.mehranj73.moviedb.util.TopSpacingItemDecoration
import com.mehranj73.moviedb.util.originalPosterUrl
import com.mehranj73.moviedb.util.w154PosterUrl
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_tv.*
import kotlinx.android.synthetic.main.movie_detail_fragment.first_air_date_textView
import kotlinx.android.synthetic.main.movie_detail_fragment.name_textView
import kotlinx.android.synthetic.main.movie_detail_fragment.overview_textView
import kotlinx.android.synthetic.main.movie_detail_fragment.poster_imageView
import kotlinx.android.synthetic.main.movie_detail_fragment.score_textView
import kotlinx.android.synthetic.main.tv_detail_fragment.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import javax.inject.Inject

private const val TAG = "TvDetailFragment"

@FlowPreview
@ExperimentalCoroutinesApi
@AndroidEntryPoint
class TvDetailFragment : BaseTvFragment(R.layout.tv_detail_fragment), TvSeasonsAdapter.Interaction {

    @Inject
    lateinit var requestManager: RequestManager

    private lateinit var seasonsAdapter: TvSeasonsAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as AppCompatActivity).supportActionBar?.setDisplayShowTitleEnabled(true)
        uiCommunicationListener.expandAppBar()
        viewModel.setStateEvent(TvDetailEvent)

        initRecyclerView()
        subscribeObservers()

    }

    private fun subscribeObservers() {

        viewModel.viewState.observe(viewLifecycleOwner, Observer { viewState ->
            viewState?.tvDetailFields?.let { tvDetailFields ->
                tvDetailFields.tvEntity?.let {

                    setTvDetail(it)
                    it.seasons?.let {seasons ->
                        seasonsAdapter.differ.submitList(seasons)
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
                        }
                    }
                )
            }
        })

    }


    private fun setTvDetail(tvEntity: TvEntity) {
        name_textView.text = tvEntity.name
        score_textView.text = tvEntity.vote_average.toString()
        overview_textView.text = tvEntity.overview
        first_air_date_textView.text = tvEntity.first_air_date

        requestManager
            .load(tvEntity.poster_path.originalPosterUrl())
            .thumbnail(requestManager.load(tvEntity.poster_path.w154PosterUrl()))
            .into(poster_imageView)

        tvEntity.last_air_date?.let {
            last_air_date_textView.text = it.toString()
        }
        tvEntity.type?.let {
            type_textView.text = it.toString()
        }
        tvEntity.status?.let {
            status_textView.text = it
        }
        tvEntity.in_production?.let {

            in_production_textView.text = if (it) "Yes" else "No"
        }


    }

    private fun initRecyclerView() {
        seasonsAdapter = TvSeasonsAdapter(
            requestManager as RequestManager,
            this
        )
        tv_seasons_recyclerView.apply {
            layoutManager = LinearLayoutManager(this@TvDetailFragment.context, LinearLayoutManager.HORIZONTAL, false)
            val topSpacingItemDecoration = TopSpacingItemDecoration(30)
            removeItemDecoration(topSpacingItemDecoration)
            addItemDecoration(topSpacingItemDecoration)
            adapter = seasonsAdapter
        }

    }

    override fun onItemSelected(position: Int, item: Season) {

    }

}