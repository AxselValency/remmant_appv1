package com.axsel.remmant_app.Administrador

import android.app.DatePickerDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.InputFilter
import android.widget.DatePicker
import android.widget.Toast
import com.axsel.remmant_app.MainActivity
import com.axsel.remmant_app.R
import com.axsel.remmant_app.Trabajador.ApiResponse
import com.axsel.remmant_app.Trabajador.ApiService
import com.axsel.remmant_app.databinding.ActivityAgregarTrabajadorBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class AgregarTrabajadores : AppCompatActivity() {

    private lateinit var binding: ActivityAgregarTrabajadorBinding
    private lateinit var dateFormatter: SimpleDateFormat

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAgregarTrabajadorBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Inicializa el SimpleDateFormat para formatear la fecha seleccionada
        dateFormatter = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())

        // Agrega un OnClickListener al EditText de fecha de nacimiento
        binding.nacimiento.setOnClickListener {
            showDatePickerDialog()
        }

        binding.etDni.filters = arrayOf<InputFilter>(
            InputFilter.LengthFilter(8),
            InputFilter { source, start, end, dest, dstart, dend ->
                if (source == "" || source.matches(Regex("[0-9]+"))) {
                    return@InputFilter source
                }
                ""
            }
        )
        // Agrega un OnClickListener al botón de búsqueda de DNI
        binding.btnBuscarDni.setOnClickListener {
            val dni = binding.etDni.text.toString()
            if (dni.length == 8 && dni.all { it.isDigit() }) {
                buscarTrabajadorPorDni(dni)
            } else {
                Toast.makeText(this, "Por favor, ingrese un DNI válido de 8 dígitos", Toast.LENGTH_SHORT).show()
            }
        }

        binding.btnCrearCuenta.setOnClickListener {
            guardarDatosTrabajador()
        }
    }

    // Método para buscar trabajador por DNI
    private fun buscarTrabajadorPorDni(dni: String) {
        val token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJlbWFpbCI6ImdhYnJpZWx2YWxlbmN5aEBnbWFpbC5jb20ifQ.r6pL42_5MQBpBvyG7dDPNDUuPbU0lM2TmcGc9ocWPw0"

        val retrofit = Retrofit.Builder()
            .baseUrl("https://dniruc.apisperu.com/api/v1/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val apiService = retrofit.create(ApiService::class.java)

        val call = apiService.getTrabajadorPorDni(dni, token)
        call.enqueue(object : Callback<ApiResponse> {
            override fun onResponse(
                call: Call<ApiResponse>,
                response: Response<ApiResponse>
            ) {
                if (response.isSuccessful && response.body() != null) {
                    val trabajador = response.body()
                    if (trabajador != null && trabajador.nombres != null) {
                        binding.etNombres.setText(trabajador.nombres)
                        binding.etPrimerApellido.setText(trabajador.apellidoPaterno)
                        binding.etSegundoApellido.setText(trabajador.apellidoMaterno)
                        // Generar y rellenar automáticamente el correo y la contraseña
                        val correo = "${trabajador.nombres.replace(" ", "").lowercase()}@remmant.com"
                        val password = "${trabajador.apellidoPaterno}${dni.substring(0, 3)}"
                        binding.etCorreo.setText(correo)
                        binding.passwordT.setText(password)
                    } else {
                        Toast.makeText(this@AgregarTrabajadores, "DNI no encontrado. Por favor, ingrese los nombres y apellidos manualmente.", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(this@AgregarTrabajadores, "Datos del trabajador no encontrados", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<ApiResponse>, t: Throwable) {
                Toast.makeText(this@AgregarTrabajadores, "Error de conexión", Toast.LENGTH_SHORT).show()
            }
        })
    }

    // Método para mostrar el DatePickerDialog
    private fun showDatePickerDialog() {
        val newCalendar = Calendar.getInstance()
        val datePickerDialog = DatePickerDialog(
            this,
            { _: DatePicker, year: Int, monthOfYear: Int, dayOfMonth: Int ->
                val selectedDate = Calendar.getInstance()
                selectedDate.set(year, monthOfYear, dayOfMonth)
                // Formatea la fecha seleccionada y establece el texto en el EditText
                binding.nacimiento.setText(dateFormatter.format(selectedDate.time))
            },
            newCalendar.get(Calendar.YEAR),
            newCalendar.get(Calendar.MONTH),
            newCalendar.get(Calendar.DAY_OF_MONTH)
        )
        datePickerDialog.show()
    }

    private fun guardarDatosTrabajador() {
        val dni = binding.etDni.text.toString()
        val nombres = binding.etNombres.text.toString()
        val primerApellido = binding.etPrimerApellido.text.toString()
        val segundoApellido = binding.etSegundoApellido.text.toString()
        val fechaNacimiento = binding.nacimiento.text.toString()
        val genero = binding.etGenero.text.toString()
        val estadoCivil = binding.etEstadoCivil.text.toString()
        val estadoProfesional = binding.etEstadoProfesional.text.toString()
        val correo = binding.etCorreo.text.toString()
        val password = binding.passwordT.text.toString()

        // Validar que los campos necesarios no estén vacíos
        if (dni.isEmpty() || nombres.isEmpty() || primerApellido.isEmpty() || segundoApellido.isEmpty() ||
            fechaNacimiento.isEmpty() || genero.isEmpty() || estadoCivil.isEmpty() || estadoProfesional.isEmpty() ||
            correo.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Por favor, complete todos los campos", Toast.LENGTH_SHORT).show()
            return
        }

        // Crear usuario en Firebase Authentication
        val firebaseAuth = FirebaseAuth.getInstance()
        firebaseAuth.createUserWithEmailAndPassword(correo, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    // Usuario creado exitosamente en Firebase Authentication
                    val user = firebaseAuth.currentUser
                    val userId = user?.uid

                    // Obtener una referencia a la base de datos de Firebase
                    val database = FirebaseDatabase.getInstance()
                    val reference = database.getReference("Trabajadores")

                    // Crear un mapa para almacenar los datos del trabajador
                    val trabajadorMap = HashMap<String, Any>()
                    trabajadorMap["dni"] = dni
                    trabajadorMap["nombres"] = nombres
                    trabajadorMap["primerApellido"] = primerApellido
                    trabajadorMap["segundoApellido"] = segundoApellido
                    trabajadorMap["fechaNacimiento"] = fechaNacimiento
                    trabajadorMap["genero"] = genero
                    trabajadorMap["estadoCivil"] = estadoCivil
                    trabajadorMap["estadoProfesional"] = estadoProfesional
                    trabajadorMap["correo"] = correo

                    // Guardar los datos del trabajador en Firebase Realtime Database
                    userId?.let {
                        reference.child(it).setValue(trabajadorMap)
                            .addOnSuccessListener {
                                // Datos guardados exitosamente
                                Toast.makeText(this@AgregarTrabajadores, "Datos del trabajador guardados correctamente", Toast.LENGTH_SHORT).show()
                                Toast.makeText(applicationContext, "Cuenta Creada", Toast.LENGTH_SHORT).show()

                                // Crear un Intent para iniciar MainActivity
                                val intent = Intent(this@AgregarTrabajadores, MainActivity::class.java)
                                startActivity(intent)
                                finish() // Opcional: finalizar la actividad actual si no deseas volver a ella
                            }
                            .addOnFailureListener { e ->
                                // Error al guardar los datos
                                Toast.makeText(this@AgregarTrabajadores, "Error al guardar los datos del trabajador: ${e.message}", Toast.LENGTH_SHORT).show()
                            }
                    }
                } else {
                    // Error al crear el usuario en Firebase Authentication
                    val exception = task.exception
                    Toast.makeText(this, "Error al crear el usuario: ${exception?.message}", Toast.LENGTH_SHORT).show()
                }
            }
    }
}

