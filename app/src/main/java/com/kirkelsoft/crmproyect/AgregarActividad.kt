package com.kirkelsoft.crmproyect

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import kotlinx.android.synthetic.main.activity_agregar_actividad.*
import java.util.*
import android.app.DatePickerDialog
import android.content.DialogInterface
import android.content.Intent
import android.icu.util.Calendar
import android.support.v7.app.AlertDialog
import kotlinx.android.synthetic.main.include_agregar_actividad.*
import java.text.SimpleDateFormat
import android.app.TimePickerDialog
import android.view.View
import com.kirkelsoft.crmproyect.Consultas.*
import kotlinx.android.synthetic.main.fragment_fechafin.*

class AgregarActividad : AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_agregar_actividad)
        toolbarAgregarActividad.setNavigationIcon(R.drawable.cerrar)
        setSupportActionBar(toolbarAgregarActividad)

        layoutClienteProspecto.setOnClickListener{
            if (switchProspecto.isChecked) {
                val intent = Intent(this, VerClientes::class.java)
                this.startActivityForResult(intent, 1)
            } else {
                val intent = Intent(this, VerClientes::class.java)
                this.startActivityForResult(intent, 1)
            }
        }
        layoutContacto.setOnClickListener { view ->
            val intent = Intent(this, VerContactos::class.java)
            this.startActivityForResult(intent, 2)
        }
        layoutOportunidades.setOnClickListener{
            val intent = Intent(this, VerOportunidades::class.java)
            this.startActivityForResult(intent, 3)
        }
        layoutRazonVisita.setOnClickListener{
            val intent = Intent(this, VerRazonVisita::class.java)
            this.startActivityForResult(intent, 4)
        }

        editTipoActividad.setOnFocusChangeListener { view, b ->
            if (editTipoActividad.isFocused)
                obtenerTipoActividad()
        }
        editTipoVisita.setOnFocusChangeListener { view, b ->
            if (editTipoVisita.isFocused)
                if (editTipoActividad.text.toString() == "Visita")
                    obtenerTipoVisita()
        }
        editFechaInicio.setOnFocusChangeListener { view, b ->
            if (editFechaInicio.isFocused)
                obtenerFecha()
        }
        editHoraInicio.setOnFocusChangeListener { view: View, b: Boolean ->
            if (editHoraInicio.isFocused)
                obtenerHora()
        }
        editEstaus.setOnFocusChangeListener { view, b ->
            if (editEstaus.isFocused)
                obtenerEstatus()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu_guardar, menu)
        return true
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
        super.onActivityResult(requestCode, resultCode, data)
        when (resultCode) {
            1 -> {
                val nombreCliente = data.getStringExtra("nombreCliente")
                val nombreProspecto = data.getStringExtra("nombreProspecto")
                val switch = data.getStringExtra("switchProspecto")
                if (switch == "true") {
                    switchProspecto.isChecked = true
                    editClienteProspecto.setText(nombreProspecto)
                } else
                    editClienteProspecto.setText(nombreCliente)
            }
            2 -> {
                val nombreContacto = data.getStringExtra("nombreContacto")
                editContacto.setText(nombreContacto)
            }
            3 -> {
                val nombreOportunidad = data.getStringExtra("nombreOportunidad")
                editOportunidades.setText(nombreOportunidad)
            }
            4 -> {
                val nombreRazonVisita = data.getStringExtra("razonVisita")
                editRazonVisita.setText(nombreRazonVisita)
            }
        }
    }




    fun obtenerHora() {
        val cal = Calendar.getInstance()
        val timeSetListener = TimePickerDialog.OnTimeSetListener { view, hourOfDay, minute ->
            cal.set(Calendar.HOUR_OF_DAY, hourOfDay)
            cal.set(Calendar.MINUTE, minute)
            val myFormat = "hh:mm a"
            val locale = Locale("es", "ES")
            val sdf = SimpleDateFormat(myFormat, locale)
            editHoraInicio.setText(sdf.format(cal.time))
        }
        TimePickerDialog (this, timeSetListener,
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
            editFechaInicio.setText(sdf.format(cal.time))
        }
        DatePickerDialog(this, dateSetListener,
            cal.get(Calendar.YEAR),
            cal.get(Calendar.MONTH),
            cal.get(Calendar.DAY_OF_MONTH)).show()
    }

    fun obtenerTipoActividad() {
        val builder = AlertDialog.Builder(this)
        val items = arrayOfNulls<CharSequence>(2)
        items[0] = "Tarea"
        items[1] = "Visita"
        builder.setTitle("Tipo de Actividad")
        builder.setItems(items, object : DialogInterface.OnClickListener {
            override fun onClick(p0: DialogInterface?, p1: Int) {
                if (items[p1] == "Tarea") {
                    editTipoActividad.setText("Tarea")
                    editTipoVisita.setText("")
                    editTipoVisita.editableText
                } else if (items[p1] == "Visita") {
                    editTipoActividad.setText("Visita")
                }
            }
        })
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }

    fun obtenerTipoVisita() {
        val builder = AlertDialog.Builder(this)
        val items = arrayOfNulls<CharSequence>(3)
        items[0] = "Personal"
        items[1] = "Llamada telefónica"
        items[2] = "Correo electrónico"
        builder.setTitle("Estatus")
        builder.setItems(items, object : DialogInterface.OnClickListener {
            override fun onClick(p0: DialogInterface?, p1: Int) {
                if (items[p1] == "Personal") {
                    editTipoVisita.setText("Personal")
                } else if (items[p1] == "Llamada telefónica") {
                    editTipoVisita.setText("Llamada telefónica")
                } else if (items[p1] == "Correo electrónico") {
                    editTipoVisita.setText("Correo electrónico")
                }
            }
        })
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }

    fun mostrarFragment() {
        val manager = supportFragmentManager
        val transaction = manager.beginTransaction()
        val fragment = FragmentFechaFin()
        transaction.replace(R.id.fragmentFechaFin, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }

    fun eliminarFragment() {
        val manager = supportFragmentManager
        val transaction = manager.beginTransaction()
        val fragment = manager.findFragmentById(R.id.fragmentFechaFin)
        transaction.remove(fragment)
        transaction.commit()
    }

    fun obtenerEstatus() {
        val builder = AlertDialog.Builder(this)
        val items = arrayOfNulls<CharSequence>(3)
        items[0] = "En Proceso"
        items[1] = "Pendiente"
        items[2] = "Terminado"
        builder.setTitle("Estatus")
        builder.setItems(items, object : DialogInterface.OnClickListener {
            override fun onClick(p0: DialogInterface?, p1: Int) {
                if (items[p1] == "En Proceso") {
                    eliminarFragment()
                    editEstaus.setText("En Proceso")
                } else if (items[p1] == "Pendiente") {
                    eliminarFragment()
                    editEstaus.setText("Pendiente")
                } else if (items[p1] == "Terminado") {
                    mostrarFragment()
                    editEstaus.setText("Terminado")
                }
            }
        })
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }

    fun dialogError() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Error al guardar")
        builder.setMessage("Asegúrese de llenar todos los datos.")
        builder.setPositiveButton("OK", object : DialogInterface.OnClickListener {
            override fun onClick(p0: DialogInterface?, p1: Int) {
                p0?.cancel()
            }
        })
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }

    fun dialogErrorFecha() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Error al guardar")
        builder.setMessage("La fecha de fin no debe ser igual a la fecha de inicio.")
        builder.setPositiveButton("OK", object : DialogInterface.OnClickListener {
            override fun onClick(p0: DialogInterface?, p1: Int) {
                p0?.cancel()
            }
        })
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item!!.itemId) {
            android.R.id.home -> {
                finish()
                return true
            }
            R.id.menuGuardar -> {
                val titulo = editTitulo.text.toString()
                val tipoActividad = editTipoActividad.text.toString()
                val tipoVisita = editTipoVisita.text.toString()
                val oportunidad = editOportunidades.text.toString()
                val clienteProspecto = editClienteProspecto.text.toString()
                val contacto = editContacto.text.toString()
                val fechaInicio = editFechaInicio.text.toString()
                val horaInicio = editHoraInicio.text.toString()
                val razonVisita = editRazonVisita.text.toString()
                val estatus = editEstaus.text.toString()
                val comentario = editComentario.text.toString()
                val fechaFin = editFechaFin?.text.toString()
                val horaFin = editHoraFin?.text.toString()

                if (titulo.isNotEmpty() && oportunidad.isNotEmpty() && clienteProspecto.isNotEmpty() && contacto.isNotEmpty() && tipoActividad.isNotEmpty() &&
                    fechaInicio.isNotEmpty() && estatus.isNotEmpty() && comentario.isNotEmpty()) {
                    if (tipoActividad == "Tarea" || (tipoActividad == "Visita" && tipoVisita.isNotEmpty() && razonVisita.isNotEmpty())) {
                        if (fechaInicio == fechaFin) {
                            dialogErrorFecha()
                        } else {
                            val intent = Intent(this, MainActivity::class.java)
                            intent.putExtra("tituloAct", titulo)
                            intent.putExtra("clienteProspectoAct", clienteProspecto)
                            intent.putExtra("estatusAct", estatus)
                            intent.putExtra("fechaInicioAct", fechaInicio)
                            intent.putExtra("tipoActividadAct", tipoActividad)
                            intent.putExtra("tipoVisitaAct", tipoVisita)
                            intent.putExtra("razonVisitaAct", razonVisita)
                            intent.putExtra("oportunidadAct", oportunidad)
                            intent.putExtra("contacto", contacto)
                            intent.putExtra("comentario", comentario)
                            intent.putExtra("horaInicioAct", horaInicio)
                            intent.putExtra("fechaFinAct", fechaFin)
                            intent.putExtra("horaFinAct", horaFin)
                            this.startActivity(intent)
                            return true
                        }
                    } else {
                        dialogError()
                    }
                } else {
                    dialogError()
                }
            }
        }
        return super.onOptionsItemSelected(item)
    }


}