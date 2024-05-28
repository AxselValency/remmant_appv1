package com.axsel.remmant_app.Administrador

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import com.axsel.remmant_app.MainActivity
import com.axsel.remmant_app.R
import com.axsel.remmant_app.databinding.ActivityLoginAdminBinding
import com.axsel.remmant_app.databinding.FragmentAdminCuentaBinding
import com.google.firebase.auth.FirebaseAuth

class Login_Admin : AppCompatActivity() {


    private lateinit var binding: ActivityLoginAdminBinding
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var progressDialog: ProgressDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginAdminBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseAuth = FirebaseAuth.getInstance()

        progressDialog = ProgressDialog(this)
        progressDialog.setTitle("Espere por Favor")
        progressDialog.setCanceledOnTouchOutside(false)

        binding.IbRegresar.setOnClickListener{
            onBackPressedDispatcher.onBackPressed()
        }

         binding.BtnLoginAdmin.setOnClickListener{
             ValidarInformacion()
         }


    }

    private var email = ""
    private var password =""

    private fun ValidarInformacion() {
        email = binding.EtEmailAdmin.text.toString().trim()
        password = binding.EtPasswordAdmin.text.toString().trim()

        if (email.isEmpty()){
            binding.EtEmailAdmin.error = "Ingrese su Correo"
            binding.EtEmailAdmin.requestFocus()

        }
        else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){

            binding.EtEmailAdmin.error="Correo Invalido"
            binding.EtEmailAdmin.requestFocus()
        }
         else if(password.isEmpty()){
             binding.EtEmailAdmin.error = "Ingrese la contraseña"
            binding.EtEmailAdmin.requestFocus()


         }
        else {
            LoginAdmin()
        }

    }

    private fun LoginAdmin() {

        progressDialog.setMessage("Iniciando Sesion")
        progressDialog.show()

        firebaseAuth.signInWithEmailAndPassword(email,password)
            .addOnSuccessListener {
                progressDialog.dismiss()
                startActivity(Intent(this@Login_Admin,MainActivity::class.java))
                finishAffinity()


            }
            .addOnFailureListener{e->
                progressDialog.dismiss()
                Toast.makeText(applicationContext,"No se puede iniciar sesion debido a ${e.message}",
                    Toast.LENGTH_SHORT).show()


            }


    }
}