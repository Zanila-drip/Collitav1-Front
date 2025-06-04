package com.programobil.collitav1_front.ui.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.programobil.collitav1_front.network.ApiService
import com.programobil.collitav1_front.network.UserProfileResponse
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class UserData(
    val nombre: String = "",
    val apellidoPaterno: String = "",
    val apellidoMaterno: String = "",
    val curp: String = "",
    val telefono: String = "",
    val email: String = ""
)

class UserViewModel : ViewModel() {
    private val _userData = MutableStateFlow(UserData())
    val userData: StateFlow<UserData> = _userData.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error.asStateFlow()

    fun loadUserData(token: String) {
        Log.d("UserViewModel", "Iniciando carga de datos del usuario")
        Log.d("UserViewModel", "Token recibido: ${token.take(10)}...")
        
        viewModelScope.launch {
            try {
                _isLoading.value = true
                Log.d("UserViewModel", "Realizando llamada a la API")
                
                // Por ahora, simulamos una respuesta exitosa
                _userData.value = UserData(
                    nombre = "Usuario",
                    apellidoPaterno = "Apellido",
                    apellidoMaterno = "Materno",
                    curp = "CURP123456",
                    telefono = "1234567890",
                    email = "usuario@ejemplo.com"
                )
                Log.d("UserViewModel", "Datos del usuario actualizados correctamente")
                
            } catch (e: Exception) {
                Log.e("UserViewModel", "Error al cargar datos del usuario", e)
                _error.value = "Error al cargar los datos: ${e.message}"
            } finally {
                _isLoading.value = false
                Log.d("UserViewModel", "Finalizada la carga de datos")
            }
        }
    }
} 