package com.mehranj73.moviedb.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "movie_table")
data class MovieEntity(
    val adult: Boolean,
    val backdrop_path: String?,

    @PrimaryKey(autoGenerate = false)
    val id: Int,

    val original_title: String,
    val overview: String,
    val popularity: Double,
    val poster_path: String,
    val release_date: String,
    val title: String,
    val vote_average: Float,
    val vote_count: Int,

    val budget: Int?,
    val revenue: Int?,
    val runtime: Int?,
    val status: String?,

)