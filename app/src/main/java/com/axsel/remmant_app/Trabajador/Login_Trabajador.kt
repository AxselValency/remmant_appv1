package com.axsel.remmant_app.Trabajador

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import com.axsel.remmant_app.MainActivity
import com.axsel.remmant_app.MainTrabajadorActivity
import com.axsel.remmant_app.databinding.ActivityLoginTrabajadorBinding
import com.google.firebase.auth.FirebaseAuth

class Login_Trabajador : AppCompatActivity() {

    private lateinit var binding: ActivityLoginTrabajadorBinding
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var progressDialog: ProgressDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLoginTrabajadorBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseAuth = FirebaseAuth.getInstance()

        progressDialog = ProgressDialog(this)
        progressDialog.setTitle("Espere por Favor")
        progressDialog.setCanceledOnTouchOutside(false)

        binding.IbRegresar.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }
        binding.BtnLoginTrabajador.setOnClickListener {
            ValidarInformacion()
        }
    }

    private var email = ""
    private var password = ""

    private fun ValidarInformacion() {
        email = binding.EtEmailTrabajador.text.toString().trim()
        password = binding.EtPasswordTrabajador.text.toString().trim()

        if (email.isEmpty()) {
            binding.EtEmailTrabajador.error = "Ingrese su Correo"
            binding.EtEmailTrabajador.requestFocus()
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            binding.EtEmailTrabajador.error = "Correo Invalido"
            binding.EtEmailTrabajador.requestFocus()
        } else if (password.isEmpty()) {
            binding.EtEmailTrabajador.error = "Ingrese la contraseña"
            binding.EtEmailTrabajador.requestFocus()
        } else {
            LoginTrabajador()
        }
    }

    private fun LoginTrabajador() {
        val correo = binding.EtEmailTrabajador.text.toString()
        val password = binding.EtPasswordTrabajador.text.toString()

        if (correo.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Por favor, ingrese el correo y la contraseña", Toast.LENGTH_SHORT).show()
            return
        }

        progressDialog.setMessage("Iniciando Sesión")
        progressDialog.show()

        firebaseAuth.signInWithEmailAndPassword(correo, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val intent = Intent(this, MainTrabajadorActivity::class.java)
                    intent.putExtra("isAdmin", false)
                    startActivity(intent)
                    finish()
                } else {
                    val exception = task.exception
                    if (exception?.message?.contains("password") == true || exception?.message?.contains("correo") == true) {
                        Toast.makeText(this, "Correo o contraseña incorrectos", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(this, "Error al iniciar sesión: vuelva a escribir su email o contraseña", Toast.LENGTH_SHORT).show()
                    }
                    progressDialog.dismiss()
                }
            }
    }
}
