package com.abrullc.mibibliotecamusical.findModule.adapters

import com.abrullc.mibibliotecamusical.retrofit.entities.Cancion

interface OnClickListener {
    fun onAddCancionToUserPlaylist(canion: Cancion)
}