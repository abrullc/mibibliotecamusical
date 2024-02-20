package com.abrullc.mibibliotecamusical.common.utils

import com.abrullc.mibibliotecamusical.BibliotecaMusicalApplication

object Constants {
    // RUTAS
    const val BASE_URL = "http://lab1.navelsystems.com"
    const val USUARIOS_PATH = "/usuarios"
    const val PLAYLISTS_USUARIO_PATH = "/usuario/{id}/playlists"
    const val PODCASTS_USUARIO_PATH = "/usuario/{id}/podcasts"
    const val ALBUMS_USUARIO_PATH = "/usuario/{id}/albums"

    // PROPIEDADES
    const val ID_PROPERTY = "id"
    const val TOKEN_PROPERTY = "token"
    const val DATA_PROPERTY = "data"
    const val SUPPORT_PROPERTY = "support"

    // RESULTADOS
    const val EMAIL_PARAM = "email"
    const val PASSWORD_PARAM = "password"
    const val ERROR_VALUE = "fail"
}