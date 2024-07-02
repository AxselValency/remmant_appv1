package com.axsel.remmant_app.Fragmentos_Trabajador

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.axsel.remmant_app.Elegir_rol
import com.axsel.remmant_app.R
import com.google.firebase.auth.FirebaseAuth

class Fragment_Trabajador_cuenta : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment__trabajador_cuenta, container, false)

        // Obtener referencia al TextView de cerrar sesión
        val cerrarSesionTextView: TextView = view.findViewById(R.id.CerrarSesionTrabajador)

        // Configurar el listener para cerrar sesión
        cerrarSesionTextView.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            val intent = Intent(activity, Elegir_rol::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        }

        return view
    }


}
