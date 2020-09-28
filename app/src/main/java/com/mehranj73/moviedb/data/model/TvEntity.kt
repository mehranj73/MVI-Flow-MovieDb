package com.mehranj73.moviedb.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName


@Entity(tableName = "tv_table")
data class TvEntity(
    val backdrop_path: String?,
    val first_air_date: String,

    @PrimaryKey(autoGenerate = false)
    val id: Int,
    val name: String,

    val overview: String,
    val popularity: Double,
    val poster_path: String?,
    val vote_average: Double,
    val vote_count: Int,

    @SerializedName("episode_run_time")
    val episodeRunTime: List<Int>?,

    val last_air_date: String?,

    val in_production: Boolean?,

    val number_of_episodes: Int?,
    val number_of_seasons: Int?,

    val seasons: List<Season>?,
    val status: String?,
    val type: String?

)