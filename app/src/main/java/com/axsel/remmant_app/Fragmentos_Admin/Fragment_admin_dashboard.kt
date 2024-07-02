package com.axsel.remmant_app.Fragmentos_Admin

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.axsel.remmant_app.Administrador.AgregarTrabajadores
import com.axsel.remmant_app.Administrador.Listar_Trabajadores
import com.axsel.remmant_app.databinding.FragmentAdminDashboardBinding

class Fragment_admin_dashboard : Fragment() {

    private lateinit var binding: FragmentAdminDashboardBinding

    private lateinit var  mContext : Context

    override fun onAttach(context: Context) {
        mContext = context
        super.onAttach(context)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentAdminDashboardBinding.inflate(layoutInflater,container,false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Abrir la actividad AgregarTrabajadores cuando se hace clic en el botón correspondiente
        binding.BtnAgregarTrabajador.setOnClickListener {
            startActivity(Intent(mContext, AgregarTrabajadores::class.java))
        }

        // Abrir la actividad Listar_Trabajadores cuando se hace clic en el botón correspondiente
        binding.BtnListarTrabajadores.setOnClickListener {
            startActivity(Intent(mContext, Listar_Trabajadores::class.java))
        }
    }




}