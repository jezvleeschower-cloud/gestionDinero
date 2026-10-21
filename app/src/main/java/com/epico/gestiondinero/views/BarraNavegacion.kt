package com.epico.gestiondinero.views

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BarChart
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.TrackChanges
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.epico.gestiondinero.tema.Colores

/** 0 = Gasto esperado, 1 = Inicio, 2 = Metas. */
@Composable
fun BarraNavegacion(indice: Int, onSeleccion: (Int) -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.Black.copy(alpha = 0.4f))
            .navigationBarsPadding()
            .padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceEvenly,
    ) {
        ItemBarra(Icons.Filled.BarChart, "Gasto esperado", indice == 0) { onSeleccion(0) }
        ItemBarra(Icons.Filled.Home, "Inicio", indice == 1) { onSeleccion(1) }
        ItemBarra(Icons.Filled.TrackChanges, "Metas", indice == 2) { onSeleccion(2) }
    }
}

@Composable
private fun ItemBarra(
    icono: ImageVector,
    descripcion: String,
    activo: Boolean,
    onClick: () -> Unit,
) {
    IconButton(
        onClick = onClick,
        modifier = Modifier
            .size(56.dp)
            .background(if (activo) Colores.panel else Color.Transparent, CircleShape),
    ) {
        Icon(
            imageVector = icono,
            contentDescription = descripcion,
            tint = if (activo) Color.White else Colores.textoSuave,
            modifier = Modifier.size(34.dp),
        )
    }
}
