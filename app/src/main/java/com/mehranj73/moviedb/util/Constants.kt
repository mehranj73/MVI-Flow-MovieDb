package com.mehranj73.moviedb.util

object Constants {

    const val BASE_URL = "https://api.themoviedb.org/3/"
    const val API_KEY = "56e449fe687236a993074a51c7480801"

    const val NETWORK_TIMEOUT = 6000L
    const val CACHE_TIMEOUT = 2000L
    const val TESTING_NETWORK_DELAY = 0L // fake network delay for testing
    const val TESTING_CACHE_DELAY = 0L // fake cache delay for testing


    const val PAGINATION_PAGE_SIZE = 10

    const val GALLERY_REQUEST_CODE = 201
    const val PERMISSIONS_REQUEST_READ_STORAGE: Int = 301
    const val CROP_IMAGE_INTENT_CODE: Int = 401
}