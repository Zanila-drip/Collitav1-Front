package com.programobil.collitav1_front.data.model

data class TrabajoResponse(
    val id: Long? = null,
    val fecha: String,
    val horaInicio: String,
    val horaFin: String,
    val tiempoTotal: String,
    val cosecha: String,
    val precioAproximado: Double,
    val estado: String = "PENDIENTE"
) {
    fun toTrabajo() = Trabajo(
        id = id,
        fecha = fecha,
        horaInicio = horaInicio,
        horaFin = horaFin,
        tiempoTotal = tiempoTotal,
        cosecha = cosecha,
        precioAproximado = precioAproximado,
        estado = estado
    )
} 