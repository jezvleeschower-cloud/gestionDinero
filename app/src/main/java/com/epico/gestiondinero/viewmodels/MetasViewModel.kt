package com.epico.gestiondinero.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.epico.gestiondinero.data.Meta
import com.epico.gestiondinero.data.saldo
import com.epico.gestiondinero.repositories.FinanzasRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn

data class MetasUiState(
    val saldo: Double = 0.0,
    val metas: List<Meta> = emptyList(),
)

class MetasViewModel(private val repo: FinanzasRepository) : ViewModel() {

    val uiState: StateFlow<MetasUiState> =
        combine(repo.movimientos, repo.metas) { movs, metas -> MetasUiState(movs.saldo(), metas) }
            .stateIn(
                viewModelScope,
                SharingStarted.Eagerly,
                MetasUiState(repo.movimientos.value.saldo(), repo.metas.value),
            )

    fun crearMeta(titulo: String) = repo.crearMeta(titulo)

    fun actualizarMeta(meta: Meta, cumplida: Boolean) = repo.actualizarMeta(meta.id, cumplida)
}
