package com.epico.gestiondinero.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.epico.gestiondinero.repositories.FinanzasRepository

/** Inyección de dependencias: entrega el repositorio a cada ViewModel. */
class ViewModelFactory(private val repo: FinanzasRepository) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T = when {
        modelClass.isAssignableFrom(InicioViewModel::class.java) -> InicioViewModel(repo)
        modelClass.isAssignableFrom(GastoViewModel::class.java) -> GastoViewModel(repo)
        modelClass.isAssignableFrom(MetasViewModel::class.java) -> MetasViewModel(repo)
        else -> throw IllegalArgumentException("ViewModel desconocido: ${modelClass.name}")
    } as T
}
