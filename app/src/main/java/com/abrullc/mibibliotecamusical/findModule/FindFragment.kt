package com.abrullc.mibibliotecamusical.findModule

import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.abrullc.mibibliotecamusical.BibliotecaMusicalApplication
import com.abrullc.mibibliotecamusical.R
import com.abrullc.mibibliotecamusical.common.utils.CommonFunctions
import com.abrullc.mibibliotecamusical.common.utils.Constants
import com.abrullc.mibibliotecamusical.databinding.FragmentFindBinding
import com.abrullc.mibibliotecamusical.findModule.adapters.CancionListAdapter
import com.abrullc.mibibliotecamusical.findModule.adapters.OnClickListener
import com.abrullc.mibibliotecamusical.retrofit.entities.Cancion
import com.abrullc.mibibliotecamusical.retrofit.services.CancionService
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.textfield.TextInputEditText
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class FindFragment : Fragment(), OnClickListener {
    private lateinit var mBinding: FragmentFindBinding

    private lateinit var mCancionAdapter: CancionListAdapter

    private lateinit var mCancionLinearLayoutManager: RecyclerView.LayoutManager

    private lateinit var listaCanciones: MutableList<Cancion>

    private lateinit var commonFunctions: CommonFunctions
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding = FragmentFindBinding.inflate(inflater, container, false)

        commonFunctions = CommonFunctions()

        getCanciones()

        setupRecyclerView()

        setupSearchView()

        return mBinding.root
    }

    private fun setupRecyclerView() {
        mCancionAdapter = CancionListAdapter(this)

        mCancionLinearLayoutManager = LinearLayoutManager(this.context, LinearLayoutManager.VERTICAL, false)

        with(mBinding) {
            rcvCanciones.apply {
                setHasFixedSize(true)
                layoutManager = mCancionLinearLayoutManager
                adapter = mCancionAdapter
            }
        }
    }

    private fun setupSearchView() {
        /* Fuentes:
        * https://www.youtube.com/watch?v=l_LjUpbHxm0
        * https://www.youtube.com/watch?v=8q-4AJFlraI
        * https://www.youtube.com/watch?v=2I1NkJNBz9M */
        mBinding.svSongs.setOnQueryTextListener(
            object: SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    return false
                }

                override fun onQueryTextChange(newText: String): Boolean {
                    getCancionesByFilter(newText)
                    return true
                }
            }
        )
    }

    private fun getCanciones() {
        val retrofit = Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val service = retrofit.create(CancionService::class.java)

        lifecycleScope.launch {
            try {
                val result = service.getCanciones()
                val canciones = result.body()!!

                withContext(Dispatchers.Main) {
                    mCancionAdapter.submitList(canciones)
                    listaCanciones = canciones
                }

            } catch (e: Exception) {
                (e as? HttpException)?.let {
                    when(it.code()) {
                        400 -> {
                            Toast.makeText(context, R.string.main_error_server, Toast.LENGTH_SHORT).show()
                        }
                        else ->
                            Toast.makeText(context, R.string.error_general_peticion, Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

    private fun getCancionesByFilter(filter: String) {
        val filteredCanciones: MutableList<Cancion> = mutableListOf()

        for (cancion in listaCanciones) {
            if (cancion.titulo.toLowerCase().contains(filter.toLowerCase())) {
                filteredCanciones.add(cancion)
            }
        }

        mCancionAdapter.submitList(filteredCanciones)
    }

    override fun onAddCancionToUserPlaylist(cancion: Cancion) {
        val dialogView = layoutInflater.inflate(R.layout.dialog_user_playlists, null)

        context?.let {
            MaterialAlertDialogBuilder(it)
                .setTitle(R.string.dialog_add_song_to_playlist)
                .setView(dialogView)
                .setPositiveButton(R.string.dialog_confirm,
                    DialogInterface.OnClickListener { _, _ ->
                        val playlistTitle = dialogView.findViewById<TextInputEditText>(R.id.etPlaylistTitle).text.toString().trim()

                        addCancionToUserPlaylist(playlistTitle, cancion)
                    })
                .setNegativeButton(R.string.dialog_cancel, null)
                .setCancelable(true)
                .show()
        }
    }

    private fun addCancionToUserPlaylist(playlistTitle: String, cancion: Cancion) {
        var checked = false

        for (playlist in BibliotecaMusicalApplication.playlistsUsuario) {
            if (playlist.titulo.equals(playlistTitle, ignoreCase = true)) {
                checked = true

                val retrofit = Retrofit.Builder()
                    .baseUrl(Constants.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()

                val service = retrofit.create(CancionService::class.java)

                lifecycleScope.launch {
                    try {
                        service.postCancionPlaylist(playlist.id, cancion.id)

                        withContext(Dispatchers.Main) {
                            Toast.makeText(context, "La canción ${cancion.titulo} ha sido " +
                                    "añadida a la playlist ${playlist.titulo}", Toast.LENGTH_LONG).show()
                        }
                    } catch (e: Exception) {
                        (e as? HttpException)?.let {
                            when(it.code()) {
                                400 -> {
                                    Toast.makeText(context, R.string.main_error_server, Toast.LENGTH_SHORT).show()
                                }
                                else ->
                                    Toast.makeText(context, R.string.error_general_peticion, Toast.LENGTH_SHORT).show()
                            }
                        }
                    }
                }

                break
            }
        }

        if (!checked) {
            this.context?.let {
                commonFunctions.errorAlertDialog(getString(R.string.error_playlist), it)
            }
        }
    }
}