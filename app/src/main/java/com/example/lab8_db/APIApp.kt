package com.example.lab8_db

import android.app.Application
import androidx.room.Room
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class APIApp: Application() {
    lateinit var service: PostAPIService
        private set
    lateinit var database: Dao
        private set
    override fun onCreate() {
        super.onCreate()
        instance = this
        val retrofit = Retrofit.Builder()
            .baseUrl("https://jsonplaceholder.typicode.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        service = retrofit.create(PostAPIService::class.java)
        database = Room.databaseBuilder(this,
            PostDatabase::class.java, "db").build().dao()
    }
    companion object {
        lateinit var instance: APIApp
            private set
    }
}