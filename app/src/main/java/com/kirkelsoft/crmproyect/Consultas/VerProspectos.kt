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
import com.kirkelsoft.crmproyect.Adaptadores.AdapterProspectos
import com.kirkelsoft.crmproyect.Objetos.Prospectos
import kotlinx.android.synthetic.main.activity_ver_prospectos.*
import java.util.*
import com.kirkelsoft.crmproyect.R

class VerProspectos : AppCompatActivity() {

    lateinit var adapter: AdapterProspectos
    lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ver_prospectos)
        setSupportActionBar(toolbarProspectos)
        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val prospectos = ArrayList<Prospectos>()

        prospectos.add(Prospectos("Casa Ley S.A. de C.V.", "AC-Agricultura", "878989808"))
        prospectos.add(Prospectos("Agricola Don Silverio", "AC-Agricultura", "878989808"))
        prospectos.add(Prospectos("Nestle", "AC-Agricultura", "878989808"))
        prospectos.add(Prospectos("Omega Empresas Unidas", "AC-Agricultura", "878989808"))
        prospectos.add(Prospectos("SM Entertainment", "AC-Agricultura", "878989808"))
        prospectos.add(Prospectos("Agropecuario Amparan", "AC-Agricultura", "878989808"))
        prospectos.add(Prospectos("Pets Blue Home", "AC-Agricultura", "878989808"))
        prospectos.add(Prospectos("Armo Madera y Muebles", "AC-Agricultura", "878989808"))
        prospectos.add(Prospectos("Gedarasi S.A. de C.V.", "AC-Agricultura", "878989808"))

        adapter = AdapterProspectos(this, prospectos = prospectos)

        recyclerView = findViewById<RecyclerView>(R.id.recycleViewProspectos)
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
                search_view_prospectos.openSearch()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu_search, menu)

        val searchView = findViewById<MaterialSearchView>(R.id.search_view_prospectos);
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
