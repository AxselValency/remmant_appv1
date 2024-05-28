package com.axsel.remmant_app.Trabajador

data class Trabajador(
    val dni: String = "",
    val nombres: String = "",
    val primerApellido: String = "",
    val segundoApellido: String = "",
    val fechaNacimiento: String = "",
    val genero: String = "",
    val estadoCivil: String = "",
    val estadoProfesional: String = "",
    val correo: String = "",
    val password: String = ""
)
