package com.mehranj73.moviedb.data.local

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.mehranj73.moviedb.data.model.Season

class Converters {

    @TypeConverter
    fun fromSeasonList(value: List<Season>?): String? {
        val gson = Gson()
        val type = object : TypeToken<List<Season>>() {}.type
        return gson.toJson(value, type)
    }

    @TypeConverter
    fun toSeasonList(value: String?): List<Season>? {
        val gson = Gson()
        val type = object : TypeToken<List<Season>>() {}.type
        return gson.fromJson(value, type)
    }

    @TypeConverter
    fun fromString(value: String?): List<Int>? {
        val type = object: TypeToken<List<Int>>() {}.type
        return Gson().fromJson(value, type)
    }

    @TypeConverter
    fun fromArrayList(list: List<Int>?): String? {
        val type = object: TypeToken<List<Int>>() {}.type
        return Gson().toJson(list, type)
    }
}