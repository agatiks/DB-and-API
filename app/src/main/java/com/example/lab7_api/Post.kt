package com.example.lab7_api

import com.squareup.moshi.Json
data class Post (
    @Json(name = "id") val id: Int,
    @Json(name = "title") val title: String,
    @Json(name = "body") val body: String)
