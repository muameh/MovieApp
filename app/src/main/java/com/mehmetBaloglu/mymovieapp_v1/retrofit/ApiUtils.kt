package com.mehmetBaloglu.mymovieapp_v1.retrofit

import com.mehmetBaloglu.mymovieapp_v1.utils.Constants


class ApiUtils {

    companion object {

        val BASE_URL = Constants.BASE_URL

        fun getDao(): ApiDao {
            return RetrofitClient.getClient(BASE_URL).create(ApiDao::class.java)
        }
    }
}