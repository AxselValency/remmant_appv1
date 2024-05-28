package com.axsel.remmant_app

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.axsel.remmant_app.Administrador.Registrar_Admin
import com.axsel.remmant_app.Trabajador.Login_Trabajador
import com.axsel.remmant_app.databinding.ActivityElegirRolBinding

class Elegir_rol : AppCompatActivity() {


    private lateinit var binding:ActivityElegirRolBinding



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityElegirRolBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.BtnRolAdministrador.setOnClickListener{
           // Toast.makeText(applicationContext,"Rol Adminitrador",Toast.LENGTH_SHORT).show()
            startActivity(Intent(this@Elegir_rol, Registrar_Admin::class.java))

        }
        binding.BtnRolCliente.setOnClickListener{

            startActivity(Intent(this@Elegir_rol, Login_Trabajador::class.java))


        }
    }
}