package com.mehmetBaloglu.mymovieapp_v1.data.models.genres


import com.google.gson.annotations.SerializedName

data class GenresResponse(
    @SerializedName("genres")
    val genres: List<Genre>
)