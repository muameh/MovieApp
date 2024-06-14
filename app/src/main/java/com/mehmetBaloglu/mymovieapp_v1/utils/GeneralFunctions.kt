package com.mehmetBaloglu.mymovieapp_v1.utils

object GeneralFunctions {

    fun createGenresIDs(genresText: String): String {
        val immutableMap = mapOf(
            "Action" to 28,
            "Adventure" to 12,
            "Animation" to 16,
            "Comedy" to 35,
            "Crime" to 80,
            "Documentary" to 99,
            "Drama" to 18,
            "Family" to 10751,
            "Fantasy" to 14,
            "History" to 36,
            "Horror" to 27,
            "Music" to 10402,
            "Mystery" to 9648,
            "Romance" to 10749,
            "Science Fiction" to 878,
            "TV Movie" to 10770,
            "Thriller" to 53,
            "War" to 10752,
            "Western" to 37
        )

        val genresList = genresText.split(",").map { it.trim() }
        val idsList = genresList.mapNotNull { genre -> immutableMap[genre] }

        return idsList.joinToString(", ")
    }
}