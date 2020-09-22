package com.mehranj73.moviedb.ui.trending.state

import com.mehranj73.moviedb.util.StateEvent

sealed class TrendingStateEvent : StateEvent {

    object GetAllTrending: TrendingStateEvent(){

        override fun errorInfo(): String {
            return "error getting all trendings"
        }

        override fun toString(): String {
            return "GetAllTrending"
        }

    }

}