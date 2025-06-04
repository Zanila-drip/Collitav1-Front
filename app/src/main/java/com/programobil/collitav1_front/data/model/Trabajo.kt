package com.programobil.collitav1_front.data.model

data class Trabajo(
    val id: Long? = null,
    val fecha: String,
    val horaInicio: String,
    val horaFin: String,
    val tiempoTotal: String,
    val cosecha: String,
    val precioAproximado: Double,
    val estado: String = "PENDIENTE"
) 