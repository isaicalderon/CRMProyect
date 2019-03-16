package com.kirkelsoft.crmproyect.Adaptadores

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.kirkelsoft.crmproyect.Objetos.Prospectos
import com.kirkelsoft.crmproyect.R

class AdapterProspectos (val context: Context, var prospectos: ArrayList<Prospectos>) : RecyclerView.Adapter<AdapterProspectos.ViewHolder>(), Filterable {
    var mFilteredList : ArrayList<Prospectos> = prospectos
    var listaTemporal : ArrayList<Prospectos> = prospectos

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textViewName = itemView.findViewById<TextView>(R.id.nombreProspecto)
        val textViewIndus = itemView.findViewById<TextView>(R.id.industriaCat)
        val textViewCod = itemView.findViewById<TextView>(R.id.rfcProspecto)
        val relativeLayout = itemView.findViewById<RelativeLayout>(R.id.item_prospectos_layout)
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence): Filter.FilterResults {
                val charString = constraint.toString()
                if (!charString.isEmpty()) {
                    val filterList = java.util.ArrayList<Prospectos>()
                    for (a: Prospectos in prospectos) {
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
                mFilteredList = results.values as ArrayList<Prospectos>
                notifyDataSetChanged()
                prospectos = mFilteredList
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v= LayoutInflater.from(parent.context).inflate(R.layout.item_prospectos, parent,false)
        return ViewHolder(v)
    }

    override fun getItemCount(): Int {
        return prospectos.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val prospectosPos: Prospectos = prospectos[position]
        holder?.textViewName?.text = prospectosPos.nombre
        holder?.textViewIndus?.text = prospectosPos.industriaCat
        holder?.textViewCod?.text = prospectosPos.codigo
        holder?.relativeLayout.setOnClickListener ( object : View.OnClickListener {
            override fun onClick(p0: View?) {
                val intent = Intent()
                intent.putExtra("nombreProspecto", prospectosPos.nombre)
                intent.putExtra("switchProspecto", "true")
                (context as Activity).run {
                    setResult(1, intent)
                    finish()
                }
            }
        })
    }
}