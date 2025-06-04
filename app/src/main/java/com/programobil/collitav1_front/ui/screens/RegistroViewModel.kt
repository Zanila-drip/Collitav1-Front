package com.programobil.collitav1_front.ui.screens

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.programobil.collitav1_front.network.RegisterRequest
import com.programobil.collitav1_front.network.RegisterResponse
import com.programobil.collitav1_front.network.RetrofitClient
import com.programobil.collitav1_front.security.TokenManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

sealed class RegistroState {
    object Idle : RegistroState()
    object Loading : RegistroState()
    data class Success(val response: RegisterResponse) : RegistroState()
    data class Error(val message: String) : RegistroState()
}

class RegistroViewModel : ViewModel() {
    private val _registroState = MutableStateFlow<RegistroState>(RegistroState.Idle)
    val registroState: StateFlow<RegistroState> = _registroState

    fun registrarUsuario(request: RegisterRequest) {
        _registroState.value = RegistroState.Loading
        viewModelScope.launch {
            try {
                Log.d("RegistroViewModel", "Iniciando registro con datos: ${request.username}")
                val response = RetrofitClient.api.register(request)
                Log.d("RegistroViewModel", "Respuesta completa del servidor: $response")
                
                if (response.token.isNullOrBlank()) {
                    Log.e("RegistroViewModel", "Error: Token nulo o vacío en la respuesta")
                    _registroState.value = RegistroState.Error("Error: No se recibió token del servidor")
                    return@launch
                }

                // Guardar el token
                try {
                    Log.d("RegistroViewModel", "Intentando guardar token: ${response.token}")
                    TokenManager.saveToken(response.token)
                    Log.d("RegistroViewModel", "Token guardado exitosamente")
                    
                    // Actualizar el estado con éxito
                    _registroState.value = RegistroState.Success(response)
                    Log.d("RegistroViewModel", "Estado actualizado a Success")
                } catch (e: Exception) {
                    Log.e("RegistroViewModel", "Error al guardar el token: ${e.message}")
                    Log.e("RegistroViewModel", "Stack trace: ${e.stackTraceToString()}")
                    _registroState.value = RegistroState.Error("Error al guardar el token: ${e.message}")
                    return@launch
                }
            } catch (e: Exception) {
                Log.e("RegistroViewModel", "Error en registro: ${e.message}")
                Log.e("RegistroViewModel", "Stack trace: ${e.stackTraceToString()}")
                _registroState.value = RegistroState.Error(e.message ?: "Error al registrar usuario")
            }
        }
    }

    fun resetState() {
        _registroState.value = RegistroState.Idle
    }
} 