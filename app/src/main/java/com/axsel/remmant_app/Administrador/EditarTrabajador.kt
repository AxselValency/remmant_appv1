package com.axsel.remmant_app.Administrador

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import com.axsel.remmant_app.R
import com.axsel.remmant_app.Trabajador.Trabajador
import com.axsel.remmant_app.databinding.ActivityEditarTrabajadorBinding
import com.google.firebase.database.FirebaseDatabase

class EditarTrabajador : AppCompatActivity() {

    private lateinit var binding: ActivityEditarTrabajadorBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditarTrabajadorBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Obtener los datos del trabajador pasado como intent
        val trabajador = intent.getParcelableExtra<Trabajador>("trabajador") ?: Trabajador()

        // Rellenar los campos con los datos del trabajador
        binding.etDni.setText(trabajador.dni)
        binding.etNombres.setText(trabajador.nombres)
        binding.etPrimerApellido.setText(trabajador.primerApellido)
        binding.etSegundoApellido.setText(trabajador.segundoApellido)
        binding.nacimiento.setText(trabajador.fechaNacimiento)
        binding.etCorreo.setText(trabajador.correo)
        binding.passwordT.setText(trabajador.password)

        // Configurar los Spinners
        val generoOptions = arrayOf("Masculino", "Femenino", "Otro")
        binding.spinnerGenero.adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, generoOptions)

        val estadoCivilOptions = arrayOf("Soltero/a", "Casado/a", "Divorciado/a", "Viudo/a")
        binding.spinnerEstadoCivil.adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, estadoCivilOptions)

        val estadoProfesionalOptions = arrayOf("Activo", "Inactivo", "En formación")
        binding.spinnerEstadoProfesional.adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, estadoProfesionalOptions)

        // Configurar el botón de guardar cambios
        binding.btnGuardarCambios.setOnClickListener {
            guardarCambiosTrabajador(trabajador)
        }
    }

    private fun guardarCambiosTrabajador(trabajador: Trabajador) {
        val updatedTrabajador = Trabajador(
            dni = binding.etDni.text.toString(),
            nombres = binding.etNombres.text.toString(),
            primerApellido = binding.etPrimerApellido.text.toString(),
            segundoApellido = binding.etSegundoApellido.text.toString(),
            fechaNacimiento = binding.nacimiento.text.toString(),
            genero = binding.spinnerGenero.selectedItem.toString(),
            estadoCivil = binding.spinnerEstadoCivil.selectedItem.toString(),
            estadoProfesional = binding.spinnerEstadoProfesional.selectedItem.toString(),
            correo = binding.etCorreo.text.toString(),
            password = binding.passwordT.text.toString()
        )

        val database = FirebaseDatabase.getInstance()
        val reference = database.getReference("Trabajadores").child(trabajador.dni)
        reference.setValue(updatedTrabajador)
            .addOnSuccessListener {
                Toast.makeText(this, "Trabajador actualizado correctamente", Toast.LENGTH_SHORT).show()
                finish() // Finaliza la actividad después de guardar los cambios
            }
            .addOnFailureListener { e ->
                Toast.makeText(this, "Error al actualizar trabajador: ${e.message}", Toast.LENGTH_SHORT).show()
            }
    }
}
