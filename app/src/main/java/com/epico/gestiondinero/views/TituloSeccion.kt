package com.epico.gestiondinero.views

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.epico.gestiondinero.tema.Colores

@Composable
fun TituloSeccion(texto: String) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(Colores.tarjeta, RoundedCornerShape(16.dp))
            .padding(vertical = 14.dp),
        contentAlignment = Alignment.Center,
    ) {
        Text(texto, color = Colores.texto, fontSize = 24.sp, fontWeight = FontWeight.Medium)
    }
}
