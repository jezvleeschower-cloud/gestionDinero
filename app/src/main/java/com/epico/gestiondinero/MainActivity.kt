package com.epico.gestiondinero

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.MaterialTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val contenedor = (application as GestionDineroApp).contenedor
        setContent {
            MaterialTheme {
                Navegacion(contenedor.viewModelFactory)
            }
        }
    }
}
