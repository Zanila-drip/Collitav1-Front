package com.programobil.collitav1_front.network

import retrofit2.http.*

interface ApiService {
    @POST("api/auth/login")
    suspend fun login(@Body request: LoginRequest): LoginResponse

    @POST("api/auth/register")
    suspend fun register(@Body request: RegisterRequest): RegisterResponse

    @GET("api/users/profile")
    suspend fun getUserProfile(@Header("Authorization") token: String): UserResponse

    @POST("api/trabajos")
    suspend fun guardarTrabajo(
        @Header("Authorization") token: String,
        @Body trabajo: TrabajoRequest
    ): TrabajoResponse

    @GET("api/trabajos")
    suspend fun obtenerTrabajos(
        @Header("Authorization") token: String
    ): List<TrabajoResponse>
} 