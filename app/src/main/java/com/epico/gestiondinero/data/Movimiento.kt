package com.epico.gestiondinero.data

data class Movimiento(
    val id: Long,
    val concepto: String,
    val monto: Double,
    val esIngreso: Boolean,
    val fecha: Long,
)

fun List<Movimiento>.totalIngresos(): Double = filter { it.esIngreso }.sumOf { it.monto }
fun List<Movimiento>.totalEgresos(): Double = filter { !it.esIngreso }.sumOf { it.monto }
fun List<Movimiento>.saldo(): Double = totalIngresos() - totalEgresos()

