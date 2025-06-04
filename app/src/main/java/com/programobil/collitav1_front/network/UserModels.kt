package com.programobil.collitav1_front.network

data class LoginRequest(
    val username: String,
    val password: String
)

data class LoginResponse(
    val token: String,
    val user: UserResponse
)

data class UserResponse(
    val id: String,
    val username: String,
    val email: String,
    val nombre: String,
    val apellidoPaterno: String,
    val apellidoMaterno: String,
    val curp: String,
    val telefono: String,
    val rol: String
)

data class RegisterRequest(
    val username: String,
    val email: String,
    val password: String,
    val nombre: String,
    val apellidoPaterno: String,
    val apellidoMaterno: String,
    val curp: String,
    val telefono: String
)

data class RegisterResponse(
    val token: String,
    val user: UserResponse
)

data class Trabajo(
    val id: String = "",
    val fecha: String,
    val horaInicio: String,
    val horaFin: String,
    val tiempoTotal: String,
    val cosecha: String,
    val precioAproximado: Double,
    val estado: String = "completado"
)

data class TrabajoRequest(
    val fecha: String,
    val horaInicio: String,
    val horaFin: String,
    val tiempoTotal: String,
    val cosecha: String,
    val precioAproximado: Double
)

data class TrabajoResponse(
    val id: String,
    val fecha: String,
    val horaInicio: String,
    val horaFin: String,
    val tiempoTotal: String,
    val cosecha: String,
    val precioAproximado: Double,
    val estado: String
) 