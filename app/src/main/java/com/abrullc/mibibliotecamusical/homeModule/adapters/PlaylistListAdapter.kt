package com.abrullc.mibibliotecamusical.homeModule.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.abrullc.mibibliotecamusical.R
import com.abrullc.mibibliotecamusical.common.utils.CommonFunctions
import com.abrullc.mibibliotecamusical.databinding.ItemPlaylistBinding
import com.abrullc.mibibliotecamusical.retrofit.entities.Playlist

class PlaylistListAdapter(): ListAdapter<Playlist, RecyclerView.ViewHolder>(PlaylistDiffCallback()) {
    private lateinit var context: Context
    private lateinit var commonFunctions: CommonFunctions

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val binding = ItemPlaylistBinding.bind(view)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        context = parent.context

        commonFunctions = CommonFunctions()

        val view = LayoutInflater.from(context).inflate(R.layout.item_playlist, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val playlist = getItem(position)

        with(holder as ViewHolder) {
            with(binding) {
                tvTitulo.text = playlist.titulo.capitalize()

                if (playlist.numeroCanciones != null && playlist.numeroCanciones > 0) {
                    if (playlist.numeroCanciones == 1) {
                        tvNumeroCanciones.text = context.getString(R.string.one_song)
                    } else {
                        tvNumeroCanciones.text = playlist.numeroCanciones.toString()+ context.getString(R.string.songs)
                    }
                } else {
                    tvNumeroCanciones.text = context.getString(R.string.without_songs)
                }

                if (playlist.fechaCreacion != null) {
                    tvFechaCreacion.text = context.getString(R.string.text_created_on)+commonFunctions.parseDate(playlist.fechaCreacion)
                } else {
                    tvFechaCreacion.text = context.getString(R.string.not_specified_create_date)
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