package com.axsel.remmant_app.Trabajador

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {



    @GET("dni/{dni}")
    fun getTrabajadorPorDni(
        @Path("dni") dni: String,
        @Query("token") token: String
    ): Call<ApiResponse>
}