package com.axsel.remmant_app

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import com.axsel.remmant_app.Administrador.Login_Admin
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class Bienvenida : AppCompatActivity() {
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bienvenida)
        firebaseAuth = FirebaseAuth.getInstance()
        VerBienvenida()
    }

    private fun VerBienvenida() {
        Log.d("Bienvenida", "CountDownTimer iniciado")
        object : CountDownTimer(5000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                // Opcional: actualizar la UI con el tiempo restante
                Log.d("Bienvenida", "Tiempo restante: $millisUntilFinished ms")
            }

            override fun onFinish() {
                Log.d("Bienvenida", "CountDownTimer terminado")
                ComprobarSesion()
            }
        }.start()
    }

    private fun ComprobarSesion() {
        val firebaseUser = firebaseAuth.currentUser
        if (firebaseUser == null) {
            Log.d("Bienvenida", "Usuario no autenticado, dirigiendo a Elegir_rol")
            startActivity(Intent(this, Elegir_rol::class.java))
            finishAffinity()
        } else {
            Log.d("Bienvenida", "Usuario autenticado: ${firebaseUser.uid}")
            val reference = FirebaseDatabase.getInstance().getReference("Usuarios")
            reference.child(firebaseUser.uid)
                .addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        val rol = snapshot.child("rol").value
                        Log.d("Bienvenida", "Rol del usuario: $rol")
                        if (rol == "admin") {
                            Log.d("Bienvenida", "Usuario es admin, dirigiendo a MainActivity")
                            startActivity(Intent(this@Bienvenida, MainActivity::class.java))
                            finishAffinity()
                        } else {
                            Log.d("Bienvenida", "Usuario no es admin, rol: $rol")
                            // Aquí puedes manejar el caso donde el usuario no es admin
                            // Por ejemplo, dirigir a otra actividad
                            startActivity(Intent(this@Bienvenida,Elegir_rol::class.java))
                            finishAffinity()
                        }
                    }

                    override fun onCancelled(error: DatabaseError) {
                        Log.e("Bienvenida", "Error al comprobar el rol del usuario: ${error.message}")
                        // Manejar el error adecuadamente
                    }
                })
        }
    }
}
