package com.mehmetBaloglu.mymovieapp_v1.retrofit

import com.mehmetBaloglu.mymovieapp_v1.data.models.general_returns.GeneralResponse
import com.mehmetBaloglu.mymovieapp_v1.data.models.general_returns.detail.DetailResponse
import com.mehmetBaloglu.mymovieapp_v1.data.models.genres.GenresResponse
import com.mehmetBaloglu.mymovieapp_v1.data.models.series.SeriesResponse
import com.mehmetBaloglu.mymovieapp_v1.utils.Constants
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiDao {


    // En popüler filmleri listeler
    @GET("movie/popular")
    suspend fun getPopularMovies(
        @Header("accept") accept: String = "application/json",
        @Header("Authorization") auth: String = Constants.BEARER_TOKEN,
        @Query("language") language: String = "en-US",
        @Query("page") page: Int = 1
    ): GeneralResponse

    // En popüler dizileri listeler.
    @GET("tv/popular")
    suspend fun getPopularTVSeries(
        @Header("accept") accept: String = "application/json",
        @Header("Authorization") auth: String = Constants.BEARER_TOKEN,
        @Query("language") language: String = "en-US",
        @Query("page") page: Int = 1
    ): SeriesResponse

    // En yüksek oylamaya sahip dizileri listeler
    @GET("tv/top_rated")
    suspend fun getTopRatedSeries(
        @Header("accept") accept: String = "application/json",
        @Header("Authorization") auth: String = Constants.BEARER_TOKEN,
        @Query("language") language: String = "en-US",
        @Query("page") page: Int = 1
    ): SeriesResponse

    // Şu an sinemalarda gösterimde olan filmleri listeler.
    @GET("movie/now_playing")
    suspend fun getMoviesInTheaters(
        @Header("accept") accept: String = "application/json",
        @Header("Authorization") auth: String = Constants.BEARER_TOKEN,
        @Query("language") language: String = "en-US",
        @Query("page") page: Int = 1
    ): GeneralResponse

    // Yakında vizyona girecek filmleri listeler
    @GET("movie/upcoming")
    suspend fun getUpcomingMovies(
        @Header("accept") accept: String = "application/json",
        @Header("Authorization") auth: String = Constants.BEARER_TOKEN,
        @Query("language") language: String = "en-US",
        @Query("page") page: Int = 1
    ): GeneralResponse

    // En yüksek oylamaya sahip filmleri listeler
    @GET("movie/top_rated")
    suspend fun getTopRatedMovies(
        @Header("accept") accept: String = "application/json",
        @Header("Authorization") auth: String = Constants.BEARER_TOKEN,
        @Query("language") language: String = "en-US",
        @Query("page") page: Int = 1
    ): GeneralResponse

    // Belirtilen arama terimi ile eşleşen filmleri listeler
    @GET("search/movie")
    suspend fun searchMovies(
        @Query("query") query: String,
        @Header("accept") accept: String = "application/json",
        @Header("Authorization") auth: String = Constants.BEARER_TOKEN,
        @Query("language") language: String = "en-US",
        @Query("page") page: Int = 1,
    ): GeneralResponse

    // Belirtilen arama terimi ile eşleşen dizileri listeler
    @GET("search/tv")
    suspend fun searchSeries(
        @Query("query") query: String,
        @Header("accept") accept: String = "application/json",
        @Header("Authorization") auth: String = Constants.BEARER_TOKEN,
        @Query("language") language: String = "en-US",
        @Query("page") page: Int = 1
    ): SeriesResponse

    // Belirtilen filmin detaylarını getirir
    @GET("movie/{movie_id}")
    suspend fun getMovieDetails(
        @Path("movie_id") movieId: Int,
        @Header("accept") accept: String = "application/json",
        @Header("Authorization") auth: String = Constants.BEARER_TOKEN,
        @Query("language") language: String = "en-US"
    ): DetailResponse

    // Belirtilen dizinin detaylarını getirir
    @GET("tv/{tv_id}")
    suspend fun getSerieDetails(
        @Path("tv_id") tv_id: Int,
        @Header("accept") accept: String = "application/json",
        @Header("Authorization") auth: String = Constants.BEARER_TOKEN,
        @Query("language") language: String = "en-US"
    ): DetailResponse

    // Mevcut film türlerini listeler
    @GET("genre/movie/list")
    suspend fun getGenresOfMovies(
        @Header("accept") accept: String = "application/json",
        @Header("Authorization") auth: String = Constants.BEARER_TOKEN,
        @Query("language") language: String = "en-US",
        @Query("page") page: Int = 1
    ): GenresResponse

    // Mevcut dizi türlerini listeler
    @GET("genre/tv/list")
    suspend fun getGenresOfSeries(
        @Header("accept") accept: String = "application/json",
        @Header("Authorization") auth: String = Constants.BEARER_TOKEN,
        @Query("language") language: String = "en-US",
        @Query("page") page: Int = 1
    ): GenresResponse

    // En popüler oyuncuları listeler
    @GET("person/popular")
    suspend fun getPopularActors(
        @Header("accept") accept: String = "application/json",
        @Header("Authorization") auth: String = Constants.BEARER_TOKEN,
        @Query("language") language: String = "en-US",
        @Query("page") page: Int = 1
    ): GeneralResponse

    //https://api.themoviedb.org/3/
    // discover/movie?
    // api_key=03fcf2929cdbc24d14c2167c3a7dadfd&
    // language=en-US&
    // sort_by=popularity.desc&
    // include_adult=false&
    // include_video=false&
    // page=1&
    // primary_release_date.gte=2020-01-01&
    // primary_release_date.lte=2024-12-31&
    // with_genres=28,35&
    // with_original_language=en&
    // vote_average.gte=7.0&
    // with_runtime.gte=90&
    // with_runtime.lte=200&
    // with_keywords=
    @GET("discover/movie")
    suspend fun discoverMovies(
        @Header("accept") accept: String = "application/json",
        @Header("Authorization") auth: String = "Bearer YOUR_ACCESS_TOKEN",
        @Query("language") language: String = "en-US",
        @Query("sort_by") sortBy: String = "popularity.desc",
        @Query("include_adult") includeAdult: Boolean = false,
        @Query("include_video") includeVideo: Boolean = false,
        @Query("page") page: Int = 1,
        @Query("primary_release_date.gte") releaseDateGte: String,  // 2020-01-01
        @Query("primary_release_date.lte") releaseDateLte: String,  //2024-12-31
        @Query("with_genres") withGenres: String, //28,35
        @Query("with_original_language") withOriginalLanguage: String, //en
        @Query("vote_average.gte") voteAverageGte: Double, //7.0
        @Query("with_runtime.gte") runtimeGte: Int, //90
        @Query("with_runtime.lte") runtimeLte: Int, //200
        @Query("with_keywords") withKeywords: String
    ): GeneralResponse






}