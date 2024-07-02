package com.axsel.remmant_app.Trabajador

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.axsel.remmant_app.Administrador.EditarTrabajador
import com.axsel.remmant_app.R
import com.axsel.remmant_app.Trabajador.Trabajador
import com.google.firebase.FirebaseApp
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class TrabajadorAdapter(
    private val trabajadorList: MutableList<Trabajador>,
    private val context: Context
) : RecyclerView.Adapter<TrabajadorAdapter.TrabajadorViewHolder>() {

    private lateinit var database: FirebaseDatabase
    private lateinit var trabajadoresRef: DatabaseReference

    init {
        FirebaseApp.initializeApp(context)
        database = FirebaseDatabase.getInstance()
        trabajadoresRef = database.getReference("Trabajadores")
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrabajadorViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_trabajador, parent, false)
        return TrabajadorViewHolder(view)
    }

    override fun onBindViewHolder(holder: TrabajadorViewHolder, position: Int) {
        val trabajador = trabajadorList[position]
        holder.textViewNombre.text = trabajador.nombres
        holder.textViewCorreo.text = trabajador.correo
        holder.textViewPassword.text = trabajador.password
        holder.buttonEditar.setOnClickListener {
            editarTrabajador(trabajador)
        }
        holder.buttonEliminar.setOnClickListener {
            eliminarTrabajador(trabajador, position)
        }

    }

    override fun getItemCount(): Int {
        return trabajadorList.size
    }

    class TrabajadorViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textViewNombre: TextView = itemView.findViewById(R.id.textViewNombre)
        val textViewCorreo: TextView = itemView.findViewById(R.id.textViewCorreo)
        val textViewPassword: TextView = itemView.findViewById(R.id.textViewPassword)
        val buttonEditar: Button = itemView.findViewById(R.id.buttonEditar)
        val buttonEliminar: Button = itemView.findViewById(R.id.buttonEliminar)
    }

    private fun editarTrabajador(trabajador: Trabajador) {
        val intent = Intent(context, EditarTrabajador::class.java)
        intent.putExtra("trabajador", trabajador)
        context.startActivity(intent)
    }

    private fun eliminarTrabajador(trabajador: Trabajador, position: Int) {
        val builder = AlertDialog.Builder(context)
        builder.setTitle("Confirmar eliminación")
        builder.setMessage("¿Estás seguro de eliminar a ${trabajador.nombres}?")

        builder.setPositiveButton("Eliminar") { dialog, which ->
            // Obtener una referencia a la colección "Trabajadores" en Firebase Realtime Database
            val trabajadoresRef = database.getReference("Trabajadores")

            // Eliminar el trabajador usando su DNI como identificador
            trabajadoresRef.child(trabajador.dni).removeValue()
                .addOnSuccessListener {
                    // Eliminación exitosa en Firebase, ahora eliminar localmente
                    trabajadorList.removeAt(position)
                    notifyDataSetChanged()
                    Log.d(TAG, "Trabajador eliminado en Firebase y localmente")
                }
                .addOnFailureListener { e ->
                    // Manejar errores si la eliminación falla
                    Log.e(TAG, "Error al eliminar trabajador en Firebase", e)
                    Toast.makeText(context, "Error al eliminar trabajador", Toast.LENGTH_SHORT).show()
                }
        }

        builder.setNegativeButton("Cancelar") { dialog, which ->
            dialog.dismiss()
        }

        val dialog = builder.create()
        dialog.show()
    }





    companion object {
        private const val TAG = "TrabajadorAdapter"
    }
}
