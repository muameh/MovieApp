package com.mehmetBaloglu.mymovieapp_v1.data.models.series


import com.google.gson.annotations.SerializedName

data class SeriesResponse(
    @SerializedName("page")
    val page: Int?,
    @SerializedName("results")
    val seriesItems: List<SeriesItem>,
    @SerializedName("total_pages")
    val totalPages: Int?,
    @SerializedName("total_results")
    val totalResults: Int?
)