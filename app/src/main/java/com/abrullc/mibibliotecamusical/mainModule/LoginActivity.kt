package com.abrullc.mibibliotecamusical.mainModule

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.abrullc.mibibliotecamusical.R
import com.abrullc.mibibliotecamusical.common.utils.Constants
import com.abrullc.mibibliotecamusical.databinding.ActivityLoginBinding
import com.abrullc.mibibliotecamusical.retrofit.UsuarioService
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        loadImgPortada("https://imageio.forbes.com/specials-images/imageserve/568781608/0x0.jpg?format=jpg&height=900&width=1600&fit=bounds")

        //setupRecyclerView()

        binding.btnLogin.setOnClickListener {
            val username = binding.etUsername.text.toString()
            val password = binding.etPassword.text.toString()

            checkUserFields(username, password)

            checkUsuario(username, password)
        }
    }

    /*private fun setupRecyclerView() {
        //configuracion del adapter

        getCanciones()
    }*/

    private fun checkUsuario(loginUsername: String, loginPassword: String) {
        val retrofit = Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val service = retrofit.create(UsuarioService::class.java)

        lifecycleScope.launch {
            try {
                val result = service.getUsers()
                val usuarios = result.body()!!

                // si es usuario compruebo que es el mismo que ha hecho login
                // si es correcto y coincide hago un intent a la activiy de lo fragments
                withContext(Dispatchers.Main) {
                    for (usuario in usuarios) {
                        if (usuario.username == loginUsername && usuario.password == loginPassword) {
                            Toast.makeText(this@LoginActivity, "Bienvenido ${usuario.username}!", Toast.LENGTH_LONG).show()
                            goToMain()
                        }
                    }

                    errorAlertDialog("El usuario y/o contraseña introducidos son incorrectos")
                }

            } catch (e: Exception) {
                (e as? HttpException)?.let {
                    errorAlertDialog("Error desconocido en el login")
                    /*when(it.code()) {
                        400 -> {
                            updateUI(getString(R.string.main_error_server))
                        }
                        else ->
                            updateUI(getString(R.string.main_error_response))
                    }*/
                }
            }
        }
    }
     /*private fun getCanciones() {
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
                     //randomAdapter.submitList(canciones)
                 }

             } catch (//tratar excepcion) {
             // ...
         }
     }*/

    private fun goToMain() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }

    private fun loadImgPortada(url: String) {
        Glide.with(this)
            .load(url)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .centerCrop()
            .into(binding.imgPortada)
    }

    private fun checkUserFields(username: String, password: String): Boolean {
        if (username.isEmpty()) {
            errorAlertDialog("El campo del nombre de usuario está vacío.")
            return false
        }

        if (password.isEmpty()) {
            errorAlertDialog("El campo de la contraseña está vacío.")
            return false
        }

        return true
    }

    private fun focusChangeListener(layout: TextInputLayout, textInput: TextInputEditText) {
        textInput.onFocusChangeListener = View.OnFocusChangeListener { view, focused ->
            var errorStr: String? = null

            if (!focused) {
                if(textInput.text.toString().isEmpty()) {
                    errorStr = "Este campo es obligatorio"
                }
            }
            layout.error = errorStr
        }
    }

    private fun errorAlertDialog(texto: String) {
        MaterialAlertDialogBuilder(this@LoginActivity)
            .setTitle(R.string.dialog_error_title)
            .setMessage(texto)
            .setPositiveButton(R.string.dialog_confirm, null)
            .setCancelable(false)
            .show()
    }
}