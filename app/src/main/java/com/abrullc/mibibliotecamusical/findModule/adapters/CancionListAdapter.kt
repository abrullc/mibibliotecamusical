package com.abrullc.mibibliotecamusical.findModule.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.abrullc.mibibliotecamusical.R
import com.abrullc.mibibliotecamusical.databinding.ItemCancionBinding
import com.abrullc.mibibliotecamusical.retrofit.entities.Cancion

class CancionListAdapter(): ListAdapter<Cancion, RecyclerView.ViewHolder>(CancionDiffCallback()) {
    private lateinit var context: Context

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val binding = ItemCancionBinding.bind(view)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        context = parent.context

        val view = LayoutInflater.from(context).inflate(R.layout.item_cancion, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val cancion = getItem(position)

        with(holder as ViewHolder) {
            with(binding) {
                tvTitulo.text = cancion.titulo
                tvArtista.text = "Artista: "+cancion.album.artista.nombre
                tvAlbum.text = "Álbum: "+cancion.album.titulo
                tvDuration.text = cancion.duracion.toString()
                tvReproductions.text = cancion.numeroReproducciones.toString()
            }
        }
    }

    class CancionDiffCallback : DiffUtil.ItemCallback<Cancion>() {
        override fun areItemsTheSame(oldItem: Cancion, newItem: Cancion): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Cancion, newItem: Cancion): Boolean {
            return oldItem == newItem
        }
    }
}