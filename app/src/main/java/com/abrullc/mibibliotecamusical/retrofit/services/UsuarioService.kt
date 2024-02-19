package com.abrullc.mibibliotecamusical.retrofit.services

import com.abrullc.mibibliotecamusical.common.utils.Constants
import com.abrullc.mibibliotecamusical.retrofit.entities.Usuario
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.POST

interface UsuarioService {
    @GET(Constants.USUARIOS_PATH)
    suspend fun getUsuarios(): Response<MutableList<Usuario>>
    @POST(Constants.USUARIOS_PATH)
    suspend fun postUsuario(): Response<MutableList<Usuario>>
}