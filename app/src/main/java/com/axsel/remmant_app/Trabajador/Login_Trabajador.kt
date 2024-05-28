package com.axsel.remmant_app.Trabajador

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.axsel.remmant_app.MainActivity
import com.axsel.remmant_app.R
import com.axsel.remmant_app.databinding.ActivityLoginAdminBinding
import com.axsel.remmant_app.databinding.ActivityLoginTrabajadorBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

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

        binding.IbRegresar.setOnClickListener{
            onBackPressedDispatcher.onBackPressed()
        }
        binding.BtnLoginTrabajador.setOnClickListener{
            ValidarInformacion()
        }



    }
    private var email = ""
    private var password =""

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
        // Obtener las credenciales del formulario
        val correo = binding.EtEmailTrabajador.text.toString()
        val password = binding.EtPasswordTrabajador.text.toString()

        // Verificar si el correo o la contraseña están vacíos
        if (correo.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Por favor, ingrese el correo y la contraseña", Toast.LENGTH_SHORT).show()
            return
        }

        // Mostrar el diálogo de progreso
        progressDialog.setMessage("Iniciando Sesión")
        progressDialog.show()

        // Autenticar al usuario con Firebase
        val firebaseAuth = FirebaseAuth.getInstance()
        firebaseAuth.signInWithEmailAndPassword(correo, password)
            .addOnCompleteListener { task ->
                // Verificar si el inicio de sesión fue exitoso
                if (task.isSuccessful) {
                    // Inicio de sesión exitoso
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                    finish()
                } else {
                    // Error al iniciar sesión
                    val exception = task.exception
                    // Verificar si el error es por credenciales inválidas
                    if (exception?.message?.contains("password") == true || exception?.message?.contains("correo") == true) {
                        // Credenciales inválidas
                        Toast.makeText(this, "Correo o contraseña incorrectos", Toast.LENGTH_SHORT).show()
                    } else {
                        // Otro tipo de error
                        Toast.makeText(this, "Error al iniciar sesión: vuelva a escribir su email o contraseña", Toast.LENGTH_SHORT).show()
                    }
                    // Ocultar el diálogo de progreso
                    progressDialog.dismiss()
                }
            }
    }






}