package com.mehranj73.moviedb.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "trending_table")
data class TrendingEntity(
    val adult: Boolean,
    val backdrop_path: String,
    val first_air_date: String,

    @PrimaryKey(autoGenerate = false)
    val id: Int,
    val media_type: String,
    val name: String,
    val origin_country: List<String>,
    val original_language: String,
    val original_name: String,
    val original_title: String,
    val overview: String,
    val popularity: Double,
    val poster_path: String,
    val release_date: String,
    val title: String,
    val video: Boolean,
    val vote_average: Double,
    val vote_count: Int,


    val gender: Int?,
    val known_for_department: String?,
    val profile_path: String?
)