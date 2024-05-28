package com.axsel.remmant_app.Trabajador

data class ApiResponse(
    val success: Boolean,
    val dni: String,
    val nombres: String,
    val apellidoPaterno: String,
    val apellidoMaterno: String,
    val codVerifica: String
)
