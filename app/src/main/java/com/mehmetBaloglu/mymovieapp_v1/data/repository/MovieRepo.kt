package com.mehmetBaloglu.mymovieapp_v1.data.repository

import com.mehmetBaloglu.mymovieapp_v1.data.models.detailseries.DetailSerieResponse
import com.mehmetBaloglu.mymovieapp_v1.data.models.general_returns.FilmItem
import com.mehmetBaloglu.mymovieapp_v1.data.models.general_returns.detail.DetailFilmResponse
import com.mehmetBaloglu.mymovieapp_v1.data.models.series.SeriesItem
import com.mehmetBaloglu.mymovieapp_v1.retrofit.ApiDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MovieRepo(var apiDao: ApiDao) {

    //--------------------- MOVIES -------------------------------------//
    suspend fun getPopularMovies() : List<FilmItem> =
        withContext(Dispatchers.IO) { apiDao.getPopularMovies().filmItems }

    suspend fun getMoviesInTheaters() : List<FilmItem> =  //vizyondakiler
        withContext(Dispatchers.IO) { apiDao.getMoviesInTheaters().filmItems }

    suspend fun getUpcomingMovies() : List<FilmItem> =
        withContext(Dispatchers.IO) { apiDao.getUpcomingMovies().filmItems}

    suspend fun getTopRatedMovies() : List<FilmItem> =
        withContext(Dispatchers.IO) { apiDao.getTopRatedMovies().filmItems}

    //--------------------- TV SERIES -------------------------------------//
    suspend fun getTopRatedTVSeries() : List<SeriesItem> =
        withContext(Dispatchers.IO) { apiDao.getTopRatedSeries().seriesItems}

    suspend fun getPopularTVSeries(): List<SeriesItem> =
        withContext(Dispatchers.IO) { apiDao.getPopularTVSeries().seriesItems }

    //--------------------- SEARCH -------------------------------------//
    suspend fun searchMovie(query: String) : List<FilmItem> =
        withContext(Dispatchers.IO) {apiDao.searchMovies(query).filmItems }

    suspend fun searchSeries(query: String) : List<SeriesItem> =
        withContext(Dispatchers.IO) {apiDao.searchSeries(query).seriesItems}

    //--------------------- DETAILS ---------------------------------------//
    suspend fun getMovieDetails(id : Int) : DetailFilmResponse =
        withContext(Dispatchers.IO) {apiDao.getMovieDetails(id)}

    suspend fun getSerieDetails(id : Int) : DetailSerieResponse =
        withContext(Dispatchers.IO) { apiDao.getSerieDetails(id)}



}


