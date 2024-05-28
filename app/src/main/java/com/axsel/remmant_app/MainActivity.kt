package com.axsel.remmant_app

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.axsel.remmant_app.Fragmentos_Admin.Fragment_admin_cuenta
import com.axsel.remmant_app.Fragmentos_Admin.Fragment_admin_dashboard
import com.axsel.remmant_app.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        firebaseAuth = FirebaseAuth.getInstance()
        ComprobarSesion()

              VerFragmentoDashboard()
        binding.BottomNvAdmin.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.Menu_panel -> {
                    VerFragmentoDashboard()
                    // Lógica para el item Menu_panel
                    true
                }
                R.id.Menu_cuenta -> {
                    VerFragmentoCuenta()
                    // Lógica para el item Menu_cuenta
                    true
                }
                else -> false
            }
        }
    }

    private fun VerFragmentoDashboard(){

        val nombre_titulo = "Dashboard"
        binding.TituloRLAdmin.text = nombre_titulo

        val fragment = Fragment_admin_dashboard()
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(binding.FragmentsAdmin.id,fragment,"Fragment dashboard")
        fragmentTransaction.commit()

    }
    private fun VerFragmentoCuenta(){

        val nombre_titulo = "Mi cuenta"
        binding.TituloRLAdmin.text = nombre_titulo

        val fragment =Fragment_admin_cuenta()
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(binding.FragmentsAdmin.id,fragment,"Fragment mi cuenta")
        fragmentTransaction.commit()


    }

    private fun ComprobarSesion(){
        val firebaseUser = firebaseAuth.currentUser
        if(firebaseUser == null){
            startActivity(Intent(this,Elegir_rol::class.java))
            finishAffinity()


        }else{
            Toast.makeText(applicationContext,"Bienvenido(a) ${firebaseUser.email}",
                Toast.LENGTH_SHORT).show()
        }

    }

}