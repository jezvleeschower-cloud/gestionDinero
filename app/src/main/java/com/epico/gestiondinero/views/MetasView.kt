package com.epico.gestiondinero.views

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.epico.gestiondinero.data.Meta
import com.epico.gestiondinero.tema.Colores
import com.epico.gestiondinero.viewmodels.MetasViewModel

@Composable
fun MetasView(vm: MetasViewModel) {
    val estado by vm.uiState.collectAsState()
    var dialogoNueva by remember { mutableStateOf(false) }
    var metaSeleccionada by remember { mutableStateOf<Meta?>(null) }

    Column(
        Modifier
            .fillMaxSize()
            .padding(start = 20.dp, top = 16.dp, end = 20.dp, bottom = 12.dp)
    ) {
        TarjetaSaldo(estado.saldo)
        Spacer(Modifier.height(16.dp))
        TituloSeccion("Metas")
        Spacer(Modifier.height(12.dp))

        Box(Modifier.weight(1f).fillMaxWidth()) {
            if (estado.metas.isEmpty()) {
                Text(
                    "Aún no tienes metas.\nCrea una para empezar.",
                    color = Colores.textoSuave,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.align(Alignment.Center),
                )
            } else {
                LazyColumn(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    items(estado.metas, key = { it.id }) { meta ->
                        TarjetaMeta(meta) { metaSeleccionada = meta }
                    }
                }
            }
        }
        Spacer(Modifier.height(8.dp))
        BotonPrincipal("Nueva meta", { dialogoNueva = true }, Modifier.fillMaxWidth())
    }

    if (dialogoNueva) {
        DialogoMovimiento(
            titulo = "Nueva meta",
            pedirMonto = false,
            onAceptar = { vm.crearMeta(it.concepto); dialogoNueva = false },
            onCancelar = { dialogoNueva = false },
        )
    }

    metaSeleccionada?.let { meta ->
        DialogoMetaCumplida(
            meta = meta.titulo,
            onResultado = { cumplida ->
                vm.actualizarMeta(meta, cumplida)
                metaSeleccionada = null
            },
            onCerrar = { metaSeleccionada = null },
        )
    }
}

@Composable
private fun TarjetaMeta(meta: Meta, onClick: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(Colores.panel)
            .clickable(onClick = onClick)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            meta.titulo,
            color = Colores.texto,
            fontSize = 20.sp,
            textAlign = TextAlign.Center,
        )
        Spacer(Modifier.height(10.dp))
        Text(
            if (meta.cumplida) "Cumplido" else "Pendiente",
            color = if (meta.cumplida) Color.Black else Color.White,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .background(
                    if (meta.cumplida) Colores.cumplido else Color.White.copy(alpha = 0.24f),
                    RoundedCornerShape(12.dp),
                )
                .padding(horizontal = 16.dp, vertical = 4.dp),
        )
    }
}
