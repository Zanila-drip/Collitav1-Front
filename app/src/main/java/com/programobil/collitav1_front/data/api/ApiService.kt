package com.programobil.collitav1_front.data.api

import android.util.Log
import com.programobil.collitav1_front.data.model.TrabajoRequest
import com.programobil.collitav1_front.data.model.TrabajoResponse
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*
import java.util.concurrent.TimeUnit

interface ApiService {
    @GET("trabajos")
    suspend fun getTrabajos(@Header("Authorization") token: String): List<TrabajoResponse>

    @POST("trabajos")
    suspend fun addTrabajo(
        @Header("Authorization") token: String,
        @Body trabajo: TrabajoRequest
    ): TrabajoResponse

    companion object {
        private const val BASE_URL = "http://10.0.2.2:8080/api/"  // Para el emulador
        // private const val BASE_URL = "http://192.168.1.X:8080/api/"  // Para dispositivo físico

        fun create(): ApiService {
            val loggingInterceptor = HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            }

            val client = OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .addInterceptor { chain ->
                    val request = chain.request()
                    Log.d("ApiService", "Realizando petición: ${request.url}")
                    try {
                        val response = chain.proceed(request)
                        Log.d("ApiService", "Respuesta recibida: ${response.code}")
                        response
                    } catch (e: Exception) {
                        Log.e("ApiService", "Error en la petición: ${e.message}")
                        throw e
                    }
                }
                .connectTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .build()

            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(ApiService::class.java)
        }
    }
} 