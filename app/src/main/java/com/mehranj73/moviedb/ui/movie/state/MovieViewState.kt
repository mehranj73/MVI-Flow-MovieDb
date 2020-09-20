package com.mehranj73.moviedb.ui.movie.state

import com.mehranj73.moviedb.data.model.Movie

data class MovieViewState(
    var movies: List<Movie>? = null,
    var movieDetailFields: MovieDetailFields = MovieDetailFields()

) {


    data class MovieDetailFields(
        var movie: Movie? = null,
        var movieId: Int? = null
    )


}


