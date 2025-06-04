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
    val apellido: String,
    val telefono: String,
    val rol: String
)

data class RegisterRequest(
    val username: String,
    val email: String,
    val password: String,
    val nombre: String,
    val apellido: String,
    val telefono: String
) 