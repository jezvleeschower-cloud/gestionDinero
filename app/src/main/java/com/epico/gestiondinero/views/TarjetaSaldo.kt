package com.epico.gestiondinero.views

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.epico.gestiondinero.tema.Colores
import com.epico.gestiondinero.utils.formatoMoneda

/** Si no recibe ingresos/egresos, solo muestra el saldo. */
@Composable
fun TarjetaSaldo(saldo: Double, ingresos: Double? = null, egresos: Double? = null) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Colores.tarjeta, RoundedCornerShape(20.dp))
            .padding(16.dp),
    ) {
        Text("Tu saldo es", color = Colores.textoSuave, fontSize = 13.sp)
        Spacer(Modifier.height(8.dp))
        Text(
            formatoMoneda(saldo),
            color = Colores.texto,
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.align(Alignment.CenterHorizontally),
        )
        if (ingresos != null && egresos != null) {
            Spacer(Modifier.height(12.dp))
            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                Resumen("Ingresos", ingresos, Colores.ingreso, Modifier.weight(1f))
                Resumen("Egresos", egresos, Colores.egreso, Modifier.weight(1f))
            }
        }
    }
}

@Composable
private fun Resumen(titulo: String, monto: Double, color: Color, modifier: Modifier) {
    Column(
        modifier = modifier
            .background(Color.White.copy(alpha = 0.12f), RoundedCornerShape(14.dp))
            .padding(vertical = 8.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(titulo, color = Colores.textoSuave, fontSize = 12.sp)
        Text(formatoMoneda(monto), color = color, fontWeight = FontWeight.Bold)
    }
}
