package com.programobil.collitav1_front.network

import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface ApiService {
    @POST("api/auth/login")
    suspend fun login(@Body request: LoginRequest): LoginResponse

    @POST("api/users/register")
    suspend fun register(@Body request: RegisterRequest): UserResponse

    @GET("api/users/profile")
    suspend fun getUserProfile(@Header("Authorization") token: String): UserProfileResponse
} 