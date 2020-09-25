package com.mehranj73.moviedb.ui.tv_show.state

import com.mehranj73.moviedb.util.StateEvent


sealed class TvStateEvent : StateEvent {

    object TvAiringTodayEvent : TvStateEvent(){

        override fun errorInfo(): String {
            return "Error getting tv airing today"
        }

        override fun toString(): String {
            return "TvAiringTodayEvent"
        }

    }
    object TvDetailEvent : TvStateEvent(){

        override fun errorInfo(): String {
            return "Error getting tv detail"
        }

        override fun toString(): String {
            return "TvDetailEvent"
        }

    }

}