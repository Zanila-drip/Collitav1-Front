package com.programobil.collitav1_front.di

import com.programobil.collitav1_front.network.ApiService
import com.programobil.collitav1_front.ui.viewmodels.UserViewModel
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object AppModule {
    private const val BASE_URL = "http://192.168.1.71:8080/"

    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val apiService: ApiService by lazy {
        retrofit.create(ApiService::class.java)
    }

    fun provideUserViewModel(): UserViewModel {
        return UserViewModel()
    }
} 