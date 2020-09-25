package com.mehranj73.moviedb.ui.tv_show.state


import com.mehranj73.moviedb.data.model.TvEntity

data class TvViewState(
    var tvs: List<TvEntity>? = null,
    var tvDetailFields: TvDetailFields = TvDetailFields()
) {

    data class TvDetailFields(
        var tvEntity: TvEntity? = null,
        var tvId: Int? = null
    )
}