package com.abrullc.mibibliotecamusical.retrofit

import com.abrullc.mibibliotecamusical.common.utils.Constants
import retrofit2.Response
import retrofit2.http.GET

interface UsuarioService {
    @GET(Constants.USERS_PATH)
    suspend fun getUsers(): Response<MutableList<Usuario>>
}