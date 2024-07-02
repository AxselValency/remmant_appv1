package com.axsel.remmant_app

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.axsel.remmant_app.Fragmentos_Admin.Fragment_admin_cuenta
import com.axsel.remmant_app.Fragmentos_Admin.Fragment_admin_dashboard
import com.axsel.remmant_app.Fragmentos_Trabajador.Fragment_Trabajador_dashboard
import com.axsel.remmant_app.databinding.ActivityMainBinding
import com.axsel.remmant_app.Fragmentos_Trabajador.Fragment_Trabajador_cuenta
import com.axsel.remmant_app.databinding.ActivityMainTrabajadorBinding
import com.google.firebase.auth.FirebaseAuth

class MainTrabajadorActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainTrabajadorBinding
    private lateinit var firebaseAuth: FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivityMainTrabajadorBinding.inflate(layoutInflater)
        setContentView(binding.root)
        firebaseAuth = FirebaseAuth.getInstance()
        ComprobarSesion()

        VerFragmentoTrabajdorDashboard()
        binding.BottomNvAdmin.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.Menu_panel -> {
                    VerFragmentoTrabajdorDashboard()
                    // Lógica para el item Menu_panel
                    true
                }
                R.id.Menu_cuenta -> {
                    VerFragmentoTrabajadorCuenta()
                    // Lógica para el item Menu_cuenta
                    true
                }
                else -> false
            }
        }
    }

    private fun VerFragmentoTrabajdorDashboard(){

        val nombre_titulo = "Dashboard Trabajador"
        binding.TituloRLTrabajador.text = nombre_titulo

        val fragment = Fragment_Trabajador_dashboard()
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(binding.FragmentsTrabjadorDash.id, fragment, "Fragment trabajador dashboard")
        fragmentTransaction.commit()

    }
    private fun VerFragmentoTrabajadorCuenta(){

        val nombre_titulo = "Mi cuenta Trabajor"
        binding.TituloRLTrabajador.text = nombre_titulo

        val fragment = Fragment_Trabajador_cuenta()
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(binding.FragmentsTrabjadorDash.id,fragment,"Fragment mi cuenta Trabajador")
        fragmentTransaction.commit()


    }

    private fun ComprobarSesion(){
        val firebaseUser = firebaseAuth.currentUser
        if(firebaseUser == null){
            startActivity(Intent(this,Elegir_rol::class.java))
            finishAffinity()


        }else{
            Toast.makeText(applicationContext,"Bienvenido(a) Trabajador ${firebaseUser.email}",
                Toast.LENGTH_SHORT).show()
        }

    }






}