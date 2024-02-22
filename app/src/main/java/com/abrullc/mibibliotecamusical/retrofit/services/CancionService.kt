package com.abrullc.mibibliotecamusical.retrofit.services

import com.abrullc.mibibliotecamusical.common.utils.Constants
import com.abrullc.mibibliotecamusical.retrofit.entities.Cancion
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface CancionService {
    @GET(Constants.CANCIONES_PATH)
    suspend fun getCanciones(): Response<MutableList<Cancion>>

    @POST(Constants.CANCION_TO_PLAYLIST_PATH)
    suspend fun postCancionPlaylist(
        @Path("idPlaylist") idPlaylist: Int,
        @Path("idCancion") idCancion: Int
    )
}