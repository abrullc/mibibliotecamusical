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
import com.abrullc.mibibliotecamusical.databinding.ItemPodcastBinding
import com.abrullc.mibibliotecamusical.retrofit.entities.Podcast
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy

class PodcastListAdapter(): ListAdapter<Podcast, RecyclerView.ViewHolder>(PodcastDiffCallback()) {
    private lateinit var context: Context
    private lateinit var commonFunctions: CommonFunctions

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val binding = ItemPodcastBinding.bind(view)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        context = parent.context

        commonFunctions = CommonFunctions()

        val view = LayoutInflater.from(context).inflate(R.layout.item_podcast, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val podcast = getItem(position)

        with(holder as ViewHolder) {
            with(binding) {
                tvTitulo.text = podcast.titulo
                if (podcast.anyo != null) {
                    tvAnyo.text = "Año: "+podcast.anyo.year
                } else {
                    tvAnyo.text = "Sin año"
                }

                if (podcast.imagen != null && commonFunctions.validateURL(podcast.imagen)) {
                    Glide.with(context)
                        .load(podcast.imagen)
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .centerCrop()
                        .into(imagePodcast)
                }
            }
        }
    }

    class PodcastDiffCallback : DiffUtil.ItemCallback<Podcast>() {
        override fun areItemsTheSame(oldItem: Podcast, newItem: Podcast): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Podcast, newItem: Podcast): Boolean {
            return oldItem == newItem
        }
    }
}