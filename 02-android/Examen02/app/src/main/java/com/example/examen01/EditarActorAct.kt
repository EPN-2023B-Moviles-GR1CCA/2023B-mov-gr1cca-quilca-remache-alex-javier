package com.example.examen01

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.google.android.material.snackbar.Snackbar
import java.util.Locale

class EditarActorAct : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_editar_actor)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val textViewActor = findViewById<TextView>(R.id.textView_Actor)
        val NombreActor = findViewById<EditText>(R.id.edit_nombre_actor)
        val FechaActor = findViewById<EditText>(R.id.edit_fecha_actor)
        val CasadoActor = findViewById<EditText>(R.id.edit_casado_actor)
        val AlturaActor = findViewById<EditText>(R.id.edit_altura_actor)
        val IdActor = intent.getIntExtra("id",0)

        textViewActor.text = intent.getStringExtra("nombre")
        NombreActor.setText(intent.getStringExtra("nombre"))
        FechaActor.setText(intent.getStringExtra("fecha"))
        CasadoActor.setText(
            if (
                intent.getBooleanExtra("casado",
                    false)) "si"
            else "no")
        AlturaActor.setText(intent.getFloatExtra(
            "altura",
            0.0f)
            .toString())

        val botonEditarActor = findViewById<Button>(R.id.btn_editar_actor)
        botonEditarActor.setOnClickListener {
            val casado = CasadoActor.text.toString().lowercase(Locale.getDefault())
            val casadoBooleano = casado == "si"

            if (NombreActor.text.toString().trim().isEmpty() ||
                FechaActor.text.toString().trim().isEmpty() ||
                CasadoActor.text.toString().trim().isEmpty() ||
                AlturaActor.text.toString().trim().isEmpty()
                ) {
                mostrarSnackbar("Por favor, llena  todos los campos")
            } else if (AlturaActor.text.toString().toDoubleOrNull() == null) {
                mostrarSnackbar("La altura debe ser un número")
            } else {
                val actor = Actor(
                    IdActor,
                    NombreActor.text.toString(),
                    FechaActor.text.toString(),
                    casadoBooleano,
                    AlturaActor.text.toString().toFloat()
                )
                val respuesta = actor.actualizarActor(this)
                if (respuesta) {
                    val intent = Intent()
                    intent.putExtra("nombre",actor.nombre)
                    setResult(Activity.RESULT_OK, intent)
                    finish()
                }
            }
        }


    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                finish()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    fun mostrarSnackbar(texto: String) {
        Snackbar
            .make(
                findViewById(R.id.cl_editar_actor), // view
                texto, // texto
                Snackbar.LENGTH_LONG // tiempo
            )
            .show()
    }
}