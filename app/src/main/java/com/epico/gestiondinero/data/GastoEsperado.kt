package com.epico.gestiondinero.data

/** cumplido: null = aún sin resultado. fecha = cuándo se registró. */
data class GastoEsperado(
    val id: Long,
    val monto: Double,
    val fecha: Long,
    val periodo: Periodo,
    val cumplido: Boolean? = null,
)
