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
import com.abrullc.mibibliotecamusical.databinding.ItemAlbumBinding
import com.abrullc.mibibliotecamusical.retrofit.entities.Album
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy

class AlbumListAdapter(): ListAdapter<Album, RecyclerView.ViewHolder>(AlbumDiffCallback()) {
    private lateinit var context: Context
    private lateinit var commonFunctions: CommonFunctions

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val binding = ItemAlbumBinding.bind(view)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        context = parent.context

        commonFunctions = CommonFunctions()

        val view = LayoutInflater.from(context).inflate(R.layout.item_album, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val album = getItem(position)

        with(holder as ViewHolder) {
            with(binding) {
                tvTitulo.text = album.titulo
                tvArtista.text = "Artista: "+album.artista.nombre
                tvAnyo.text = commonFunctions.parseDate(album.anyo)

                if (album.imagen != null && album.imagen != "") {
                    Glide.with(context)
                        .load(album.imagen)
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .centerCrop()
                        .into(imageAlbum)
                }
            }
        }
    }

    class AlbumDiffCallback : DiffUtil.ItemCallback<Album>() {
        override fun areItemsTheSame(oldItem: Album, newItem: Album): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Album, newItem: Album): Boolean {
            return oldItem == newItem
        }
    }
}