package com.abrullc.mibibliotecamusical.loginModule

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.abrullc.mibibliotecamusical.BibliotecaMusicalApplication
import com.abrullc.mibibliotecamusical.R
import com.abrullc.mibibliotecamusical.common.utils.CommonFunctions
import com.abrullc.mibibliotecamusical.common.utils.Constants
import com.abrullc.mibibliotecamusical.databinding.ActivityLoginBinding
import com.abrullc.mibibliotecamusical.mainModule.MainActivity
import com.abrullc.mibibliotecamusical.retrofit.entities.Usuario
import com.abrullc.mibibliotecamusical.retrofit.services.UsuarioService
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
import java.util.Date

class LoginActivity : AppCompatActivity() {
    private lateinit var mBinding: ActivityLoginBinding
    private lateinit var commonFunctions: CommonFunctions

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(mBinding.root)

        loadImgPortada("https://i.blogs.es/827c3a/spotify-0/1366_2000.jpg")

        commonFunctions = CommonFunctions()

        val preferences = getPreferences(MODE_PRIVATE)

        val rememberUsusario = preferences.getBoolean(getString(R.string.sp_remember_usuario), false)
        val spIdUsuario = preferences.getInt(getString(R.string.sp_id_usuario), 0)

        if (rememberUsusario) {
            getUsuario(spIdUsuario)
        }

        mBinding.btnLogin.setOnClickListener {
            val username = mBinding.etUsername.text.toString().trim()
            val password = mBinding.etPassword.text.toString().trim()

            checkUserFields(username, password)

            checkUsuario(username, password)
        }

        mBinding.newUser.setOnClickListener {
            createUser()
        }
    }

    private fun checkUsuario(loginUsername: String, loginPassword: String) {
        var checked = false

        val retrofit = Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val service = retrofit.create(UsuarioService::class.java)

        lifecycleScope.launch {
            try {
                val result = service.getUsuarios()
                val usuarios = result.body()!!

                // si es usuario compruebo que es el mismo que ha hecho login
                // si es correcto y coincide hago un intent a la activiy de lo fragments
                withContext(Dispatchers.Main) {
                    for (usuario in usuarios) {
                        if (usuario.username == loginUsername && usuario.password == loginPassword) {
                            BibliotecaMusicalApplication.usuario = usuario

                            checked = true
                            spLogin()
                            Toast.makeText(this@LoginActivity, "Bienvenido ${usuario.username}!", Toast.LENGTH_LONG).show()
                            goToMain()
                            break
                        }
                    }

                    if (!checked) {
                        commonFunctions.errorAlertDialog(getString(R.string.error_login), this@LoginActivity)
                    }
                }

            } catch (e: Exception) {
                (e as? HttpException)?.let {
                    when(it.code()) {
                        400 -> {
                            Toast.makeText(this@LoginActivity, R.string.main_error_server, Toast.LENGTH_SHORT).show()
                        }
                        else ->
                            Toast.makeText(this@LoginActivity, R.string.error_general_peticion, Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

    private fun getUsuario(idUsuario: Int) {
        val retrofit = Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val service = retrofit.create(UsuarioService::class.java)

        lifecycleScope.launch {
            try {
                val result = service.getUsuario(idUsuario)
                val usuario = result.body()!!
                
                withContext(Dispatchers.Main) {
                    BibliotecaMusicalApplication.usuario = usuario

                    with(mBinding) {
                        etUsername.setText(usuario.username)
                        etPassword.setText(usuario.password)
                        cbRememberPass.isChecked = true
                    }

                    Toast.makeText(this@LoginActivity, "Bienvenido ${usuario.username}!", Toast.LENGTH_LONG).show()
                    goToMain()
                }
            } catch (e: Exception) {
                (e as? HttpException)?.let {
                    when(it.code()) {
                        400 -> {
                            Toast.makeText(this@LoginActivity, R.string.main_error_server, Toast.LENGTH_SHORT).show()
                        }
                        else ->
                            Toast.makeText(this@LoginActivity, R.string.error_general_peticion, Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

    private fun spLogin() {
        val preferences = getPreferences(MODE_PRIVATE)

        if (mBinding.cbRememberPass.isChecked) {
            with(preferences.edit()) {
                putInt(getString(R.string.sp_id_usuario), BibliotecaMusicalApplication.usuario.id)
                putBoolean(getString(R.string.sp_remember_usuario), true)
                    .apply()
            }
        } else {
            with(preferences.edit()) {
                putBoolean(getString(R.string.sp_remember_usuario), false)
                    .apply()
            }
        }
    }

    private fun createUser() {
        val dialogView = layoutInflater.inflate(R.layout.dialog_register, null)
        lateinit var usuario: Usuario

        MaterialAlertDialogBuilder(this)
            .setTitle(R.string.dialog_new_user_title)
            .setView(dialogView)
            .setPositiveButton(R.string.dialog_register_user,
                DialogInterface.OnClickListener { _, _ ->
                    val username = dialogView.findViewById<TextInputEditText>(R.id.etUsername).text.toString().trim()
                    val password = dialogView.findViewById<TextInputEditText>(R.id.etPassword).text.toString().trim()
                    val email = dialogView.findViewById<TextInputEditText>(R.id.etEmail).text.toString().trim()

                    if (checkUserFields(username, password)) {
                        usuario = Usuario(
                            id = 0,
                            username = username,
                            password = password,
                            email = checkOptionalField(email),
                            genero = null,
                            fechaNacimiento = Date(),
                            pais = null,
                            codigoPostal = null
                        )

                        registerUser(usuario)
                    }
                })
            .setNegativeButton(R.string.dialog_cancel, null)
            .setCancelable(true)
            .show()

        focusChangeListener(dialogView.findViewById(R.id.tilUsername), dialogView.findViewById(R.id.etUsername))
        focusChangeListener(dialogView.findViewById(R.id.tilPassword), dialogView.findViewById(R.id.etPassword))
    }

    private fun registerUser(usuario: Usuario) {
        val retrofit = Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val service = retrofit.create(UsuarioService::class.java)

        lifecycleScope.launch {
            try {
                val result = service.postUsuario(usuario)
                val usuario = result.body()!!

                withContext(Dispatchers.Main) {
                    Toast.makeText(this@LoginActivity, "Nuevo usuario ${usuario.username} registrado!", Toast.LENGTH_SHORT).show()

                    with(mBinding) {
                        etUsername.setText(usuario.username)
                        etPassword.setText(usuario.password)
                    }
                }
            } catch (e: Exception) {
                (e as? HttpException)?.let {
                    when (it.code()) {
                        400 -> {
                            Toast.makeText(this@LoginActivity, R.string.main_error_server, Toast.LENGTH_SHORT).show()
                        }
                        else ->
                            Toast.makeText(this@LoginActivity, R.string.error_general_peticion, Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

    private fun goToMain() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }

    private fun loadImgPortada(url: String) {
        Glide.with(this)
            .load(url)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .centerCrop()
            .into(mBinding.imgPortada)
    }

    private fun checkUserFields(username: String, password: String): Boolean {
        if (username.isEmpty()) {
            commonFunctions.errorAlertDialog(getString(R.string.error_username_empty), this)
            return false
        }

        if (password.isEmpty()) {
            commonFunctions.errorAlertDialog(getString(R.string.error_password_empty), this)
            return false
        }

        return true
    }

    private fun checkOptionalField(field: String): String? {
        if (field.isEmpty()) {
            return null
        } else {
            return field
        }
    }

    private fun focusChangeListener(layout: TextInputLayout, textInput: TextInputEditText) {
        textInput.onFocusChangeListener = View.OnFocusChangeListener { view, focused ->
            var errorStr: String? = null

            if (!focused) {
                if(textInput.text.toString().isEmpty()) {
                    errorStr = getString(R.string.error_required_field)
                }
            }
            layout.error = errorStr
        }
    }
}