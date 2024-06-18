package com.mehmetBaloglu.mymovieapp_v1.ui.viewmodels

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import com.mehmetBaloglu.mymovieapp_v1.data.models.general_returns.FilmItem
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mehmetBaloglu.mymovieapp_v1.data.models.ForFirebaseResponse
import com.mehmetBaloglu.mymovieapp_v1.data.models.detailseries.DetailSerieResponse
import com.mehmetBaloglu.mymovieapp_v1.data.models.general_returns.detail.DetailFilmResponse
import com.mehmetBaloglu.mymovieapp_v1.data.models.series.SeriesItem
import com.mehmetBaloglu.mymovieapp_v1.data.repository.MovieRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MoviesViewModel @Inject constructor(private val movieRepo: MovieRepo): ViewModel() {

    private val _error = MutableLiveData<String?>()
    val error: LiveData<String?> get() = _error

    var topRatedMoviesList = MutableLiveData<List<FilmItem>>()
    var popularMoviesList = MutableLiveData<List<FilmItem>>()
    var moviesInTheatersList = MutableLiveData<List<FilmItem>>()
    var upcomingMoviesList = MutableLiveData<List<FilmItem>>()

    var popularTVSeriesList = MutableLiveData<List<SeriesItem>>()
    var topRatedTVSeriesList = MutableLiveData<List<SeriesItem>>()

    var searchListForMovies = MutableLiveData<List<FilmItem>>()
    var searchListForSeries = MutableLiveData<List<SeriesItem>>()

    var detailedMovie = MutableLiveData<DetailFilmResponse>()
    var detailedSerie = MutableLiveData<DetailSerieResponse>()

    var discoveredMoviesList = MutableLiveData<List<FilmItem>>()

    var UserWatchList = MutableLiveData<List<ForFirebaseResponse>>()
    var UserWatchEDList = MutableLiveData<List<ForFirebaseResponse>>()

    init {
        getpopularMovies()
        getMoviesInTheaters()
        getUpcomingMovies()
        getTopRatedMovies()

        getPopularTVSeries()
        getTopRatedTVSeries()

    }
    fun deleteFromWatchEDList(context: Context,filmID: String){ //context i kullanmammın tek nedeni movie repoda Toast fonk kullanmak içindi 
        movieRepo.deleteFromWatchEDList(context, filmID)
    }

    fun deleteFromWatchList(context: Context,filmID: String){
        movieRepo.deleteFromWatchList(context,filmID)
    }

    fun createUsersWatchList(){
        UserWatchList = movieRepo.createUserWatchList()
    }

    fun createUsersWatchEDList() {
        UserWatchEDList = movieRepo.createUserWatchEDList()

    }

    fun searchMovies(query : String) {
        viewModelScope.launch (Dispatchers.IO) {
            try {
                searchListForMovies.postValue(movieRepo.searchMovie(query))
            } catch (e : Exception) {
                _error.postValue("Failed Code: ${e.message}")
            }
        }
    }

    fun searchSeries(query : String) {
        viewModelScope.launch (Dispatchers.IO) {
            try {
                searchListForSeries.postValue(movieRepo.searchSeries(query))
            } catch (e : Exception) {
                _error.postValue("Failed Code: ${e.message}")
            }
        }
    }


    fun getpopularMovies() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                popularMoviesList.postValue(movieRepo.getPopularMovies())
            } catch (e: Exception){
                _error.postValue("Failed Code: ${e.message}")
            }
        }
    }

    fun getTopRatedMovies() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                topRatedMoviesList.postValue(movieRepo.getTopRatedMovies())
            } catch (e: Exception){
                _error.postValue("Failed Code: ${e.message}")
            }
        }
    }

    fun getPopularTVSeries()  = viewModelScope.launch (Dispatchers.IO) {
        try {
            popularTVSeriesList.postValue(movieRepo.getPopularTVSeries())
        } catch (e: Exception){
            _error.postValue("Failed Code: ${e.message}")
        }
    }

    fun getTopRatedTVSeries() = viewModelScope.launch (Dispatchers.IO) {
        try {
            topRatedTVSeriesList.postValue(movieRepo.getTopRatedTVSeries())
        } catch (e: Exception){
            _error.postValue("Failed Code: ${e.message}")
        }
    }

    fun getMoviesInTheaters() = viewModelScope.launch(Dispatchers.IO) {
        try {
            moviesInTheatersList.postValue(movieRepo.getMoviesInTheaters())
        } catch (e: Exception){
            _error.postValue("Failed Code: ${e.message}")
        }
    }

    fun getUpcomingMovies() = viewModelScope.launch(Dispatchers.IO) {
        try {
            upcomingMoviesList.postValue(movieRepo.getUpcomingMovies())
        } catch (e: Exception){
            _error.postValue("Failed Code: ${e.message}")
        }
    }

    fun getMovieDetails(id : Int) = viewModelScope.launch (Dispatchers.IO) {
        try {
            detailedMovie.postValue(movieRepo.getMovieDetails(id))
        } catch (e: Exception){
            _error.postValue("Failed Code: ${e.message}")
        }
    }

    fun getSerieDetails(id : Int) = viewModelScope.launch (Dispatchers.IO) {
        try {
            detailedSerie.postValue(movieRepo.getSerieDetails(id))
        } catch (e: Exception){
            _error.postValue("Failed Code: ${e.message}")
        }
    }

    fun discoverMovies(
        releaseDateGte: String,
        releaseDateLte: String,
        withGenres: String,
        voteAverageGte: Double,
        voteAverageLte: Double,
        runtimeGte: Int,
        runtimeLte: Int,
        voteCount : Int,
        keyWord : String?
    ) = viewModelScope.launch  (Dispatchers.IO) {
        try {
            discoveredMoviesList.postValue(movieRepo.discoverMovies(releaseDateGte,
                releaseDateLte,
                withGenres,
                voteAverageGte,
                voteAverageLte,
                runtimeGte,
                runtimeLte,
                voteCount,
                keyword = keyWord ))
            Log.e("xxx1",discoveredMoviesList.toString())
        } catch (e: Exception){
            _error.postValue("Failed Code: ${e.message}")
            Log.e("xxx2",e.toString())
        }
    }




}