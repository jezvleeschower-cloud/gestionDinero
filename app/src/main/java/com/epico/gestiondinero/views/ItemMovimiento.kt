package com.epico.gestiondinero.views

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.epico.gestiondinero.data.Movimiento
import com.epico.gestiondinero.tema.Colores
import com.epico.gestiondinero.utils.formatoMoneda

@Composable
fun ItemMovimiento(movimiento: Movimiento) {
    val signo = if (movimiento.esIngreso) "+" else "-"
    val color = if (movimiento.esIngreso) Colores.ingreso else Colores.egreso
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 6.dp)
            .background(Color.Black.copy(alpha = 0.25f), RoundedCornerShape(10.dp))
            .padding(12.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(movimiento.concepto, color = Colores.texto, modifier = Modifier.weight(1f))
        Text(
            signo + formatoMoneda(movimiento.monto),
            color = color,
            fontWeight = FontWeight.Bold,
        )
    }
}
