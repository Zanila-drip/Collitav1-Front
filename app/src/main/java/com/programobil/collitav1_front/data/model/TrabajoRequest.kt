package com.programobil.collitav1_front.data.model

data class TrabajoRequest(
    val fecha: String,
    val horaInicio: String,
    val horaFin: String,
    val tiempoTotal: String,
    val cosecha: String,
    val precioAproximado: Double
) 