package com.epico.gestiondinero.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.epico.gestiondinero.data.GastoEsperado
import com.epico.gestiondinero.data.Movimiento
import com.epico.gestiondinero.data.Periodo
import com.epico.gestiondinero.data.saldo
import com.epico.gestiondinero.repositories.FinanzasRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn

data class GastoUiState(
    val saldo: Double = 0.0,
    val periodo: Periodo = Periodo.HOY,
    val gastos: List<GastoEsperado> = emptyList(), // solo los del periodo elegido
)

class GastoViewModel(private val repo: FinanzasRepository) : ViewModel() {

    private val periodo = MutableStateFlow(Periodo.HOY)

    private fun aEstado(movs: List<Movimiento>, gastos: List<GastoEsperado>, p: Periodo) =
        GastoUiState(movs.saldo(), p, gastos.filter { it.periodo == p })

    val uiState: StateFlow<GastoUiState> =
        combine(repo.movimientos, repo.gastos, periodo) { movs, gastos, p -> aEstado(movs, gastos, p) }
            .stateIn(
                viewModelScope,
                SharingStarted.Eagerly,
                aEstado(repo.movimientos.value, repo.gastos.value, periodo.value),
            )

    fun seleccionarPeriodo(nuevo: Periodo) {
        periodo.value = nuevo
    }

    fun registrarGasto(monto: Double) = repo.registrarGasto(monto, periodo.value)

    fun marcar(id: Long, cumplido: Boolean) = repo.marcarGasto(id, cumplido)

    fun eliminar(id: Long) = repo.eliminarGasto(id)
}
