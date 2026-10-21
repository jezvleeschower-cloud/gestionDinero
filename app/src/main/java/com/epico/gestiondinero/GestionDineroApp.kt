package com.epico.gestiondinero

import android.app.Application

class GestionDineroApp : Application() {
    lateinit var contenedor: AppContenedor
        private set

    override fun onCreate() {
        super.onCreate()
        contenedor = AppContenedor(this)
    }
}
