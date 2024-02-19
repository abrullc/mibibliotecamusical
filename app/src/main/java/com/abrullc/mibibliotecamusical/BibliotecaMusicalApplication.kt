package com.abrullc.mibibliotecamusical

import android.app.Application
import com.abrullc.mibibliotecamusical.retrofit.entities.Usuario

class BibliotecaMusicalApplication: Application() {
    companion object {
        lateinit var usuario: Usuario
    }
}