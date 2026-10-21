package com.epico.gestiondinero.views

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.epico.gestiondinero.tema.Colores
import com.epico.gestiondinero.utils.DatosMovimiento

/** Pide concepto y/o monto. Si el usuario cancela, llama a onCancelar. */
@Composable
fun DialogoMovimiento(
    titulo: String,
    onAceptar: (DatosMovimiento) -> Unit,
    onCancelar: () -> Unit,
    pedirConcepto: Boolean = true,
    pedirMonto: Boolean = true,
) {
    var concepto by remember { mutableStateOf("") }
    var monto by remember { mutableStateOf("") }
    var error by remember { mutableStateOf<String?>(null) }

    val colores = OutlinedTextFieldDefaults.colors(
        focusedTextColor = Color.White,
        unfocusedTextColor = Color.White,
        focusedLabelColor = Colores.textoSuave,
        unfocusedLabelColor = Colores.textoSuave,
        focusedBorderColor = Colores.acento,
        unfocusedBorderColor = Colores.textoSuave,
        cursorColor = Color.White,
    )

    fun validar() {
        val textoConcepto = concepto.trim()
        if (pedirConcepto && textoConcepto.isEmpty()) {
            error = "Escribe un concepto"
            return
        }
        var valor = 0.0
        if (pedirMonto) {
            valor = monto.replace(',', '.').toDoubleOrNull() ?: 0.0
            if (valor <= 0) {
                error = "Escribe un monto mayor a 0"
                return
            }
        }
        onAceptar(DatosMovimiento(textoConcepto, valor))
    }

    AlertDialog(
        onDismissRequest = onCancelar,
        containerColor = Colores.tarjeta,
        title = { Text(titulo, color = Color.White) },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                if (pedirConcepto) {
                    OutlinedTextField(
                        value = concepto,
                        onValueChange = { concepto = it },
                        label = { Text("Concepto") },
                        singleLine = true,
                        colors = colores,
                    )
                }
                if (pedirMonto) {
                    OutlinedTextField(
                        value = monto,
                        onValueChange = { monto = it },
                        label = { Text("Monto") },
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                        colors = colores,
                    )
                }
                error?.let { Text(it, color = Colores.egreso, fontSize = 13.sp) }
            }
        },
        confirmButton = {
            TextButton(onClick = { validar() }) {
                Text("Aceptar", color = Colores.cumplido, fontWeight = FontWeight.Bold)
            }
        },
        dismissButton = {
            TextButton(onClick = onCancelar) {
                Text("Cancelar", color = Colores.textoSuave)
            }
        },
    )
}
