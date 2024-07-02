package com.axsel.remmant_app.Fragmentos_Trabajador

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import com.axsel.remmant_app.Informes.AgregarInformes
import com.axsel.remmant_app.Informes.Lista_Informes // Importa la actividad Lista_Informes
import com.axsel.remmant_app.R
import com.axsel.remmant_app.bot.WatsonChatBotActivity
import com.axsel.remmant_app.databinding.FragmentTrabajadorDashboardBinding

class Fragment_Trabajador_dashboard : Fragment() {

    private lateinit var mContext: Context

    override fun onAttach(context: Context) {
        mContext = context
        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflar el diseño del fragmento
        val rootView = inflater.inflate(R.layout.fragment__trabajador_dashboard, container, false)

        // Inicializar botones y configurar onClickListeners
        val btnAgregarInformes = rootView.findViewById<Button>(R.id.Btn_agregar_Informes)
        btnAgregarInformes.setOnClickListener {
            // Navegar al Activity para agregar informes
            val intent = Intent(mContext, AgregarInformes::class.java)
            startActivity(intent)
        }

        val btnListarInformes = rootView.findViewById<Button>(R.id.Btn_Listado_Informes)
        btnListarInformes.setOnClickListener {
            // Navegar al Activity para listar informes
            val intent = Intent(mContext, Lista_Informes::class.java)
            startActivity(intent)
        }

        val btnChatBot = rootView.findViewById<Button>(R.id.Btn_chat_bot)
        btnChatBot.setOnClickListener {
            // Navegar a WatsonChatBotActivity para mostrar el bot
            val intent = Intent(mContext, WatsonChatBotActivity::class.java)
            startActivity(intent)
        }


        return rootView
    }
}
