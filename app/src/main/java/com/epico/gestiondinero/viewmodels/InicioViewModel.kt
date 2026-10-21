package com.epico.gestiondinero.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.epico.gestiondinero.data.Movimiento
import com.epico.gestiondinero.data.saldo
import com.epico.gestiondinero.data.totalEgresos
import com.epico.gestiondinero.data.totalIngresos
import com.epico.gestiondinero.repositories.FinanzasRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

data class InicioUiState(
    val saldo: Double = 0.0,
    val ingresos: Double = 0.0,
    val egresos: Double = 0.0,
    val movimientos: List<Movimiento> = emptyList(),
)

class InicioViewModel(private val repo: FinanzasRepository) : ViewModel() {

    private fun aEstado(lista: List<Movimiento>) =
        InicioUiState(lista.saldo(), lista.totalIngresos(), lista.totalEgresos(), lista)

    val uiState: StateFlow<InicioUiState> = repo.movimientos
        .map(::aEstado)
        .stateIn(viewModelScope, SharingStarted.Eagerly, aEstado(repo.movimientos.value))

    fun agregar(concepto: String, monto: Double) =
        repo.agregarMovimiento(concepto, monto, esIngreso = true)

    fun retirar(concepto: String, monto: Double) =
        repo.agregarMovimiento(concepto, monto, esIngreso = false)
}
