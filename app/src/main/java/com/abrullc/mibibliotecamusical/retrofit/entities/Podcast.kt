package com.abrullc.mibibliotecamusical.retrofit.entities

import java.util.Date

data class Podcast(
    val id: Int,
    val titulo: String,
    val imagen: String,
    val descripcion: String,
    val anyo: Date
)
