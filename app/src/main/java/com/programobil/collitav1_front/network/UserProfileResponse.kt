package com.programobil.collitav1_front.network

data class UserProfileResponse(
    val nombre: String,
    val apellidoPaterno: String,
    val apellidoMaterno: String,
    val curp: String,
    val telefono: String,
    val email: String
) 