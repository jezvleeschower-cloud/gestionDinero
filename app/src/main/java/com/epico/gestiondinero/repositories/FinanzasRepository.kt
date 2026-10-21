package com.epico.gestiondinero.repositories

import com.epico.gestiondinero.data.GastoEsperado
import com.epico.gestiondinero.data.Meta
import com.epico.gestiondinero.data.Movimiento
import com.epico.gestiondinero.data.Periodo
import kotlinx.coroutines.flow.StateFlow

/** Único punto de acceso a los datos. Los ViewModel solo conocen esta interfaz. */
interface FinanzasRepository {
    val movimientos: StateFlow<List<Movimiento>>
    val gastos: StateFlow<List<GastoEsperado>>
    val metas: StateFlow<List<Meta>>

    fun agregarMovimiento(concepto: String, monto: Double, esIngreso: Boolean)
    fun registrarGasto(monto: Double, periodo: Periodo)
    fun marcarGasto(id: Long, cumplido: Boolean)
    fun eliminarGasto(id: Long)
    fun crearMeta(titulo: String)
    fun actualizarMeta(id: Long, cumplida: Boolean)
}
