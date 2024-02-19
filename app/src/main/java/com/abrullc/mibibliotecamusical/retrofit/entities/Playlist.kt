package com.abrullc.mibibliotecamusical.retrofit.entities

import java.util.Date

data class Playlist(
    val id: Int,
    val titulo: String,
    val numeroCanciones: Int,
    val fechaCreacion: Date
)
