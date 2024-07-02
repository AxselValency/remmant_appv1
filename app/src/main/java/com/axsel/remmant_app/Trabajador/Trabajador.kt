package com.axsel.remmant_app.Trabajador

import android.os.Parcel
import android.os.Parcelable
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
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString() ?: "", // Asegúrate de manejar nullables correctamente
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: ""
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(dni)
        parcel.writeString(nombres)
        parcel.writeString(primerApellido)
        parcel.writeString(segundoApellido)
        parcel.writeString(fechaNacimiento)
        parcel.writeString(genero)
        parcel.writeString(estadoCivil)
        parcel.writeString(estadoProfesional)
        parcel.writeString(correo)
        parcel.writeString(password)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Trabajador> {
        override fun createFromParcel(parcel: Parcel): Trabajador {
            return Trabajador(parcel)
        }

        override fun newArray(size: Int): Array<Trabajador?> {
            return arrayOfNulls(size)
        }
    }
}
