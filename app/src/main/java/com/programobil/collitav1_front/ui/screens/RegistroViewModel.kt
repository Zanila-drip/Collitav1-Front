package com.programobil.collitav1_front.ui.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.programobil.collitav1_front.network.RegisterRequest
import com.programobil.collitav1_front.network.UserResponse
import com.programobil.collitav1_front.network.RetrofitClient
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

sealed class RegistroState {
    object Idle : RegistroState()
    object Loading : RegistroState()
    data class Success(val response: UserResponse) : RegistroState()
    data class Error(val message: String) : RegistroState()
}

class RegistroViewModel : ViewModel() {
    private val _registroState = MutableStateFlow<RegistroState>(RegistroState.Idle)
    val registroState: StateFlow<RegistroState> = _registroState

    fun registrarUsuario(request: RegisterRequest) {
        _registroState.value = RegistroState.Loading
        viewModelScope.launch {
            try {
                val response = RetrofitClient.api.register(request)
                _registroState.value = RegistroState.Success(response)
            } catch (e: Exception) {
                _registroState.value = RegistroState.Error(e.message ?: "Error desconocido")
            }
        }
    }

    fun resetState() {
        _registroState.value = RegistroState.Idle
    }
} 