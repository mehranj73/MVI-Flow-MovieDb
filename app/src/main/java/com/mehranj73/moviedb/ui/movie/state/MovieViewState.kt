package com.mehranj73.moviedb.ui.movie.state

import com.mehranj73.moviedb.data.model.MovieEntity

data class MovieViewState(
    var movies: List<MovieEntity>? = null,
    var movieDetailFields: MovieDetailFields = MovieDetailFields()

) {


    data class MovieDetailFields(
        var movieEntity: MovieEntity? = null,
        var movieId: Int? = null
    )


}


