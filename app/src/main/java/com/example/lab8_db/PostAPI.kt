package com.example.lab8_db

import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.parcelize.Parcelize

class PostAPI {
    @Parcelize
    data class PostAPI (
        //@Json(name = "id") val id: Int,
        @Json(name = "title") val title: String,
        @Json(name = "body") val body: String) : Parcelable
}