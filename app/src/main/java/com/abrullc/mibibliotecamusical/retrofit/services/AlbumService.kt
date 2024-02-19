package com.abrullc.mibibliotecamusical.retrofit.services

import com.abrullc.mibibliotecamusical.common.utils.Constants
import com.abrullc.mibibliotecamusical.retrofit.entities.Album
import com.abrullc.mibibliotecamusical.retrofit.entities.Usuario
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.POST

interface AlbumService {
    @GET(Constants.ALBUMS_USUARIO_PATH)
    suspend fun getAlbumsUsuario(id: Int): Response<MutableList<Album>>
}