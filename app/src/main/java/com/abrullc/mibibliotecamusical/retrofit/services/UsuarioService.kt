package com.abrullc.mibibliotecamusical.retrofit.services

import com.abrullc.mibibliotecamusical.common.utils.Constants
import com.abrullc.mibibliotecamusical.retrofit.entities.Usuario
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface UsuarioService {
    @GET(Constants.USUARIOS_PATH)
    suspend fun getUsuarios(): Response<MutableList<Usuario>>

    @GET(Constants.USUARIO_PATH)
    suspend fun getUsuario(@Path("id") id: Int): Response<Usuario>

    @POST(Constants.USUARIOS_PATH)
    suspend fun postUsuario(@Body usuario: Usuario): Response<Usuario>
}