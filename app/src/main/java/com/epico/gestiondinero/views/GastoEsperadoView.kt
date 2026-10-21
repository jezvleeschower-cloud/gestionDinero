package com.epico.gestiondinero.views

import android.text.format.DateUtils
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.epico.gestiondinero.data.GastoEsperado
import com.epico.gestiondinero.data.Periodo
import com.epico.gestiondinero.tema.Colores
import com.epico.gestiondinero.utils.formatoMoneda
import com.epico.gestiondinero.viewmodels.GastoViewModel
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

private val local = Locale("es", "MX")

private fun etiquetaFecha(gasto: GastoEsperado): String = when (gasto.periodo) {
    Periodo.HOY ->
        if (DateUtils.isToday(gasto.fecha)) "Hoy"
        else SimpleDateFormat("dd/MM/yyyy", local).format(Date(gasto.fecha))

    Periodo.SEMANAL -> {
        val cal = Calendar.getInstance().apply {
            firstDayOfWeek = Calendar.MONDAY
            timeInMillis = gasto.fecha
            set(Calendar.DAY_OF_WEEK, Calendar.MONDAY)
        }
        val formato = SimpleDateFormat("dd/MM", local)
        val inicio = formato.format(cal.time)
        cal.add(Calendar.DAY_OF_YEAR, 6)
        "Semana del $inicio al ${formato.format(cal.time)}"
    }

    Periodo.MENSUAL ->
        SimpleDateFormat("MMMM yyyy", local).format(Date(gasto.fecha))
            .replaceFirstChar { it.uppercase() }
}

@Composable
fun GastoEsperadoView(vm: GastoViewModel) {
    val estado by vm.uiState.collectAsState()
    var dialogo by remember { mutableStateOf(false) }
    var porEliminar by remember { mutableStateOf<GastoEsperado?>(null) }

    Column(
        Modifier
            .fillMaxSize()
            .padding(start = 20.dp, top = 16.dp, end = 20.dp, bottom = 12.dp)
    ) {
        TarjetaSaldo(estado.saldo)
        Spacer(Modifier.height(12.dp))
        TituloSeccion("Gasto esperado")
        Spacer(Modifier.height(12.dp))
        SelectorPeriodo(estado.periodo, vm::seleccionarPeriodo)
        Spacer(Modifier.height(12.dp))

        Box(Modifier.weight(1f).fillMaxWidth()) {
            if (estado.gastos.isEmpty()) {
                Text(
                    "No hay gastos esperados en \"${estado.periodo.etiqueta}\".\nToca Agregar para registrar uno.",
                    color = Colores.textoSuave,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.align(Alignment.Center),
                )
            } else {
                LazyColumn(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    items(estado.gastos, key = { it.id }) { gasto ->
                        TarjetaGasto(
                            gasto = gasto,
                            onMarcar = { vm.marcar(gasto.id, it) },
                            onEliminar = { porEliminar = gasto },
                        )
                    }
                }
            }
        }
        Spacer(Modifier.height(8.dp))
        BotonPrincipal("Agregar gasto esperado", { dialogo = true }, Modifier.fillMaxWidth())
    }

    if (dialogo) {
        DialogoMovimiento(
            titulo = "Gasto esperado (${estado.periodo.etiqueta})",
            pedirConcepto = false,
            onAceptar = { vm.registrarGasto(it.monto); dialogo = false },
            onCancelar = { dialogo = false },
        )
    }

    porEliminar?.let { gasto ->
        AlertDialog(
            onDismissRequest = { porEliminar = null },
            containerColor = Colores.tarjeta,
            title = { Text("¿Eliminar este gasto?", color = Color.White) },
            text = {
                Text(
                    "${formatoMoneda(gasto.monto)} (${etiquetaFecha(gasto)})",
                    color = Colores.textoSuave,
                )
            },
            confirmButton = {
                BotonPrincipal(
                    "Eliminar",
                    { vm.eliminar(gasto.id); porEliminar = null },
                    color = Colores.fallo, colorTexto = Color.Black,
                )
            },
            dismissButton = {
                TextButton(onClick = { porEliminar = null }) {
                    Text("Cancelar", color = Colores.textoSuave)
                }
            },
        )
    }
}

@Composable
private fun SelectorPeriodo(actual: Periodo, onSeleccion: (Periodo) -> Unit) {
    Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
        Periodo.values().forEach { periodo ->
            val activo = periodo == actual
            Box(
                modifier = Modifier
                    .weight(1f)
                    .height(48.dp)
                    .clip(CircleShape)
                    .background(if (activo) Colores.acento else Colores.tarjeta)
                    .clickable { onSeleccion(periodo) },
                contentAlignment = Alignment.Center,
            ) {
                Text(
                    periodo.etiqueta,
                    color = Color.White,
                    fontWeight = if (activo) FontWeight.Bold else FontWeight.Normal,
                )
            }
        }
    }
}

@Composable
private fun TarjetaGasto(
    gasto: GastoEsperado,
    onMarcar: (Boolean) -> Unit,
    onEliminar: () -> Unit,
) {
    Column(
        Modifier
            .fillMaxWidth()
            .background(Colores.panel, RoundedCornerShape(16.dp))
            .padding(16.dp)
    ) {
        Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
            Column(Modifier.weight(1f)) {
                Text(etiquetaFecha(gasto), color = Colores.textoSuave, fontSize = 14.sp)
                Text(
                    formatoMoneda(gasto.monto),
                    color = Colores.texto,
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold,
                )
            }
            IconButton(onClick = onEliminar) {
                Icon(Icons.Filled.Delete, contentDescription = "Eliminar gasto", tint = Colores.egreso)
            }
        }
        gasto.cumplido?.let { cumplido ->
            Spacer(Modifier.height(6.dp))
            Text(
                if (cumplido) "Resultado: cumplido" else "Resultado: fallo",
                color = if (cumplido) Colores.cumplido else Colores.egreso,
                fontWeight = FontWeight.Bold,
            )
        }
        Spacer(Modifier.height(12.dp))
        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
            BotonPrincipal(
                "Cumplido", { onMarcar(true) },
                color = Colores.cumplido, colorTexto = Color.Black,
            )
            BotonPrincipal(
                "Fallo", { onMarcar(false) },
                color = Colores.fallo, colorTexto = Color.Black,
            )
        }
    }
}
