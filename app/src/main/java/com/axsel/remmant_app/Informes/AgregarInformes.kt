package com.axsel.remmant_app.Informes

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.axsel.remmant_app.R
import com.google.firebase.database.FirebaseDatabase

class AgregarInformes : AppCompatActivity() {

    // Referencia a la base de datos de Firebase
    private val database = FirebaseDatabase.getInstance()
    private val informesRef = database.getReference("informes")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_agregar_informes)

        // Inicialización de spinners y botón
        val spTipoMaquina: Spinner = findViewById(R.id.spTipoMaquina)
        ArrayAdapter.createFromResource(
            this,
            R.array.tipo_maquina_array,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spTipoMaquina.adapter = adapter
        }

        val spMecanicoACargo: Spinner = findViewById(R.id.spMecanicoACargo)
        ArrayAdapter.createFromResource(
            this,
            R.array.mecanico_cargo_array,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spMecanicoACargo.adapter = adapter
        }

        val btnGuardarInforme: Button = findViewById(R.id.btnGuardarInforme)
        btnGuardarInforme.setOnClickListener {
            guardarInformeEnFirebase()
        }
    }

    private fun guardarInformeEnFirebase() {
        // Obtener valores de los campos de entrada
        val fecha = findViewById<EditText>(R.id.etInformeFecha).text.toString()
        val horasExtras = findViewById<EditText>(R.id.etHorasExtras).text.toString()
        val tipoMaquina = findViewById<Spinner>(R.id.spTipoMaquina).selectedItem.toString()
        val mecanicoACargo = findViewById<Spinner>(R.id.spMecanicoACargo).selectedItem.toString()
        val codigoMaquina = findViewById<EditText>(R.id.etCodigoMaquina).text.toString()
        val descripcion = findViewById<EditText>(R.id.etDescripcion).text.toString()
        val nombreTrabajador = findViewById<EditText>(R.id.etNombreTrabajador).text.toString()

        // Crear un nuevo objeto Informe
        val nuevoInforme = Informes(fecha, horasExtras, tipoMaquina, mecanicoACargo, codigoMaquina, descripcion, nombreTrabajador)

        // Generar una nueva clave única para el informe
        val informeKey = informesRef.push().key ?: ""

        // Guardar el informe en la base de datos usando la clave generada
        informesRef.child(informeKey).setValue(nuevoInforme)
            .addOnSuccessListener {
                // Éxito al guardar el informe
                Toast.makeText(this, "Informe guardado exitosamente", Toast.LENGTH_SHORT).show()
                finish()
            }
            .addOnFailureListener { e ->
                // Error al guardar el informe
                Toast.makeText(this, "Error al guardar el informe: ${e.message}", Toast.LENGTH_SHORT).show()
            }
    }
}
