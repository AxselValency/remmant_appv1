package com.axsel.remmant_app.Noticia

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.axsel.remmant_app.R
import com.axsel.remmant_app.databinding.ItemNoticiaBinding


class NoticiasAdapter(private val context: Context, private val noticias: List<Noticia>)
    : RecyclerView.Adapter<NoticiasAdapter.NoticiaViewHolder>() {

    inner class NoticiaViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding = ItemNoticiaBinding.bind(view)

        fun bind(noticia: Noticia) {
            binding.imageViewNoticia.setImageResource(noticia.imageResId)
            binding.textViewTitulo.text = noticia.titulo
            binding.root.setOnClickListener {
                // Handle click event to show detailed news
                // You can use an AlertDialog or navigate to another Activity or Fragment
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoticiaViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_noticia, parent, false)
        return NoticiaViewHolder(view)
    }

    override fun onBindViewHolder(holder: NoticiaViewHolder, position: Int) {
        holder.bind(noticias[position])
    }

    override fun getItemCount(): Int = noticias.size
}