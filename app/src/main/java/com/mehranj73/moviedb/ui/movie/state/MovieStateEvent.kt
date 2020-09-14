package com.mehranj73.moviedb.ui.movie.state

import com.mehranj73.moviedb.util.StateEvent

sealed class MovieStateEvent : StateEvent {

    object NowPlayingEvent : MovieStateEvent() {
        override fun errorInfo(): String {
            return "Error getting now playing movies"
        }

        override fun toString(): String {
            return "NowPlayingEvent"
        }


    }

    class None: MovieStateEvent(){
        override fun errorInfo(): String {
            return "None."
        }

    }

}