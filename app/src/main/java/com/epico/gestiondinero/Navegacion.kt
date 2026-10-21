package com.epico.gestiondinero

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import com.epico.gestiondinero.tema.Colores
import com.epico.gestiondinero.viewmodels.GastoViewModel
import com.epico.gestiondinero.viewmodels.InicioViewModel
import com.epico.gestiondinero.viewmodels.MetasViewModel
import com.epico.gestiondinero.views.BarraNavegacion
import com.epico.gestiondinero.views.GastoEsperadoView
import com.epico.gestiondinero.views.InicioView
import com.epico.gestiondinero.views.MetasView

/** Crea los ViewModel con la factory y muestra la vista elegida. */
@Composable
fun Navegacion(factory: ViewModelProvider.Factory) {
    var indice by rememberSaveable { mutableIntStateOf(1) } // arranca en Inicio
    val inicioVm: InicioViewModel = viewModel(factory = factory)
    val gastoVm: GastoViewModel = viewModel(factory = factory)
    val metasVm: MetasViewModel = viewModel(factory = factory)

    Box(
        Modifier
            .fillMaxSize()
            .background(Brush.verticalGradient(listOf(Colores.fondoArriba, Colores.fondoAbajo)))
    ) {
        Scaffold(
            containerColor = Color.Transparent,
            contentColor = Colores.texto,
            bottomBar = { BarraNavegacion(indice) { indice = it } },
        ) { padding ->
            Box(Modifier.padding(padding).statusBarsPadding()) {
                when (indice) {
                    0 -> GastoEsperadoView(gastoVm)
                    1 -> InicioView(inicioVm)
                    else -> MetasView(metasVm)
                }
            }
        }
    }
}
