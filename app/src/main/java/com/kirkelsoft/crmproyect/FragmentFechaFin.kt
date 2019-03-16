package com.kirkelsoft.crmproyect

import android.app.Activity
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Intent
import android.icu.util.Calendar
import android.support.v4.app.Fragment
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import kotlinx.android.synthetic.main.fragment_fechafin.*
import java.text.SimpleDateFormat
import java.util.*

class FragmentFechaFin : Fragment() {

    fun obtenerHora() {
        val cal = Calendar.getInstance()
        val timeSetListener = TimePickerDialog.OnTimeSetListener { view, hourOfDay, minute ->
            cal.set(Calendar.HOUR_OF_DAY, hourOfDay)
            cal.set(Calendar.MINUTE, minute)
            val myFormat = "hh:mm a"
            val locale = Locale("es", "ES")
            val sdf = SimpleDateFormat(myFormat, locale)
            editHoraFin.setText(sdf.format(cal.time))
        }
        TimePickerDialog (this.context, timeSetListener,
            cal.get(Calendar.HOUR_OF_DAY),
            cal.get(Calendar.MINUTE), false).show()
    }

    fun obtenerFecha() {
        val cal = Calendar.getInstance()
        val dateSetListener = DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
            cal.set(Calendar.YEAR, year)
            cal.set(Calendar.MONTH, monthOfYear)
            cal.set(Calendar.DAY_OF_MONTH, dayOfMonth)
            val myFormat = "dd/MM/yyyy"
            val locale = Locale("es", "ES")
            val sdf = SimpleDateFormat(myFormat, locale)
            editFechaFin.setText(sdf.format(cal.time))
        }
        DatePickerDialog(this.context, dateSetListener,
            cal.get(Calendar.YEAR),
            cal.get(Calendar.MONTH),
            cal.get(Calendar.DAY_OF_MONTH)).show()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState:
    Bundle?): View? {
        val v = inflater!!.inflate(R.layout.fragment_fechafin, container, false)
        val fechaFin = v.findViewById<EditText>(R.id.editFechaFin)
        val horaFin = v.findViewById<EditText>(R.id.editHoraFin)
        fechaFin.setOnFocusChangeListener { view, b ->
            if (fechaFin.isFocused) {
                obtenerFecha()
            }
        }
        fechaFin.setOnClickListener {
            obtenerFecha()
        }
        horaFin.setOnFocusChangeListener { view, b ->
            if (horaFin.isFocused) {
                obtenerHora()
            }
        }
        horaFin.setOnClickListener {
            obtenerHora()
        }
        return v
    }
}