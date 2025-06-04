package com.programobil.collitav1_front.ui.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.programobil.collitav1_front.data.repository.TrabajoRepository
import com.programobil.collitav1_front.data.model.Trabajo
import com.programobil.collitav1_front.network.RetrofitClient
import com.programobil.collitav1_front.network.TrabajoRequest
import com.programobil.collitav1_front.network.TrabajoResponse
import com.programobil.collitav1_front.security.TokenManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException
import java.net.UnknownHostException
import kotlinx.coroutines.delay

sealed class TrabajoState {
    object Idle : TrabajoState()
    object Loading : TrabajoState()
    data class Success(val trabajos: List<Trabajo>) : TrabajoState()
    data class Error(val message: String) : TrabajoState()
}

class TrabajoViewModel(
    private val repository: TrabajoRepository
) : ViewModel() {
    private val _trabajoState = MutableStateFlow<TrabajoState>(TrabajoState.Idle)
    val trabajoState: StateFlow<TrabajoState> = _trabajoState

    private var retryCount = 0
    private val maxRetries = 3

    fun guardarTrabajo(trabajo: Trabajo) {
        viewModelScope.launch {
            try {
                val trabajoGuardado = repository.addTrabajo(trabajo)
                Log.d("TrabajoViewModel", "Trabajo guardado exitosamente: ${trabajoGuardado.id}")
                cargarTrabajos()
            } catch (e: HttpException) {
                val errorMessage = when (e.code()) {
                    500 -> "Error del servidor al guardar el trabajo. Detalles: ${e.message()}"
                    401 -> "Sesión expirada. Por favor, inicie sesión nuevamente."
                    403 -> "No tiene permisos para realizar esta acción."
                    else -> "Error al guardar el trabajo: ${e.message()}"
                }
                _trabajoState.value = TrabajoState.Error(errorMessage)
            } catch (e: Exception) {
                Log.e("TrabajoViewModel", "Error al guardar trabajo: ${e.message}", e)
                _trabajoState.value = TrabajoState.Error("Error al guardar el trabajo: ${e.message}")
            }
        }
    }

    fun cargarTrabajos() {
        viewModelScope.launch {
            _trabajoState.value = TrabajoState.Loading
            try {
                val trabajos = repository.getTrabajos()
                _trabajoState.value = TrabajoState.Success(trabajos)
                retryCount = 0 // Resetear contador de reintentos en caso de éxito
            } catch (e: HttpException) {
                val errorMessage = when (e.code()) {
                    500 -> {
                        if (retryCount < maxRetries) {
                            retryCount++
                            Log.w("TrabajoViewModel", "Error 500, reintentando (${retryCount}/$maxRetries)")
                            delay(2000) // Esperar 2 segundos antes de reintentar
                            cargarTrabajos()
                            return@launch
                        }
                        "Error del servidor (500). Detalles: ${e.message()}. Por favor, intente más tarde."
                    }
                    401 -> "Sesión expirada. Por favor, inicie sesión nuevamente."
                    403 -> "No tiene permisos para acceder a esta información."
                    else -> "Error al obtener los trabajos: ${e.message()}"
                }
                _trabajoState.value = TrabajoState.Error(errorMessage)
            } catch (e: UnknownHostException) {
                _trabajoState.value = TrabajoState.Error("No se pudo conectar al servidor. Verifique su conexión a internet.")
            } catch (e: IOException) {
                _trabajoState.value = TrabajoState.Error("Error de conexión. Verifique su conexión a internet.")
            } catch (e: Exception) {
                Log.e("TrabajoViewModel", "Error inesperado: ${e.message}", e)
                _trabajoState.value = TrabajoState.Error("Error inesperado: ${e.message}")
            }
        }
    }
} 