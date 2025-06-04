package com.programobil.collitav1_front.ui.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.programobil.collitav1_front.network.LoginRequest
import com.programobil.collitav1_front.network.RegisterRequest
import com.programobil.collitav1_front.network.RetrofitClient
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

data class AuthState(
    val isLoading: Boolean = false,
    val error: String? = null,
    val isAuthenticated: Boolean = false
)

class AuthViewModel : ViewModel() {
    private val _authState = MutableStateFlow(AuthState())
    val authState: StateFlow<AuthState> = _authState

    fun login(username: String, password: String) {
        viewModelScope.launch {
            _authState.value = _authState.value.copy(isLoading = true, error = null)
            try {
                val response = RetrofitClient.api.login(LoginRequest(username, password))
                // Aquí podrías guardar el token en SharedPreferences
                _authState.value = _authState.value.copy(
                    isLoading = false,
                    isAuthenticated = true
                )
            } catch (e: Exception) {
                _authState.value = _authState.value.copy(
                    isLoading = false,
                    error = e.message ?: "Error al iniciar sesión"
                )
            }
        }
    }

    fun register(
        username: String,
        email: String,
        password: String,
        nombre: String,
        apellido: String,
        telefono: String
    ) {
        viewModelScope.launch {
            _authState.value = _authState.value.copy(isLoading = true, error = null)
            try {
                val request = RegisterRequest(username, email, password, nombre, apellido, telefono)
                RetrofitClient.api.register(request)
                _authState.value = _authState.value.copy(
                    isLoading = false,
                    isAuthenticated = true
                )
            } catch (e: Exception) {
                _authState.value = _authState.value.copy(
                    isLoading = false,
                    error = e.message ?: "Error al registrar usuario"
                )
            }
        }
    }
} 