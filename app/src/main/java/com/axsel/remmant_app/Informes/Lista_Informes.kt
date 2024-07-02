package com.axsel.remmant_app.Informes

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.axsel.remmant_app.R
import com.google.firebase.database.*

class Lista_Informes : AppCompatActivity() {

    private lateinit var informeAdapter: InformeAdapter
    private lateinit var informeList: MutableList<Informes>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lista_informes)

        informeList = mutableListOf()
        informeAdapter = InformeAdapter(informeList)

        val recyclerViewInformes = findViewById<RecyclerView>(R.id.recyclerViewInformes)
        recyclerViewInformes.layoutManager = LinearLayoutManager(this)
        recyclerViewInformes.adapter = informeAdapter

        // Llamar al método para llenar la lista de informes
        listarInformes()
    }

    private fun listarInformes() {
        val database = FirebaseDatabase.getInstance()
        val reference = database.getReference("informes")

        reference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                informeList.clear() // Limpiar la lista para evitar duplicados

                for (informeSnapshot in snapshot.children) {
                    // Obtener los datos del informe del snapshot
                    val fecha = informeSnapshot.child("fecha").getValue(String::class.java) ?: ""
                    val horasExtras = informeSnapshot.child("horasExtras").getValue(String::class.java) ?: ""
                    val tipoMaquina = informeSnapshot.child("tipoMaquina").getValue(String::class.java) ?: ""
                    val mecanicoACargo = informeSnapshot.child("mecanicoACargo").getValue(String::class.java) ?: ""
                    val codigoMaquina = informeSnapshot.child("codigoMaquina").getValue(String::class.java) ?: ""
                    val descripcion = informeSnapshot.child("descripcion").getValue(String::class.java) ?: ""
                    val nombreTrabajador = informeSnapshot.child("nombreTrabajador").getValue(String::class.java) ?: ""

                    // Crear un objeto Informe con los datos obtenidos
                    val informe = Informes(
                        fecha = fecha,
                        horasExtras = horasExtras,
                        tipoMaquina = tipoMaquina,
                        mecanicoACargo = mecanicoACargo,
                        codigoMaquina = codigoMaquina,
                        descripcion = descripcion,
                        nombreTrabajador = nombreTrabajador
                    )

                    // Agregar el informe a la lista
                    informeList.add(informe)
                }

                // Notificar al adaptador que los datos han cambiado
                informeAdapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {
                // Manejar errores en caso de que la consulta falle
                Log.e("Firebase", "Error al obtener informes", error.toException())
            }
        })
    }
}
