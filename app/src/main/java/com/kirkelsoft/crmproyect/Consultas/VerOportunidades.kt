package com.kirkelsoft.crmproyect.Consultas

import android.content.ActivityNotFoundException
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
import com.kirkelsoft.crmproyect.Adaptadores.AdapterOportunidades
import com.kirkelsoft.crmproyect.Objetos.Oportunidades
import kotlinx.android.synthetic.main.activity_ver_oportunidades.*
import java.util.*
import com.kirkelsoft.crmproyect.R

class VerOportunidades : AppCompatActivity() {

    lateinit var adapter: AdapterOportunidades
    lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ver_oportunidades)
        setSupportActionBar(toolbarOportunidades)

        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val oportunidad = ArrayList<Oportunidades>()

        oportunidad.add(Oportunidades("La Huerta Industria Mineria", "OP-00-071401", "345BL1"))
        oportunidad.add(Oportunidades("Yeso Industrial de Navojoa", "OP-00-071402", "345BL2"))
        oportunidad.add(Oportunidades("Universidad La Salle Noroeste", "OP-00-071403", "345BL3"))
        oportunidad.add(Oportunidades("AGRIEXPORT S.A. de C.V.", "OP-00-071404", "345BL4"))
        oportunidad.add(Oportunidades("Mineria Alamos de Sonora", "OP-00-071405", "345BL5"))
        oportunidad.add(Oportunidades("Iglesia Bautista Independiente", "OP-00-071406", "345BL6"))
        oportunidad.add(Oportunidades("Videxport S.A. de C.V.", "OP-00-071407", "345BL7"))
        oportunidad.add(Oportunidades("La Huerta Industria Mineria", "OP-00-071408", "345BL8"))
        oportunidad.add(Oportunidades("Universidad La Salle Noroeste", "OP-00-071409", "345BL9"))
        oportunidad.add(Oportunidades("AGRIEXPORT S.A. de C.V.", "OP-00-071410", "346BL0"))
        oportunidad.add(Oportunidades("Mineria Alamos de Sonora", "OP-00-071411", "346BL1"))

        adapter = AdapterOportunidades(this, oportunidades = oportunidad)
        recyclerView = findViewById<RecyclerView>(R.id.recycleViewOportunidades)
        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayout.VERTICAL, false)
        recyclerView.adapter = adapter
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item!!.itemId) {
            android.R.id.home -> {
                finish()
                return true
            }
            R.id.menuSearch -> {
                search_view_oportunidades.openSearch()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu_search, menu)

        val searchView = findViewById<MaterialSearchView>(R.id.search_view_oportunidades);
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
        return true
    }

    fun pedirEntradaDeVoz() {
        val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault())
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT,"Di algo")
        try {
            startActivityForResult(intent, MaterialSearchView.REQUEST_VOICE)
        } catch (a: ActivityNotFoundException) {
            a.printStackTrace()
            Toast.makeText(this, "\n" + "Disculpe, el dictado de texto no es compatible en su dispositivo.", Toast.LENGTH_SHORT).show()
        }
    }
}
