package com.mehranj73.moviedb.ui.tv_show

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.Observer
import com.mehranj73.moviedb.R
import com.mehranj73.moviedb.ui.tv_show.state.TvStateEvent.TvDetailEvent
import com.mehranj73.moviedb.util.StateMessageCallback
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview

private const val TAG = "TvDetailFragment"

@FlowPreview
@ExperimentalCoroutinesApi
@AndroidEntryPoint
class TvDetailFragment : BaseTvFragment(R.layout.tv_detail_fragment) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.setStateEvent(TvDetailEvent)

        subscribeObservers()

    }

    private fun subscribeObservers() {

        viewModel.viewState.observe(viewLifecycleOwner, Observer { viewState ->
            viewState?.tvDetailFields?.let { tvDetailFields ->
                tvDetailFields.tvEntity?.let {
                    Log.d(TAG, "subscribeObservers: ${it.number_of_seasons}")

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

}