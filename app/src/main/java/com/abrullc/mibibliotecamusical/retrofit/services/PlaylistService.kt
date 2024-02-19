package com.abrullc.mibibliotecamusical.retrofit.services

import com.abrullc.mibibliotecamusical.common.utils.Constants
import com.abrullc.mibibliotecamusical.retrofit.entities.Playlist
import retrofit2.Response
import retrofit2.http.GET

interface PlaylistService {
    @GET(Constants.PLAYLISTS_USUARIO_PATH)
    suspend fun getPlaylistsUsuario(id: Int): Response<MutableList<Playlist>>
}