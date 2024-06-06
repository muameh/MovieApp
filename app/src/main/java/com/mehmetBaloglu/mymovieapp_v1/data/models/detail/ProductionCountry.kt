package com.mehmetBaloglu.mymovieapp_v1.data.models.general_returns.detail


import com.google.gson.annotations.SerializedName

data class ProductionCountry(
    @SerializedName("iso_3166_1")
    val iso31661: String,
    @SerializedName("name")
    val name: String
)