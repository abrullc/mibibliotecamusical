package com.abrullc.mibibliotecamusical.retrofit.services

import com.abrullc.mibibliotecamusical.common.utils.Constants
import com.abrullc.mibibliotecamusical.retrofit.entities.Podcast
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface PodcastService {
    @GET(Constants.PODCASTS_USUARIO_PATH)
    suspend fun getPodcastsUsuario(@Path("id") id: Int): Response<MutableList<Podcast>>
}