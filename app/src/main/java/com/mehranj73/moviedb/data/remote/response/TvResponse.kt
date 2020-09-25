package com.mehranj73.moviedb.data.remote.response

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.mehranj73.moviedb.data.model.TvEntity

@Entity(tableName = "tv_table")
data class TvResponse(

    @PrimaryKey(autoGenerate = false)
    val id: Int,
    val page: Int,
    val results: List<TvEntity>,
    val total_pages: Int,
    val total_results: Int
)