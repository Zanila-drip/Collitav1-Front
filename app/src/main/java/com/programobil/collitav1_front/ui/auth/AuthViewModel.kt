package com.programobil.collitav1_front.ui.auth

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.programobil.collitav1_front.network.LoginRequest
import com.programobil.collitav1_front.network.RegisterRequest
import com.programobil.collitav1_front.network.RetrofitClient
import com.programobil.collitav1_front.security.TokenManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

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
                Log.d("AuthViewModel", "Login exitoso, guardando token")
                TokenManager.saveToken(response.token)
                _authState.value = _authState.value.copy(
                    isLoading = false,
                    isAuthenticated = true
                )
            } catch (e: HttpException) {
                val errorMessage = try {
                    e.response()?.errorBody()?.string() ?: "Error al iniciar sesión"
                } catch (e: Exception) {
                    "Error al iniciar sesión"
                }
                Log.e("AuthViewModel", "Error HTTP en login: $errorMessage", e)
                _authState.value = _authState.value.copy(
                    isLoading = false,
                    error = errorMessage
                )
            } catch (e: IOException) {
                Log.e("AuthViewModel", "Error de conexión en login", e)
                _authState.value = _authState.value.copy(
                    isLoading = false,
                    error = "Error de conexión. Por favor, verifica tu conexión a internet."
                )
            } catch (e: Exception) {
                Log.e("AuthViewModel", "Error inesperado en login", e)
                _authState.value = _authState.value.copy(
                    isLoading = false,
                    error = "Error inesperado: ${e.message}"
                )
            }
        }
    }

    fun register(
        username: String,
        email: String,
        password: String,
        nombre: String,
        apellidoPaterno: String,
        apellidoMaterno: String,
        curp: String,
        telefono: String
    ) {
        viewModelScope.launch {
            _authState.value = _authState.value.copy(isLoading = true, error = null)
            try {
                val request = RegisterRequest(
                    username = username,
                    email = email,
                    password = password,
                    nombre = nombre,
                    apellidoPaterno = apellidoPaterno,
                    apellidoMaterno = apellidoMaterno,
                    curp = curp,
                    telefono = telefono
                )
                val response = RetrofitClient.api.register(request)
                Log.d("AuthViewModel", "Registro exitoso, guardando token")
                TokenManager.saveToken(response.token)
                _authState.value = _authState.value.copy(
                    isLoading = false,
                    isAuthenticated = true
                )
            } catch (e: HttpException) {
                val errorMessage = try {
                    e.response()?.errorBody()?.string() ?: "Error al registrar usuario"
                } catch (e: Exception) {
                    "Error al registrar usuario"
                }
                Log.e("AuthViewModel", "Error HTTP en registro: $errorMessage", e)
                _authState.value = _authState.value.copy(
                    isLoading = false,
                    error = errorMessage
                )
            } catch (e: IOException) {
                Log.e("AuthViewModel", "Error de conexión en registro", e)
                _authState.value = _authState.value.copy(
                    isLoading = false,
                    error = "Error de conexión. Por favor, verifica tu conexión a internet."
                )
            } catch (e: Exception) {
                Log.e("AuthViewModel", "Error inesperado en registro", e)
                _authState.value = _authState.value.copy(
                    isLoading = false,
                    error = "Error inesperado: ${e.message}"
                )
            }
        }
    }

    fun logout() {
        TokenManager.clearToken()
        _authState.value = AuthState()
    }
} 