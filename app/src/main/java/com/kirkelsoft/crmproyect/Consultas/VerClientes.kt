package com.kirkelsoft.crmproyect.Consultas

import android.app.SearchManager
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.speech.RecognizerIntent
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.Menu
import android.view.MenuItem
import android.widget.LinearLayout
import android.widget.SearchView
import android.widget.Toast
import br.com.mauker.materialsearchview.MaterialSearchView
import com.kirkelsoft.crmproyect.Adaptadores.AdapterClientes
import com.kirkelsoft.crmproyect.Objetos.Clientes
import kotlinx.android.synthetic.main.activity_ver_clientes.*
import java.util.*
import com.kirkelsoft.crmproyect.R

class VerClientes : AppCompatActivity() {

    lateinit var adapter: AdapterClientes
    lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ver_clientes)
        setSupportActionBar(toolbarClientes)
        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val clientes = ArrayList<Clientes>()

        clientes.add(Clientes(R.drawable.cliente, "Casa Ley S.A. de C.V."))
        clientes.add(Clientes(R.drawable.cliente, "Ingeniería de Ciudades"))
        clientes.add(Clientes(R.drawable.cliente, "WALMART"))
        clientes.add(Clientes(R.drawable.cliente, "Exportalizas Mexicanas"))
        clientes.add(Clientes(R.drawable.cliente, "Minería del Yaqui"))
        clientes.add(Clientes(R.drawable.cliente, "SAMS"))
        clientes.add(Clientes(R.drawable.cliente, "Dermaplastic"))
        clientes.add(Clientes(R.drawable.cliente, "nSoluciones"))
        clientes.add(Clientes(R.drawable.cliente, "Loteria Nacional S.A. de C.V."))
        clientes.add(Clientes(R.drawable.cliente, "Mercado Libre"))
        clientes.add(Clientes(R.drawable.cliente, "Amazon"))
        clientes.add(Clientes(R.drawable.cliente, "Vintage Outfit"))

        adapter = AdapterClientes(this, clientes = clientes)
        recyclerView = findViewById<RecyclerView>(R.id.recycleViewClientes)
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
                search_view_clientes.openSearch()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu_search, menu)

        val searchView = findViewById<MaterialSearchView>(R.id.search_view_clientes);
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