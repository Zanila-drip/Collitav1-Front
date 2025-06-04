package com.programobil.collitav1_front.data.repository

import android.util.Log
import com.programobil.collitav1_front.data.api.ApiService
import com.programobil.collitav1_front.data.model.Trabajo
import com.programobil.collitav1_front.data.model.TrabajoRequest
import com.programobil.collitav1_front.data.model.TrabajoResponse
import com.programobil.collitav1_front.security.TokenManager
import retrofit2.HttpException
import java.io.IOException

class TrabajoRepository(
    private val apiService: ApiService
) {
    suspend fun getTrabajos(): List<Trabajo> {
        return try {
            val token = TokenManager.getToken() ?: throw Exception("No hay token disponible")
            Log.d("TrabajoRepository", "Iniciando petición de trabajos")
            val response = apiService.getTrabajos("Bearer $token")
            Log.d("TrabajoRepository", "Respuesta recibida: ${response.size} trabajos")
            response.map { it.toTrabajo() }
        } catch (e: HttpException) {
            Log.e("TrabajoRepository", "Error HTTP ${e.code()}: ${e.message()}")
            throw e
        } catch (e: IOException) {
            Log.e("TrabajoRepository", "Error de conexión: ${e.message}")
            throw e
        } catch (e: Exception) {
            Log.e("TrabajoRepository", "Error inesperado: ${e.message}")
            throw e
        }
    }

    suspend fun addTrabajo(trabajo: Trabajo): Trabajo {
        return try {
            val token = TokenManager.getToken() ?: throw Exception("No hay token disponible")
            Log.d("TrabajoRepository", "Iniciando petición para agregar trabajo")
            val request = TrabajoRequest(
                fecha = trabajo.fecha,
                horaInicio = trabajo.horaInicio,
                horaFin = trabajo.horaFin,
                tiempoTotal = trabajo.tiempoTotal,
                cosecha = trabajo.cosecha,
                precioAproximado = trabajo.precioAproximado
            )
            val response = apiService.addTrabajo("Bearer $token", request)
            Log.d("TrabajoRepository", "Trabajo agregado exitosamente")
            response.toTrabajo()
        } catch (e: HttpException) {
            Log.e("TrabajoRepository", "Error HTTP ${e.code()}: ${e.message()}")
            throw e
        } catch (e: IOException) {
            Log.e("TrabajoRepository", "Error de conexión: ${e.message}")
            throw e
        } catch (e: Exception) {
            Log.e("TrabajoRepository", "Error inesperado: ${e.message}")
            throw e
        }
    }
} 