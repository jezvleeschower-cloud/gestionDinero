package com.epico.gestiondinero

import android.content.Context
import androidx.lifecycle.ViewModelProvider
import com.epico.gestiondinero.repositories.FinanzasRepository
import com.epico.gestiondinero.repositories.RepositoryFactory
import com.epico.gestiondinero.viewmodels.ViewModelFactory

/** Contenedor de dependencias (DI manual, sin librerías extra). */
class AppContenedor(context: Context) {
    val repositorio: FinanzasRepository = RepositoryFactory.crear(context)
    val viewModelFactory: ViewModelProvider.Factory = ViewModelFactory(repositorio)
}
