package com.abrullc.mibibliotecamusical.retrofit.entities

data class Cancion(
    val id: Int,
    val titulo: String,
    val duracion: Int,
    val ruta: String,
    val numeroReproducciones: Int,
    val album: Album
)
