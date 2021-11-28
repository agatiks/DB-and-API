package com.example.lab7_api

import android.app.Application
import android.app.Service
import com.example.lab7_api.databinding.ActivityMainBinding
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

class APIApp: Application() {
    lateinit var service: PostAPIService
        private set
    lateinit var binding: ActivityMainBinding
    override fun onCreate() {
        super.onCreate()
        instance = this
        val retrofit = Retrofit.Builder()
            .baseUrl("https://jsonplaceholder.typicode.com/")
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
        service = retrofit.create(PostAPIService::class.java)
    }
    companion object {
        lateinit var instance: APIApp
            private set
    }
}