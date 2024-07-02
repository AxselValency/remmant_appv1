package com.axsel.remmant_app.Administrador

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.axsel.remmant_app.R
import com.axsel.remmant_app.Trabajador.Trabajador
import com.axsel.remmant_app.Trabajador.TrabajadorAdapter
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.firestore.FirebaseFirestore


class Listar_Trabajadores : AppCompatActivity() {

    private lateinit var recyclerViewTrabajadores: RecyclerView
    private lateinit var trabajadorAdapter: TrabajadorAdapter
    private lateinit var trabajadorList: MutableList<Trabajador>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_listar_trabajadores)

        recyclerViewTrabajadores = findViewById(R.id.recyclerViewTrabajadores)
        trabajadorList = mutableListOf()
        trabajadorAdapter = TrabajadorAdapter(trabajadorList, this)

        recyclerViewTrabajadores.layoutManager = LinearLayoutManager(this)
        recyclerViewTrabajadores.adapter = trabajadorAdapter

        // Llamar al método para llenar la lista de trabajadores
        listarTrabajadores()


    }

    private fun listarTrabajadores() {
        val database = FirebaseDatabase.getInstance()
        val reference = database.getReference("Trabajadores")

        reference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                trabajadorList.clear() // Limpiar la lista para evitar duplicados

                for (trabajadorSnapshot in snapshot.children) {
                    // Obtener los datos del trabajador del snapshot
                    val dni = trabajadorSnapshot.child("dni").getValue(String::class.java) ?: ""
                    val nombres = trabajadorSnapshot.child("nombres").getValue(String::class.java) ?: ""
                    val primerApellido = trabajadorSnapshot.child("primerApellido").getValue(String::class.java) ?: ""
                    val segundoApellido = trabajadorSnapshot.child("segundoApellido").getValue(String::class.java) ?: ""
                    val fechaNacimiento = trabajadorSnapshot.child("fechaNacimiento").getValue(String::class.java) ?: ""
                    val genero = trabajadorSnapshot.child("genero").getValue(String::class.java) ?: ""
                    val estadoCivil = trabajadorSnapshot.child("estadoCivil").getValue(String::class.java) ?: ""
                    val estadoProfesional = trabajadorSnapshot.child("estadoProfesional").getValue(String::class.java) ?: ""
                    val correo = trabajadorSnapshot.child("correo").getValue(String::class.java) ?: ""
                    val password = trabajadorSnapshot.child("password").getValue(String::class.java) ?: ""

                    // Repite este proceso para obtener otros campos del trabajador

                    // Crear un objeto Trabajador con los datos obtenidos
                    val trabajador = Trabajador(
                        dni = dni,
                        nombres = nombres,
                        primerApellido = primerApellido,
                        segundoApellido = segundoApellido,
                        fechaNacimiento = fechaNacimiento,
                        genero = genero,
                        estadoCivil = estadoCivil,
                        estadoProfesional = estadoProfesional,
                        correo = correo,
                        password = password
                    )

                    // Agregar el trabajador a la lista
                    trabajadorList.add(trabajador)
                }

                // Notificar al adaptador que los datos han cambiado
                trabajadorAdapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {
                // Manejar errores en caso de que la consulta falle
                Log.e("Firebase", "Error al obtener trabajadores", error.toException())
            }
        })
    }


}