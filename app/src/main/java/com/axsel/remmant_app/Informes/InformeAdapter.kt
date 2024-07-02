package com.axsel.remmant_app.Informes

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.axsel.remmant_app.R

// En tu adaptador para la lista de informes (InformeAdapter por ejemplo)
class InformeAdapter(private val informesList: List<Informes>) : RecyclerView.Adapter<InformeAdapter.InformeViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InformeViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_informe, parent, false)
        return InformeViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: InformeViewHolder, position: Int) {
        val informe = informesList[position]
        holder.bind(informe)
    }

    override fun getItemCount(): Int {
        return informesList.size
    }

    inner class InformeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val fechaTextView: TextView = itemView.findViewById(R.id.tvFecha)
        private val horasExtrasTextView: TextView = itemView.findViewById(R.id.tvHorasExtras)
        private val tipoMaquinaTextView: TextView = itemView.findViewById(R.id.tvTipoMaquina)
        private val mecanicoTextView: TextView = itemView.findViewById(R.id.tvMecanico)
        private val codigoMaquinaTextView: TextView = itemView.findViewById(R.id.tvCodigoMaquina)
        private val descripcionTextView: TextView = itemView.findViewById(R.id.tvDescripcion)

        fun bind(informe: Informes) {
            fechaTextView.text = "Fecha: ${informe.fecha}"
            horasExtrasTextView.text = "Horas Extras: ${informe.horasExtras}"
            tipoMaquinaTextView.text = "Tipo de Máquina: ${informe.tipoMaquina}"
            mecanicoTextView.text = "Mecánico a Cargo: ${informe.mecanicoACargo}"
            codigoMaquinaTextView.text = "Código de Máquina: ${informe.codigoMaquina}"
            descripcionTextView.text = "Descripción: ${informe.descripcion}"
        }
    }
}

