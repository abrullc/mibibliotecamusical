package com.abrullc.mibibliotecamusical.retrofit.services

import com.abrullc.mibibliotecamusical.common.utils.Constants
import com.abrullc.mibibliotecamusical.retrofit.entities.Cancion
import retrofit2.Response
import retrofit2.http.GET

interface CancionService {
    @GET(Constants.CANCIONES_PATH)
    suspend fun getCanciones(): Response<MutableList<Cancion>>
}