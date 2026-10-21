package com.epico.gestiondinero.data

import android.content.Context
import com.epico.gestiondinero.utils.Constantes
import org.json.JSONArray
import org.json.JSONObject

/**
 * Guarda todo en el teléfono con SharedPreferences (JSON).
 * No necesita librerías extra. Si luego quieres Room, solo cambias esta clase.
 */
class AlmacenLocal(context: Context) {
    private val prefs =
        context.getSharedPreferences(Constantes.NOMBRE_PREFS, Context.MODE_PRIVATE)

    fun cargarMovimientos(): List<Movimiento> = leerArreglo(CLAVE_MOVS).map {
        Movimiento(
            id = it.getLong("id"),
            concepto = it.getString("concepto"),
            monto = it.getDouble("monto"),
            esIngreso = it.getBoolean("esIngreso"),
            fecha = it.getLong("fecha"),
        )
    }

    fun guardarMovimientos(lista: List<Movimiento>) = guardarArreglo(
        CLAVE_MOVS,
        lista.map {
            JSONObject().put("id", it.id).put("concepto", it.concepto)
                .put("monto", it.monto).put("esIngreso", it.esIngreso)
                .put("fecha", it.fecha)
        },
    )

    fun cargarGastos(): List<GastoEsperado> = leerArreglo(CLAVE_GASTOS).map {
        GastoEsperado(
            id = it.getLong("id"),
            monto = it.getDouble("monto"),
            fecha = it.getLong("fecha"),
            periodo = Periodo.valueOf(it.getString("periodo")),
            cumplido = if (it.isNull("cumplido")) null else it.getBoolean("cumplido"),
        )
    }

    fun guardarGastos(lista: List<GastoEsperado>) = guardarArreglo(
        CLAVE_GASTOS,
        lista.map {
            JSONObject().put("id", it.id).put("monto", it.monto)
                .put("fecha", it.fecha).put("periodo", it.periodo.name)
                .put("cumplido", it.cumplido ?: JSONObject.NULL)
        },
    )

    fun cargarMetas(): List<Meta> = leerArreglo(CLAVE_METAS).map {
        Meta(it.getLong("id"), it.getString("titulo"), it.getBoolean("cumplida"))
    }

    fun guardarMetas(lista: List<Meta>) = guardarArreglo(
        CLAVE_METAS,
        lista.map {
            JSONObject().put("id", it.id).put("titulo", it.titulo)
                .put("cumplida", it.cumplida)
        },
    )

    private fun leerArreglo(clave: String): List<JSONObject> {
        val texto = prefs.getString(clave, null) ?: return emptyList()
        val arreglo = JSONArray(texto)
        return List(arreglo.length()) { arreglo.getJSONObject(it) }
    }

    private fun guardarArreglo(clave: String, items: List<JSONObject>) {
        prefs.edit().putString(clave, JSONArray(items).toString()).apply()
    }

    private companion object {
        const val CLAVE_MOVS = "movimientos"
        const val CLAVE_GASTOS = "gastos_esperados"
        const val CLAVE_METAS = "metas"
    }
}
