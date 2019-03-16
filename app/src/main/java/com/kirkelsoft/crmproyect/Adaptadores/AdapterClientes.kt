package com.kirkelsoft.crmproyect.Adaptadores

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.kirkelsoft.crmproyect.Objetos.Clientes
import com.kirkelsoft.crmproyect.R


class AdapterClientes (val context: Context, var clientes: ArrayList<Clientes>) : RecyclerView.Adapter<AdapterClientes.ViewHolder>(), Filterable {

    var mFilteredList : ArrayList<Clientes> = clientes
    var listaTemporal : ArrayList<Clientes> = clientes

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textViewName = itemView.findViewById<TextView>(R.id.nombreCliente)
        val imageView = itemView.findViewById<ImageView>(R.id.imagenCliente)
        val relativeLayout = itemView.findViewById<RelativeLayout>(R.id.item_clientes_layout)
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence): Filter.FilterResults {
                val charString = constraint.toString()
                if (!charString.isEmpty()) {
                    val filterList = java.util.ArrayList<Clientes>()
                    for (a: Clientes in clientes) {
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
                mFilteredList = results.values as ArrayList<Clientes>
                notifyDataSetChanged()
                clientes = mFilteredList
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v= LayoutInflater.from(parent.context).inflate(R.layout.item_clientes, parent,false)
        return ViewHolder(v)
    }

    override fun getItemCount(): Int {
        return clientes.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val clientesPos: Clientes = clientes[position]

        holder?.textViewName?.text = clientesPos.nombre
        holder?.imageView?.setImageResource(clientesPos.imagen)
        holder?.relativeLayout.setOnClickListener ( object : View.OnClickListener {
            override fun onClick(p0: View?) {
                val intent = Intent()
                intent.putExtra("nombreCliente", clientesPos.nombre)
                (context as Activity).run {
                    setResult(1, intent)
                    finish()
                }
            }
        })
    }

}