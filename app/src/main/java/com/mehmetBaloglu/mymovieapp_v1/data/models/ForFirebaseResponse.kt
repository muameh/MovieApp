package com.mehmetBaloglu.mymovieapp_v1.data.models

import com.google.firebase.Timestamp

data class ForFirebaseResponse(
    var filmID: String? = "",
    var filmName: String? = "",
    var filmPosterUrl: String? = "",
    var email: String? = "",
    var date: Timestamp? = null
)