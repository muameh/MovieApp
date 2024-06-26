package com.mehmetBaloglu.mymovieapp_v1.data.repository

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore
import com.mehmetBaloglu.mymovieapp_v1.data.models.firebase_response.ForFirebaseResponse
import com.mehmetBaloglu.mymovieapp_v1.data.models.detailseries.DetailSerieResponse
import com.mehmetBaloglu.mymovieapp_v1.data.models.general_returns.FilmItem
import com.mehmetBaloglu.mymovieapp_v1.data.models.general_returns.GeneralResponse
import com.mehmetBaloglu.mymovieapp_v1.data.models.general_returns.detail.DetailFilmResponse
import com.mehmetBaloglu.mymovieapp_v1.data.models.series.SeriesItem
import com.mehmetBaloglu.mymovieapp_v1.data.models.series.SeriesResponse
import com.mehmetBaloglu.mymovieapp_v1.retrofit.ApiDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MovieRepo(var apiDao: ApiDao) {

    private var auth: FirebaseAuth
    private var db :FirebaseFirestore

    init {
        auth = Firebase.auth
        db = Firebase.firestore
    }

    //--------------------- MOVIES -------------------------------------//
    suspend fun getPopularMovies(page: Int) : GeneralResponse =
        withContext(Dispatchers.IO) { apiDao.getPopularMovies(page= page) }

    suspend fun getMoviesInTheaters() : List<FilmItem> =  //vizyondakiler
        withContext(Dispatchers.IO) { apiDao.getMoviesInTheaters().filmItems }

    suspend fun getUpcomingMovies() : List<FilmItem> =
        withContext(Dispatchers.IO) { apiDao.getUpcomingMovies().filmItems}

    suspend fun getTopRatedMovies(page: Int) : GeneralResponse =
        withContext(Dispatchers.IO) { apiDao.getTopRatedMovies(page= page)}

    //--------------------- TV SERIES -------------------------------------//
    suspend fun getTopRatedTVSeries(page: Int) : SeriesResponse =
        withContext(Dispatchers.IO) { apiDao.getTopRatedSeries(page= page)}

    suspend fun getPopularTVSeries(page: Int): SeriesResponse =
        withContext(Dispatchers.IO) { apiDao.getPopularTVSeries(page= page)}

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

    //------------------------DISCOVER----------------------------------------------//

    suspend fun discoverMovies(
        releaseDateGte: String,
        releaseDateLte: String,
        withGenres: String,
        voteAverageGte: Double,
        voteAverageLte: Double,
        runtimeGte: Int,
        runtimeLte: Int,
        voteCount : Int,
        keyword : String?
    ): List<FilmItem> =
        withContext(Dispatchers.IO) {
            apiDao.discoverMovies(
                releaseDateGte = releaseDateGte,
                releaseDateLte = releaseDateLte,
                withGenres = withGenres,
                voteAverageGte = voteAverageGte,
                voteAverageLte = voteAverageLte,
                runtimeGte = runtimeGte,
                runtimeLte = runtimeLte,
                voteCountGte = voteCount,
                withKeywords = keyword
            )
                .filmItems
        }

    //--------------------------------Firebase-------------------------------//

    var UserWatchList = MutableLiveData<List<ForFirebaseResponse>>()
    fun createUserWatchList(): MutableLiveData<List<ForFirebaseResponse>> {

        var usersEmailAdress = auth.currentUser?.email.toString()
        db.collection("WatchList")
            .whereEqualTo("email", usersEmailAdress)
            .addSnapshotListener { value, error ->
                if (value != null) {
                    val _tempWatchList = ArrayList<ForFirebaseResponse>()

                    val documents = value.documents

                    for (document in documents) {
                        val _item = document.toObject(ForFirebaseResponse::class.java)
                        if (_item != null) {
                            _tempWatchList.add(_item)
                        }
                    }
                    UserWatchList.value = _tempWatchList
                }
            }
        return UserWatchList
    }

    //-------------------------
    var UserWatchEDList = MutableLiveData<List<ForFirebaseResponse>>()
    fun createUserWatchEDList(): MutableLiveData<List<ForFirebaseResponse>> {
        var usersEmailAdress = auth.currentUser?.email.toString()

        db.collection("WatchedList")
            .whereEqualTo("email", usersEmailAdress)
            .addSnapshotListener { value, error ->
                if (value != null) {
                    val _tempWatchedList = ArrayList<ForFirebaseResponse>()

                    val documents = value.documents

                    for (document in documents) {
                        val _item = document.toObject(ForFirebaseResponse::class.java)
                        if (_item != null) {
                            _tempWatchedList.add(_item)
                        }
                    }
                    UserWatchEDList.value = _tempWatchedList
                }
            }
        return UserWatchEDList
    }

    //-----------------------------------------------------------------
    fun deleteFromWatchEDList(context: Context,filmID: String) {

        db.collection("WatchedList")
            .whereEqualTo("filmID", filmID)
            .get()
            .addOnSuccessListener { querySnapshot ->
                if (!querySnapshot.isEmpty) {
                    val document = querySnapshot.documents[0]

                    db.collection("WatchedList").document(document.id).delete()
                        .addOnSuccessListener {
                            println("Document successfully deleted!")
                            Toast.makeText(context,"deleted from WatchedList",Toast.LENGTH_SHORT).show()
                        }.addOnFailureListener { e -> println("Error deleting document: $e") }
                } else {
                    println("No documents found with the given ID")
                }
            }
    }
    //-----------------------------------------------------------

    fun deleteFromWatchList(context: Context,filmID: String) {

        db.collection("WatchList")
            .whereEqualTo("filmID", filmID)
            .get()
            .addOnSuccessListener { querySnapshot ->
                if (!querySnapshot.isEmpty) {
                    val document = querySnapshot.documents[0]

                    db.collection("WatchList").document(document.id).delete()
                        .addOnSuccessListener {
                            println("Document successfully deleted!")
                            Toast.makeText(context,"deleted from WatchList",Toast.LENGTH_SHORT).show()
                        }
                        .addOnFailureListener { e ->
                            println("Error deleting document: $e")
                        }
                } else {
                    println("No documents found with the given ID")
                }
            }
    }














}


