package com.mehmetBaloglu.mymovieapp_v1.data.models.general_returns.detail


import com.google.gson.annotations.SerializedName

data class Genre(
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String
)