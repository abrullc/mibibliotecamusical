package com.abrullc.mibibliotecamusical.findModule

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.abrullc.mibibliotecamusical.R
import com.abrullc.mibibliotecamusical.common.utils.Constants
import com.abrullc.mibibliotecamusical.databinding.FragmentFindBinding
import com.abrullc.mibibliotecamusical.findModule.adapters.CancionListAdapter
import com.abrullc.mibibliotecamusical.retrofit.services.CancionService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class FindFragment : Fragment() {
    private lateinit var mBinding: FragmentFindBinding

    private lateinit var mCancionAdapter: CancionListAdapter

    private lateinit var mCancionLinearLayoutManager: RecyclerView.LayoutManager
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding = FragmentFindBinding.inflate(inflater, container, false)

        getCanciones()

        setupRecyclerView()

        return mBinding.root
    }

    private fun setupRecyclerView() {
        mCancionAdapter = CancionListAdapter()

        mCancionLinearLayoutManager = LinearLayoutManager(this.context, LinearLayoutManager.VERTICAL, false)

        with(mBinding) {
            rcvCanciones.apply {
                setHasFixedSize(true)
                layoutManager = mCancionLinearLayoutManager
                adapter = mCancionAdapter
            }
        }
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
}