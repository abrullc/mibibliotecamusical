package com.abrullc.mibibliotecamusical.mainModule

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.abrullc.mibibliotecamusical.R
import com.abrullc.mibibliotecamusical.databinding.ActivityLoginBinding
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        loadImgPortada("https://imageio.forbes.com/specials-images/imageserve/568781608/0x0.jpg?format=jpg&height=900&width=1600&fit=bounds")

        /*val preferences = getPreferences(MODE_PRIVATE)

        val rememberUsusario = preferences.getBoolean(getString(R.string.sp_remember_usuario), false)
        val spIdUsuario = preferences.getLong(getString(R.string.sp_id_usuario), 0)

        if (rememberUsusario) {
            doAsync {
                val usuario = getUserById(spIdUsuario)

                uiThread {
                    binding.etUsername.setText(usuario.nombre)
                    binding.etPassword.setText(usuario.password)
                    binding.cbRememberPass.isChecked = true

                    Toast.makeText(this@LoginActivity, "Bienvenido ${usuario.nombre}!", Toast.LENGTH_LONG).show()
                    goToMain()
                }
            }
        }

        focusChangeListener(binding.tilUsername, binding.etUsername)
        focusChangeListener(binding.tilPassword, binding.etPassword)

        binding.btnLogin.setOnClickListener {
            val username = binding.etUsername.text.toString()
            val password = binding.etPassword.text.toString()

            if (checkUserFields(username, password)) {
                doAsync {
                    val usuario = getUser(username, password)
                    uiThread {
                        if (usuario != null) {
                            with(preferences.edit()) {
                                putLong(getString(R.string.sp_id_usuario_actual), usuario.idUsuario)
                                    .apply()
                            }

                            if (binding.cbRememberPass.isChecked) {
                                with(preferences.edit()) {
                                    putLong(getString(R.string.sp_id_usuario), usuario.idUsuario)
                                    putBoolean(getString(R.string.sp_remember_usuario), true)
                                        .apply()
                                }
                            } else {
                                with(preferences.edit()) {
                                    putBoolean(getString(R.string.sp_remember_usuario), false)
                                        .apply()
                                }
                            }

                            Toast.makeText(this@LoginActivity, "Bienvenido ${usuario.nombre}!", Toast.LENGTH_LONG).show()
                            goToMain()
                        } else {
                            errorAlertDialog("El usuario y/o contraseña introducidos son incorrectos")
                        }
                    }
                }
            }
        }

        binding.newUser.setOnClickListener {
            val dialogView = layoutInflater.inflate(R.layout.dialog_register, null)
            lateinit var user: UsuarioEntity

            MaterialAlertDialogBuilder(this)
                .setTitle(R.string.dialog_new_user_title)
                .setView(dialogView)
                .setPositiveButton(R.string.dialog_register_user,
                    DialogInterface.OnClickListener { _, _ ->
                        val username = dialogView.findViewById<TextInputEditText>(R.id.etUsername).text.toString()
                        val password = dialogView.findViewById<TextInputEditText>(R.id.etPassword).text.toString()

                        if (checkUserFields(username, password)) {
                            user = UsuarioEntity(nombre =  username, password = password)

                            Toast.makeText(this, "Nuevo usuario $username registrado!", Toast.LENGTH_SHORT).show()

                            binding.etUsername.setText(username)
                            binding.etPassword.setText(password)

                            doAsync {
                                NoticiaApplication.database.usuarioDao().addUsuario(user)
                            }
                        }
                    })
                .setNegativeButton(R.string.dialog_cancel, null)
                .setCancelable(true)
                .show()

            focusChangeListener(dialogView.findViewById(R.id.tilUsername), dialogView.findViewById(R.id.etUsername))
            focusChangeListener(dialogView.findViewById(R.id.tilPassword), dialogView.findViewById(R.id.etPassword))

            doAsync {
                NoticiaApplication.database.usuarioDao().addUsuario(user)
            }
        }*/
    }

    /*private fun goToMain() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }

    private fun getUser(username: String, password: String): UsuarioEntity {
        val usuario = NoticiaApplication.database.usuarioDao().getUsuario(username, password)

        return usuario
    }

    private fun getUserById(idUsuario: Long): UsuarioEntity {
        val usuario = NoticiaApplication.database.usuarioDao().getUsuarioById(idUsuario)

        return usuario
    }*/

    private fun loadImgPortada(url: String) {
        Glide.with(this)
            .load(url)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .centerCrop()
            .into(binding.imgPortada)
    }

    /*private fun checkUserFields(username: String, password: String): Boolean {
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
    }*/
}