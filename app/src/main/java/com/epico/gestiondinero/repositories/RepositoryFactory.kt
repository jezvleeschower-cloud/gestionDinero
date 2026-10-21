package com.epico.gestiondinero.repositories

import android.content.Context
import com.epico.gestiondinero.data.AlmacenLocal

/** Fábrica del repositorio. Hoy solo hay versión local. */
object RepositoryFactory {
    fun crear(context: Context): FinanzasRepository =
        FinanzasRepositoryLocal(AlmacenLocal(context.applicationContext))
}
