package com.mehmetBaloglu.mymovieapp_v1.data.models.detailseries


import com.google.gson.annotations.SerializedName

data class Network(
    @SerializedName("id")
    val id: Int?,
    @SerializedName("logo_path")
    val logoPath: String?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("origin_country")
    val originCountry: String?
)