package com.abrullc.mibibliotecamusical.retrofit.entities

import java.util.Date

data class Album(
    val id: Int,
    val titulo: String,
    val imagen: String,
    val patrocinado: Boolean,
    val fechaInicioPatrocinio: Date,
    val fechaFinPatrocinio: Date,
    val anyo: Date,
    val artista: Artista
)
