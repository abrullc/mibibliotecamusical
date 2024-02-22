package com.abrullc.mibibliotecamusical.common.utils

object Constants {
    // RUTAS
    const val BASE_URL = "http://lab1.navelsystems.com"
    const val USUARIOS_PATH = "/usuarios"
    const val USUARIO_PATH = "/usuario/{id}"
    const val PLAYLISTS_USUARIO_PATH = "/usuario/{id}/playlists"
    const val PODCASTS_USUARIO_PATH = "/usuario/{id}/podcasts"
    const val ALBUMS_USUARIO_PATH = "/usuario/{id}/albums"
    const val CANCIONES_PATH = "/canciones"
    const val CANCION_TO_PLAYLIST_PATH = "/playlist/{idPlaylist}/cancion/{idCancion}"
}