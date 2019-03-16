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
import com.kirkelsoft.crmproyect.Adaptadores.AdapterContactos
import com.kirkelsoft.crmproyect.Objetos.Contactos
import com.kirkelsoft.crmproyect.R

import kotlinx.android.synthetic.main.activity_ver_contactos.*
import java.util.*

class VerContactos : AppCompatActivity() {

    lateinit var adapter: AdapterContactos
    lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ver_contactos)
        setSupportActionBar(toolbarContactos)
        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val contactos = ArrayList<Contactos>()

        contactos.add(Contactos(R.drawable.fondo, "C", "Clarissa Flores Valenzuela", "clarisac@hotmail.com", "6441898778"))
        contactos.add(Contactos(R.drawable.fondo, "D", "Diana Laura Ruiz", "dyanar_04@hotmail.com", "6441898778"))
        contactos.add(Contactos(R.drawable.fondo, "G", "Gerardo I. Ortega", "gerardo_1007@gmail.com", "6441898778"))
        contactos.add(Contactos(R.drawable.fondo, "J", "Jorge Luis Zaragoza", "jorgito@gmail.com", "6441898778"))
        contactos.add(Contactos(R.drawable.fondo, "C", "Carlos E. Jiménez", "carlos_conejo@hotmail.com", "6441898778"))
        contactos.add(Contactos(R.drawable.fondo, "S", "Shariany Ibarra Marquez", "shary_7@gmail.com", "6441898778"))
        contactos.add(Contactos(R.drawable.fondo, "M", "Mariana Berrelleza", "marianita_bonita21@hotmail.com", "6441898778"))
        contactos.add(Contactos(R.drawable.fondo, "P", "Park Jin Young", "jinyoung@hotmail.com", "6441898778"))
        contactos.add(Contactos(R.drawable.fondo, "C", "Choi Min Ho", "minho_26@hotmail.com", "6441898778"))
        contactos.add(Contactos(R.drawable.fondo, "L", "Lee Tae Min", "taemin@gmail.com", "6441898778"))
        contactos.add(Contactos(R.drawable.fondo, "K", "Kim Ki Bum", "key_year@hotmail.com", "6441898778"))
        contactos.add(Contactos(R.drawable.fondo, "K", "Kim Jin Ki", "onew_appa@hotmail.com", "6441898778"))

        adapter = AdapterContactos(this, contactos = contactos)
        recyclerView = findViewById(R.id.recycleViewContactos)
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
                search_view_contactos.openSearch()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu_search, menu)

        val searchView = findViewById<MaterialSearchView>(R.id.search_view_contactos);
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