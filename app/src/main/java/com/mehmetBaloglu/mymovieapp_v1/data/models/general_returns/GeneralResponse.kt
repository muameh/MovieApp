package com.mehmetBaloglu.mymovieapp_v1.data.models.general_returns


import com.google.gson.annotations.SerializedName

data class GeneralResponse(
    @SerializedName("page")
    val page: Int,
    @SerializedName("results")
    val filmItems: List<FilmItem>,
    @SerializedName("total_pages")
    val totalPages: Int,
    @SerializedName("total_results")
    val totalResults: Int
)