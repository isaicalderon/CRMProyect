package com.kirkelsoft.crmproyect.Adaptadores

import android.content.Context
import android.graphics.Color
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.kirkelsoft.crmproyect.Objetos.Actividad
import com.kirkelsoft.crmproyect.R

class AdapterActividad (val context: Context, var actividades: ArrayList<Actividad>):RecyclerView.Adapter<AdapterActividad.ViewHolder>(), Filterable {

    var mFilteredList : ArrayList<Actividad> = actividades;
    var listaTemporal : ArrayList<Actividad> = actividades;


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_main, parent, false)
        return ViewHolder(v)
    }

    override fun getItemCount(): Int {
        return actividades.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val actividades: Actividad = actividades[position]
        val estatus = actividades.estatus
        holder?.textViewName?.text = actividades.name
        holder?.textViewClientes?.text = actividades.cliente
        holder?.texViewProceso?.text = actividades.estatus
        holder?.textViewFecha?.text = actividades.fecha
        holder?.imageView?.setImageResource(actividades.image)
        if(estatus == "Terminado")
            holder?.texViewProceso?.setTextColor(Color.argb(255, 198, 198, 198))
        else
            holder?.texViewProceso?.setTextColor(Color.argb(255, 34, 34, 34))
    }

    override fun getFilter(): Filter {
        return object: Filter(){
            override fun performFiltering(constraint: CharSequence): Filter.FilterResults {
                val charString = constraint.toString()
                if (!charString.isEmpty()) {
                    val filterList = java.util.ArrayList<Actividad>()
                    for (a: Actividad in actividades){
                        if (a.name.toLowerCase().contains(charString.toLowerCase()) ||
                            a.cliente.toLowerCase().contains(charString.toLowerCase()) ||
                            a.fecha.toLowerCase().contains(charString.toLowerCase())) {
                            filterList.add(a)
                        }
                    }
                    mFilteredList = filterList
                }else{
                    mFilteredList = listaTemporal
                }
                val filterResults = Filter.FilterResults()
                filterResults.values = mFilteredList
                return filterResults
            }

            override fun publishResults(constraint: CharSequence, results: FilterResults) {
                mFilteredList = results.values as ArrayList<Actividad>
                notifyDataSetChanged()
                actividades = mFilteredList
            }
        }
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textViewName = itemView.findViewById<TextView>(R.id.nombreActividad)
        val textViewClientes = itemView.findViewById<TextView>(R.id.nombreCliente)
        val texViewProceso = itemView.findViewById<TextView>(R.id.nombreProceso)
        val textViewFecha = itemView.findViewById<TextView>(R.id.fecha)
        val imageView = itemView.findViewById<ImageView>(R.id.imagenActividades)
        val relativeLayout = itemView.findViewById<RelativeLayout>(R.id.item_main)
    }
}