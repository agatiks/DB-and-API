package com.example.lab7_api


import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*

interface PostAPIService {
    @GET("users/{user}/posts")
    fun listPosts(
        @Path("user") user: Int
    ): Call<List<Post>>

    @POST("users/{user}/posts")
    fun makePost(
        @Path("user") user: Int,
        /*@Query("userId") userId: String,
        @Query("id") id: String,*/
        @Query("title") title: String,
        @Query("body") body: String
    ): Call<Post>

    @DELETE("users/{user}/posts")
    fun deletePost(
            @Path("user") user: Int,
            @Query("id") id: Int
    ): Call<Post>
}