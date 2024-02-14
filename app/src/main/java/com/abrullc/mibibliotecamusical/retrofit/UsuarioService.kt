package com.abrullc.mibibliotecamusical.retrofit

import com.abrullc.mibibliotecamusical.common.utils.Constants
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.POST

interface UsuarioService {
    @GET(Constants.USUARIOS_PATH)
    suspend fun getUsuarios(): Response<MutableList<Usuario>>
    @POST(Constants.USUARIOS_PATH)
    suspend fun postUsuario(): Response<MutableList<Usuario>>
}