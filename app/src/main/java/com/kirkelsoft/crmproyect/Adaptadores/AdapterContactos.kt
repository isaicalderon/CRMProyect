package com.kirkelsoft.crmproyect.Adaptadores

import android.content.Context
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import android.app.Activity
import com.kirkelsoft.crmproyect.Objetos.Contactos
import com.kirkelsoft.crmproyect.R


class AdapterContactos (val context: Context, var contactos: ArrayList<Contactos>) : RecyclerView.Adapter<AdapterContactos.ViewHolder>(), Filterable {
    var mFilteredList : ArrayList<Contactos> = contactos
    var listaTemporal : ArrayList<Contactos> = contactos

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textViewName = itemView.findViewById<TextView>(R.id.nombreContacto)
        val textViewLetra = itemView.findViewById<TextView>(R.id.letraContacto)
        val textViewCorreo = itemView.findViewById<TextView>(R.id.emailContacto)
        val textViewTel = itemView.findViewById<TextView>(R.id.telContacto)
        val imageView = itemView.findViewById<ImageView>(R.id.imagenContacto)
        val relativeLayout = itemView.findViewById<RelativeLayout>(R.id.item_contactos_layout)
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence): Filter.FilterResults {
                val charString = constraint.toString()
                if (!charString.isEmpty()) {
                    val filterList = java.util.ArrayList<Contactos>()
                    for (a: Contactos in contactos) {
                        if (a.nombre.toLowerCase().contains(charString.toLowerCase())) {
                            filterList.add(a)
                        }
                    }
                    mFilteredList = filterList
                }else {
                    mFilteredList = listaTemporal
                }
                val filterResults = Filter.FilterResults()
                filterResults.values = mFilteredList
                return filterResults
            }

            override fun publishResults(constraint: CharSequence, results: Filter.FilterResults) {
                mFilteredList = results.values as ArrayList<Contactos>
                notifyDataSetChanged()
                contactos = mFilteredList
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v= LayoutInflater.from(parent.context).inflate(R.layout.item_contactos, parent,false)
        return ViewHolder(v)
    }

    override fun getItemCount(): Int {
        return contactos.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val contactosPos: Contactos = contactos[position]
        holder?.textViewName?.text = contactosPos.nombre
        holder?.imageView?.setImageResource(contactosPos.imagen)
        holder?.textViewCorreo?.text = contactosPos.correo
        holder?.textViewTel?.text = contactosPos.telefono
        holder?.textViewLetra?.text = contactosPos.letra
        holder?.relativeLayout.setOnClickListener ( object : View.OnClickListener {
            override fun onClick(p0: View?) {
                val intent = Intent()
                intent.putExtra("nombreContacto", contactosPos.nombre)
                (context as Activity).run {
                    setResult(2, intent)
                    finish()
                }
            }
        })
    }

}