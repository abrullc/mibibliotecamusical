package com.abrullc.mibibliotecamusical.homeModule

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.abrullc.mibibliotecamusical.BibliotecaMusicalApplication
import com.abrullc.mibibliotecamusical.R
import com.abrullc.mibibliotecamusical.common.utils.Constants
import com.abrullc.mibibliotecamusical.databinding.FragmentHomeBinding
import com.abrullc.mibibliotecamusical.homeModule.adapters.AlbumListAdapter
import com.abrullc.mibibliotecamusical.homeModule.adapters.PlaylistListAdapter
import com.abrullc.mibibliotecamusical.homeModule.adapters.PodcastListAdapter
import com.abrullc.mibibliotecamusical.retrofit.services.AlbumService
import com.abrullc.mibibliotecamusical.retrofit.services.PlaylistService
import com.abrullc.mibibliotecamusical.retrofit.services.PodcastService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class HomeFragment : Fragment() {
    private lateinit var mBinding: FragmentHomeBinding

    private lateinit var mPlaylistAdapter: PlaylistListAdapter
    private lateinit var mPodcastAdapter: PodcastListAdapter
    private lateinit var mAlbumAdapter: AlbumListAdapter

    private lateinit var mPlaylistsLinearLayoutManager: RecyclerView.LayoutManager
    private lateinit var mPodcastsLinearLayoutManager: RecyclerView.LayoutManager
    private lateinit var mAlbumsLinearLayoutManager: RecyclerView.LayoutManager
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding = FragmentHomeBinding.inflate(inflater, container, false)

        getData()

        setupRecyclerView()

        return mBinding.root
    }

    private fun setupRecyclerView() {
        mPlaylistAdapter = PlaylistListAdapter()
        mPodcastAdapter = PodcastListAdapter()
        mAlbumAdapter = AlbumListAdapter()

        mPlaylistsLinearLayoutManager = LinearLayoutManager(this.context, LinearLayoutManager.HORIZONTAL, false)
        mPodcastsLinearLayoutManager = LinearLayoutManager(this.context, LinearLayoutManager.HORIZONTAL, false)
        mAlbumsLinearLayoutManager = LinearLayoutManager(this.context, LinearLayoutManager.HORIZONTAL, false)

        with(mBinding) {
            rcvPlaylists.apply {
                setHasFixedSize(true)
                layoutManager = mPlaylistsLinearLayoutManager
                adapter = mPlaylistAdapter
            }

            rcvPodcasts.apply {
                setHasFixedSize(true)
                layoutManager = mPodcastsLinearLayoutManager
                adapter = mPodcastAdapter
            }

            rcvAlbums.apply {
                setHasFixedSize(true)
                layoutManager = mAlbumsLinearLayoutManager
                adapter = mAlbumAdapter
            }
        }
    }

    private fun getData() {
        getPlaylistsUsuario()
        getPodcastsUsuario()
        getAlbumsUsuario()
    }

    private fun getPlaylistsUsuario() {
        val retrofit = Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val service = retrofit.create(PlaylistService::class.java)

        lifecycleScope.launch {
            try {
                val result = service.getPlaylistsUsuario(BibliotecaMusicalApplication.usuario.id)
                val playlistsUsuario = result.body()!!

                withContext(Dispatchers.Main) {
                    mPlaylistAdapter.submitList(playlistsUsuario)
                }

            } catch (e: Exception) {
                (e as? HttpException)?.let {
                    when(it.code()) {
                        400 -> {
                            //updateUI(getString(R.string.main_error_server))
                        }
                        else ->
                            Toast.makeText(context, R.string.error_general_peticion, Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

    private fun getPodcastsUsuario() {
        val retrofit = Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val service = retrofit.create(PodcastService::class.java)

        lifecycleScope.launch {
            try {
                val result = service.getPodcastsUsuario(BibliotecaMusicalApplication.usuario.id)
                val podcastsUsuario = result.body()!!

                withContext(Dispatchers.Main) {
                    mPodcastAdapter.submitList(podcastsUsuario)
                }

            } catch (e: Exception) {
                (e as? HttpException)?.let {
                    when(it.code()) {
                        400 -> {
                            //updateUI(getString(R.string.main_error_server))
                        }
                        else ->
                            Toast.makeText(context, R.string.error_general_peticion, Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

    private fun getAlbumsUsuario() {
        val retrofit = Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val service = retrofit.create(AlbumService::class.java)

        lifecycleScope.launch {
            try {
                val result = service.getAlbumsUsuario(BibliotecaMusicalApplication.usuario.id)
                val albumsUsuario = result.body()!!

                withContext(Dispatchers.Main) {
                    mAlbumAdapter.submitList(albumsUsuario)
                }

            } catch (e: Exception) {
                (e as? HttpException)?.let {
                    when(it.code()) {
                        400 -> {
                            //updateUI(getString(R.string.main_error_server))
                        }
                        else ->
                            Toast.makeText(context, R.string.error_general_peticion, Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }
}