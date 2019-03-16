package com.kirkelsoft.crmproyect.Consultas

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import kotlinx.android.synthetic.main.activity_ver_razon_visita.*
import kotlinx.android.synthetic.main.include_razon_visita.*
import com.kirkelsoft.crmproyect.R

class VerRazonVisita : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ver_razon_visita)
        setSupportActionBar(toolbarRazonVisita)
        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        actividadCuentaCorriente.setOnClickListener{
            val intent = Intent()
            intent.putExtra("razonVisita", txtActividadCuentaCorriente.text)
            setResult(4, intent)
            finish()
        }

        censoMaquinaria.setOnClickListener{
            val intent = Intent()
            intent.putExtra("razonVisita", txtCensoMaquinaria.text)
            setResult(4, intent)
            finish()
        }

        cierreVenta.setOnClickListener{
            val intent = Intent()
            intent.putExtra("razonVisita", txtCierreVenta.text)
            setResult(4, intent)
            finish()
        }

        entregarMaquinaria.setOnClickListener{
            val intent = Intent()
            intent.putExtra("razonVisita", txtEntregarMaquinaria.text)
            setResult(4, intent)
            finish()
        }

        entrevistaNecesidades.setOnClickListener{
            val intent = Intent()
            intent.putExtra("razonVisita", txtEntrevistaNecesidades.text)
            setResult(4, intent)
            finish()
        }

        inspeccionMaquinaria.setOnClickListener{
            val intent = Intent()
            intent.putExtra("razonVisita", txtInspeccionMaquinaria.text)
            setResult(4, intent)
            finish()
        }

        presentacionCotizacion.setOnClickListener{
            val intent = Intent()
            intent.putExtra("razonVisita", txtPresentacionCotizacion.text)
            setResult(4, intent)
            finish()
        }

        presentacionInspeccion.setOnClickListener{
            val intent = Intent()
            intent.putExtra("razonVisita", txtPresentacionInspeccion.text)
            setResult(4, intent)
            finish()
        }

        seguimientoCotizaciones.setOnClickListener{
            val intent = Intent()
            intent.putExtra("razonVisita", txtSeguimientoCotizaciones.text)
            setResult(4, intent)
            finish()
        }

        seguimientoRenta.setOnClickListener{
            val intent = Intent()
            intent.putExtra("razonVisita", txtSeguimientoRenta.text)
            setResult(4, intent)
            finish()
        }

        tramiteFinanciamiento.setOnClickListener{
            val intent = Intent()
            intent.putExtra("razonVisita", txtTramiteFinanciamiento.text)
            setResult(4, intent)
            finish()
        }

        visitaCliente.setOnClickListener{
            val intent = Intent()
            intent.putExtra("razonVisita", txtVisitaCliente.text)
            setResult(4, intent)
            finish()
        }

        visitaRelacion.setOnClickListener{
            val intent = Intent()
            intent.putExtra("razonVisita", txtVisitaRelacion.text)
            setResult(4, intent)
            finish()
        }

        otros.setOnClickListener{
            val intent = Intent()
            intent.putExtra("razonVisita", txtOtros.text)
            setResult(4, intent)
            finish()
        }

    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item!!.itemId) {
            android.R.id.home -> {
                finish()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}