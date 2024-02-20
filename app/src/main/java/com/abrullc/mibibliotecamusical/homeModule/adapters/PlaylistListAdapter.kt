package com.abrullc.mibibliotecamusical.homeModule.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.abrullc.mibibliotecamusical.R
import com.abrullc.mibibliotecamusical.databinding.ItemPlaylistBinding
import com.abrullc.mibibliotecamusical.retrofit.entities.Playlist

class PlaylistListAdapter(): ListAdapter<Playlist, RecyclerView.ViewHolder>(PlaylistDiffCallback()) {
    private lateinit var context: Context

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val binding = ItemPlaylistBinding.bind(view)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        context = parent.context

        val view = LayoutInflater.from(context).inflate(R.layout.item_playlist, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val playlist = getItem(position)

        with(holder as ViewHolder) {
            with(binding) {
                tvTitulo.text = playlist.titulo
                if (playlist.numeroCanciones != null || playlist.numeroCanciones > 0) {
                    tvNumeroCanciones.text = playlist.numeroCanciones.toString()+" canciones"
                } else {
                    tvFechaCreacion.text = "Sin canciones"
                }

                if (playlist.fechaCreacion != null) {
                    tvFechaCreacion.text = "Creada el "+playlist.fechaCreacion.day+"/"+playlist.fechaCreacion.month+"/"+playlist.fechaCreacion.year
                } else {
                    tvFechaCreacion.text = "Fecha de creación no especificada"
                }
            }
        }
    }

    class PlaylistDiffCallback : DiffUtil.ItemCallback<Playlist>() {
        override fun areItemsTheSame(oldItem: Playlist, newItem: Playlist): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Playlist, newItem: Playlist): Boolean {
            return oldItem == newItem
        }
    }
}