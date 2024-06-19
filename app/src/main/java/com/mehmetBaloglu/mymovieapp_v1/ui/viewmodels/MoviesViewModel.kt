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
    //-----------------------------------------------
    private val _topRatedMoviesList = MutableLiveData<List<FilmItem>>()
    val topRatedMoviesList : LiveData<List<FilmItem>> get() = _topRatedMoviesList
    //-----------------------------------------------
    var moviesInTheatersList = MutableLiveData<List<FilmItem>>()
    var upcomingMoviesList = MutableLiveData<List<FilmItem>>()
    //-----------------------------------------------
    private val _popularMoviesList = MutableLiveData<List<FilmItem>>()
    val popularMoviesList: LiveData<List<FilmItem>> get() = _popularMoviesList
    //-----------------------------------------------
    private val _popularTVSeriesList = MutableLiveData<List<SeriesItem>>()
    val popularTVSeriesList :LiveData<List<SeriesItem>> get() = _popularTVSeriesList
    //-----------------------------------------------
    private val _topRatedTVSeriesList = MutableLiveData<List<SeriesItem>>()
    val topRatedTVSeriesList : LiveData<List<SeriesItem>> get() = _topRatedTVSeriesList
    //-----------------------------------------------
    var searchListForMovies = MutableLiveData<List<FilmItem>>()
    var searchListForSeries = MutableLiveData<List<SeriesItem>>()

    var detailedMovie = MutableLiveData<DetailFilmResponse>()
    var detailedSerie = MutableLiveData<DetailSerieResponse>()

    var discoveredMoviesList = MutableLiveData<List<FilmItem>>()

    var UserWatchList = MutableLiveData<List<ForFirebaseResponse>>()
    var UserWatchEDList = MutableLiveData<List<ForFirebaseResponse>>()

    private var currentPageForPopularMovies : Int = 1
    private var currentPageForTopRatedMovies : Int = 1
    private var currentPageForPopularSeries : Int = 1
    private var currentPageForTopRatedSeries : Int = 1

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

    //-------------------------------------------------------------------------------
    fun getpopularMovies(page: Int = currentPageForPopularMovies) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = movieRepo.getPopularMovies(page)
                val currentList = _popularMoviesList.value ?: emptyList()
                _popularMoviesList.postValue(currentList + response.filmItems)
            } catch (e: Exception){
                _error.postValue("Failed Code: ${e.message}")
            }
        }
    }
    fun loadNextPageForPopularMovies() {
        currentPageForPopularMovies++
        getpopularMovies(currentPageForPopularMovies)
    }
    //-------------------------------------------------------------------------------
    fun getTopRatedMovies(page: Int = currentPageForTopRatedMovies) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = movieRepo.getTopRatedMovies(page)
                val currentList = _topRatedMoviesList.value ?: emptyList()
                _topRatedMoviesList.postValue(currentList + response.filmItems)
            } catch (e: Exception){
                _error.postValue("Failed Code: ${e.message}")
            }
        }
    }
    fun loadNextPageForTopRatedMovies() {
        currentPageForTopRatedMovies++
        getTopRatedMovies(currentPageForTopRatedMovies)
    }
    //-------------------------------------------------------------------------------
    fun getPopularTVSeries(page: Int = currentPageForPopularSeries)  = viewModelScope.launch (Dispatchers.IO) {
        try {
            val response = movieRepo.getPopularTVSeries(page)
            val currentList = _popularTVSeriesList.value ?: emptyList()
            _popularTVSeriesList.postValue(currentList + response.seriesItems)
        } catch (e: Exception){
            _error.postValue("Failed Code: ${e.message}")
        }
    }
    fun loadNextPageForPopularTVSeries() {
        currentPageForPopularSeries++
        getPopularTVSeries(currentPageForPopularSeries)
    }
    //-------------------------------------------------------------------------------
    fun getTopRatedTVSeries(page: Int = currentPageForTopRatedSeries) = viewModelScope.launch (Dispatchers.IO) {
        try {
            val response = movieRepo.getTopRatedTVSeries(page)
            val currentList = _topRatedTVSeriesList.value ?: emptyList()
            _topRatedTVSeriesList.postValue(currentList + response.seriesItems)
        } catch (e: Exception){
            _error.postValue("Failed Code: ${e.message}")
        }
    }
    fun loadNextPageForTopRatedTVSeries() {
        currentPageForTopRatedSeries++
        getPopularTVSeries(currentPageForTopRatedSeries)
    }
    //-------------------------------------------------------------------------------
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