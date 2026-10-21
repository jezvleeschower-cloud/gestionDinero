package com.epico.gestiondinero.views

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.epico.gestiondinero.tema.Colores

/** onResultado(true) = cumplida, onResultado(false) = aún no. */
@Composable
fun DialogoMetaCumplida(
    meta: String,
    onResultado: (Boolean) -> Unit,
    onCerrar: () -> Unit,
) {
    AlertDialog(
        onDismissRequest = onCerrar,
        containerColor = Colores.tarjeta,
        title = { Text("¿Meta cumplida?", color = Color.White) },
        text = { Text(meta, color = Colores.textoSuave) },
        confirmButton = {
            BotonPrincipal(
                texto = "Cumplido",
                onClick = { onResultado(true) },
                color = Colores.cumplido,
                colorTexto = Color.Black,
            )
        },
        dismissButton = {
            BotonPrincipal(
                texto = "Aún no",
                onClick = { onResultado(false) },
                color = Colores.fallo,
                colorTexto = Color.Black,
            )
        },
    )
}
