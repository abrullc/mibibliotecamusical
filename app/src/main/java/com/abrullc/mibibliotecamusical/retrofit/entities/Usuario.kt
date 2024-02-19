package com.abrullc.mibibliotecamusical.retrofit.entities

import java.util.Date

data class Usuario(
    val id: Int,
    val username: String,
    val password: String,
    val email: String,
    val genero: String,
    val fechaNacimiento: Date,
    val pais: String,
    val codigoPostal: String
)
