package com.kirkelsoft.crmproyect

import android.content.ActivityNotFoundException
import android.content.ComponentName
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.speech.RecognizerIntent
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.Menu
import android.view.MenuItem
import android.widget.LinearLayout
import android.widget.Toast
import br.com.mauker.materialsearchview.MaterialSearchView
import com.kirkelsoft.crmproyect.Adaptadores.AdapterActividad
import com.kirkelsoft.crmproyect.Objetos.Actividad
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity : AppCompatActivity() {
    lateinit var adapter: AdapterActividad
    lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)


        val actividades = ArrayList<Actividad>()
        actividades.add(
            Actividad(
                "Tarea opo 1816",
                R.drawable.tarea,
                "331504 - Iglesia Apostolica",
                "En Proceso",
                "24/04/2018",
                "07:34 p.m.",
                "Tarea",
                "",
                "Cierre de venta",
                "La Huerta Industria Mineria",
                "Diana Laura Ruiz",
                "sda",
                "30/07/2018",
                "07:34 p.m."
            )
        )
        actividades.add(
            Actividad(
                "Llamada MATCO",
                R.drawable.llamada,
                "331504 - Iglesia Apostolica",
                "Terminado",
                "24/04/2018",
                "07:34 p.m.",
                "Visita",
                "Llamada telefónica",
                "Cierre de venta",
                "La Huerta Industria Mineria",
                "Diana Laura Ruiz",
                "ds",
                "30/07/2018",
                "07:34 p.m."
            )
        )
        actividades.add(
            Actividad(
                "Viaje a Tucson",
                R.drawable.personal,
                "331506 - Comisión Federal",
                "Pendiente",
                "24/04/2018",
                "07:34 p.m.",
                "Visita",
                "Personal",
                "Cierre de venta",
                "La Huerta Industria Mineria",
                "Diana Laura Ruiz",
                "dfdf",
                "30/07/2018",
                "07:34 p.m."
            )
        )

        val sortedList = actividades.sortedWith(compareBy({ it.estatus }))
        actividades.clear()
        for (lista: Actividad in sortedList) {
            actividades.add(lista)
        }
        adapter = AdapterActividad(this, actividades = actividades)
        recyclerView = findViewById(R.id.recycleView)
        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayout.VERTICAL, false)
        recyclerView.adapter = adapter
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item!!.itemId) {
            R.id.menuSearch -> {
                search_view.openSearch()
                return true
            }
            R.id.menuCalendar -> {
                val intent = Intent()
                val cn = ComponentName("com.google.android.calendar",
                    "com.android.calendar.LaunchActivity")
                intent.component = cn
                startActivity(intent)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    fun pedirEntradaDeVoz() {
        val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
            RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault())
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT,"Diga algo compa")
        try {
            startActivityForResult(intent, MaterialSearchView.REQUEST_VOICE)
        } catch (a: ActivityNotFoundException) {
            a.printStackTrace()
            Toast.makeText(this, "\n" + "Disculpe, el dictado de texto no es compatible en su dispositivo.",
                Toast.LENGTH_SHORT).show()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu_search, menu)
        inflater.inflate(R.menu.menu_calendar, menu)
        val searchView = findViewById<MaterialSearchView>(R.id.search_view);
        searchView.setOnQueryTextListener(object : MaterialSearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(p0: String?): Boolean {
                searchView.closeSearch()
                searchView.setCloseOnTintClick(false)
                return false
            }
            override fun onQueryTextChange(p0: String?): Boolean {
                adapter.filter.filter(p0)
                return true
            }
        })
        searchView.setOnVoiceClickedListener(object : MaterialSearchView.OnVoiceClickedListener {
            override fun onVoiceClicked() {
                pedirEntradaDeVoz()
            }
        })
        searchView.setSearchViewListener(object : MaterialSearchView.SearchViewListener {
            override fun onSearchViewOpened() {
                fab.hide()
            }
            override fun onSearchViewClosed() {
                fab.show()
            }
        })
        return true
    }



}
