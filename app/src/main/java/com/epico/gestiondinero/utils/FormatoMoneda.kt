package com.epico.gestiondinero.utils

import java.util.Locale
import kotlin.math.abs

/** Siempre dos decimales, pesos mexicanos: 2894.0 -> $2,894.00 */
fun formatoMoneda(valor: Double): String {
    val texto = String.format(Locale.US, "%,.2f", abs(valor))
    return (if (valor < 0) "-" else "") + "\$" + texto
}
