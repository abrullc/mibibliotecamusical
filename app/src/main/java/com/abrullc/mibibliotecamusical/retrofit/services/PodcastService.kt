package com.abrullc.mibibliotecamusical.retrofit.services

import com.abrullc.mibibliotecamusical.common.utils.Constants
import com.abrullc.mibibliotecamusical.retrofit.entities.Podcast
import retrofit2.Response
import retrofit2.http.GET

interface PodcastService {
    @GET(Constants.PODCASTS_USUARIO_PATH)
    suspend fun getPodcastsUsuario(id: Int): Response<MutableList<Podcast>>
}