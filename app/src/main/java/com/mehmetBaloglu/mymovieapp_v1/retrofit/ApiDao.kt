package com.mehmetBaloglu.mymovieapp_v1.retrofit

import com.mehmetBaloglu.mymovieapp_v1.data.models.detailseries.DetailSerieResponse
import com.mehmetBaloglu.mymovieapp_v1.data.models.general_returns.GeneralResponse
import com.mehmetBaloglu.mymovieapp_v1.data.models.general_returns.detail.DetailFilmResponse
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
    ): DetailFilmResponse

    // Belirtilen dizinin detaylarını getirir
    @GET("tv/{tv_id}")
    suspend fun getSerieDetails(
        @Path("tv_id") tv_id: Int,
        @Header("accept") accept: String = "application/json",
        @Header("Authorization") auth: String = Constants.BEARER_TOKEN,
        @Query("language") language: String = "en-US",
        @Query("adult") adult: String = "true"
    ): DetailSerieResponse

    // Mevcut film türlerini listeler
    @GET("genre/movie/list")
    suspend fun getGenresOfMovies(
        @Header("accept") accept: String = "application/json",
        @Header("Authorization") auth: String = Constants.BEARER_TOKEN,
        @Query("language") language: String = "en-US",
        @Query("page") page: Int = 1,
        @Query("adult") adult: String = "true"
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


    //explore fragmentta çalışıyor
    @GET("discover/movie")
    suspend fun discoverMovies(
        @Header("accept") accept: String = "application/json",
        @Header("Authorization") auth: String = Constants.BEARER_TOKEN,
        @Query("language") language: String = "en-US",
        @Query("sort_by") sortBy: String = "popularity.desc",
        @Query("include_adult") includeAdult: Boolean = false,
        @Query("include_video") includeVideo: Boolean = false,
        @Query("page") page: Int = 1,
        @Query("primary_release_date.gte") releaseDateGte: String,  // 2020-01-01 ***
        @Query("primary_release_date.lte") releaseDateLte: String,  //2024-12-31 ***
        @Query("with_genres") withGenres: String, //28,35 ***
        //@Query("with_original_language") withOriginalLanguage: String, //en ***
        @Query("vote_average.gte") voteAverageGte: Double, //7.0 ***
        @Query("vote_average.gte") voteAverageLte: Double,
        @Query("with_runtime.gte") runtimeGte: Int, //90 ***
        @Query("with_runtime.lte") runtimeLte: Int, //200 ***
        @Query("with_keywords") withKeywords: String? = null, // Optional parameter
        @Query("vote_count.gte") voteCountGte: Int = 0
    ): GeneralResponse


}