package com.epico.gestiondinero.views

import androidx.compose.foundation.background
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.epico.gestiondinero.data.Movimiento
import com.epico.gestiondinero.tema.Colores
import com.epico.gestiondinero.viewmodels.InicioViewModel

@Composable
fun InicioView(vm: InicioViewModel) {
    val estado by vm.uiState.collectAsState()
    // null = sin diálogo, true = agregar ingreso, false = retirar
    var dialogoIngreso by remember { mutableStateOf<Boolean?>(null) }

    Column(
        Modifier
            .fillMaxSize()
            .padding(start = 20.dp, top = 16.dp, end = 20.dp, bottom = 12.dp)
    ) {
        TarjetaSaldo(estado.saldo, estado.ingresos, estado.egresos)
        Spacer(Modifier.height(12.dp))
        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            BotonPrincipal(
                "Agregar", { dialogoIngreso = true },
                color = Colores.cumplido, colorTexto = Color.Black,
            )
            BotonPrincipal(
                "Retirar", { dialogoIngreso = false },
                color = Colores.fallo, colorTexto = Color.Black,
            )
        }
        Spacer(Modifier.height(12.dp))
        ListaMovimientos(estado.movimientos, Modifier.weight(1f))
    }

    dialogoIngreso?.let { esIngreso ->
        DialogoMovimiento(
            titulo = if (esIngreso) "Agregar ingreso" else "Retirar dinero",
            onAceptar = { datos ->
                if (esIngreso) vm.agregar(datos.concepto, datos.monto)
                else vm.retirar(datos.concepto, datos.monto)
                dialogoIngreso = null
            },
            onCancelar = { dialogoIngreso = null },
        )
    }
}

@Composable
private fun ListaMovimientos(movimientos: List<Movimiento>, modifier: Modifier) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(Colores.panel, RoundedCornerShape(16.dp))
            .padding(12.dp),
    ) {
        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Text("Movimientos:", color = Colores.texto, fontWeight = FontWeight.SemiBold)
            Text("Dinero:", color = Colores.texto, fontWeight = FontWeight.SemiBold)
        }
        Spacer(Modifier.height(8.dp))
        if (movimientos.isEmpty()) {
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text(
                    "Aún no tienes movimientos.\nToca Agregar para empezar.",
                    color = Colores.textoSuave,
                    textAlign = TextAlign.Center,
                )
            }
        } else {
            LazyColumn {
                items(movimientos, key = { it.id }) { ItemMovimiento(it) }
            }
        }
    }
}
