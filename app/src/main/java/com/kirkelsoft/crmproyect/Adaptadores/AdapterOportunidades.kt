package com.kirkelsoft.crmproyect.Adaptadores

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.kirkelsoft.crmproyect.Objetos.Oportunidades
import com.kirkelsoft.crmproyect.R

class AdapterOportunidades (val context: Context, var oportunidades: ArrayList<Oportunidades>) : RecyclerView.Adapter<AdapterOportunidades.ViewHolder>(), Filterable {
    var mFilteredList : ArrayList<Oportunidades> = oportunidades
    var listaTemporal : ArrayList<Oportunidades> = oportunidades

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textViewName = itemView.findViewById<TextView>(R.id.nombreOportunidad)
        val textViewDes = itemView.findViewById<TextView>(R.id.desOportunidad)
        val textViewCod = itemView.findViewById<TextView>(R.id.codOportunidad)
        val relativeLayout = itemView.findViewById<RelativeLayout>(R.id.item_oportunidades_layout)
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence): Filter.FilterResults {
                val charString = constraint.toString()
                if (!charString.isEmpty()) {
                    val filterList = java.util.ArrayList<Oportunidades>()
                    for (a: Oportunidades in oportunidades) {
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
                mFilteredList = results.values as ArrayList<Oportunidades>
                notifyDataSetChanged()
                oportunidades = mFilteredList
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v= LayoutInflater.from(parent.context).inflate(R.layout.item_oportunidades, parent,false)
        return ViewHolder(v)
    }

    override fun getItemCount(): Int {
        return oportunidades.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val oportunidadesPos: Oportunidades = oportunidades[position]

        holder?.textViewName?.text = oportunidadesPos.nombre
        holder?.textViewDes?.text = oportunidadesPos.subNombre
        holder?.textViewCod?.text = oportunidadesPos.codigo
        holder?.relativeLayout.setOnClickListener ( object : View.OnClickListener {
            override fun onClick(p0: View?) {
                val intent = Intent()
                intent.putExtra("nombreOportunidad", oportunidadesPos.nombre)
                (context as Activity).run {
                    setResult(3, intent)
                    finish()
                }
            }
        })
    }

}