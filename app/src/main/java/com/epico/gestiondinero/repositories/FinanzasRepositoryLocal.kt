package com.epico.gestiondinero.repositories

import com.epico.gestiondinero.data.AlmacenLocal
import com.epico.gestiondinero.data.GastoEsperado
import com.epico.gestiondinero.data.Meta
import com.epico.gestiondinero.data.Movimiento
import com.epico.gestiondinero.data.Periodo
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class FinanzasRepositoryLocal(private val almacen: AlmacenLocal) : FinanzasRepository {

    private val _movimientos = MutableStateFlow(almacen.cargarMovimientos())
    private val _gastos = MutableStateFlow(almacen.cargarGastos())
    private val _metas = MutableStateFlow(almacen.cargarMetas())

    override val movimientos: StateFlow<List<Movimiento>> = _movimientos.asStateFlow()
    override val gastos: StateFlow<List<GastoEsperado>> = _gastos.asStateFlow()
    override val metas: StateFlow<List<Meta>> = _metas.asStateFlow()

    override fun agregarMovimiento(concepto: String, monto: Double, esIngreso: Boolean) {
        val ahora = System.currentTimeMillis()
        val lista = listOf(Movimiento(ahora, concepto, monto, esIngreso, ahora)) + _movimientos.value
        _movimientos.value = lista
        almacen.guardarMovimientos(lista)
    }

    override fun registrarGasto(monto: Double, periodo: Periodo) {
        val ahora = System.currentTimeMillis()
        val lista = listOf(GastoEsperado(ahora, monto, ahora, periodo)) + _gastos.value
        _gastos.value = lista
        almacen.guardarGastos(lista)
    }

    override fun marcarGasto(id: Long, cumplido: Boolean) {
        val lista = _gastos.value.map { if (it.id == id) it.copy(cumplido = cumplido) else it }
        _gastos.value = lista
        almacen.guardarGastos(lista)
    }

    override fun eliminarGasto(id: Long) {
        val lista = _gastos.value.filter { it.id != id }
        _gastos.value = lista
        almacen.guardarGastos(lista)
    }

    override fun crearMeta(titulo: String) {
        val lista = _metas.value + Meta(System.currentTimeMillis(), titulo)
        _metas.value = lista
        almacen.guardarMetas(lista)
    }

    override fun actualizarMeta(id: Long, cumplida: Boolean) {
        val lista = _metas.value.map { if (it.id == id) it.copy(cumplida = cumplida) else it }
        _metas.value = lista
        almacen.guardarMetas(lista)
    }
}
