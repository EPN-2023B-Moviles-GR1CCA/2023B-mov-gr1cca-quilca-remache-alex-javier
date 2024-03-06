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
import kotlinx.coroutines.*
import androidx.lifecycle.lifecycleScope
import java.util.Locale

class EditarPersonajeAct : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_editar_personaje)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val textViewPersonaje = findViewById<TextView>(R.id.textView_Personaje)
        val NombrePersonaje = findViewById<EditText>(R.id.edit_nombre_personaje)
        val FechaPersonaje = findViewById<EditText>(R.id.edit_fecha_personaje)
        val PeliculaPersonaje = findViewById<EditText>(R.id.edit_pelicula_personaje)
        val IdPersonaje = intent.getStringExtra("id")
        val IdActor = intent.getStringExtra("idActor").toString()

        textViewPersonaje.text = intent.getStringExtra("nombre")
        NombrePersonaje.setText(intent.getStringExtra("nombre"))
        FechaPersonaje.setText(intent.getStringExtra("fecha"))
        PeliculaPersonaje.setText(intent.getStringExtra("pelicula"))

        val botonEditarPersonaje = findViewById<Button>(R.id.btn_editar_personaje)
        botonEditarPersonaje.setOnClickListener {

            if (NombrePersonaje.text.toString().trim().isEmpty() ||
                FechaPersonaje.text.toString().trim().isEmpty() ||
                PeliculaPersonaje.text.toString().trim().isEmpty()
                ) {
                mostrarSnackbar("Por favor, llena  todos los campos")
            } else {
                val personaje = Personaje(
                    IdPersonaje,
                    NombrePersonaje.text.toString(),
                    FechaPersonaje.text.toString(),
                    PeliculaPersonaje.text.toString(),
                    IdActor
                )
                lifecycleScope.launch {
                    val respuesta = personaje.actualizarPersonaje()
                    withContext(Dispatchers.Main) {
                        if (respuesta) {
                            val intent = Intent()
                            intent.putExtra("nombre",personaje.nombre)
                            setResult(Activity.RESULT_OK, intent)
                            finish()
                        }
                    }
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
                findViewById(R.id.cl_editar_personaje), // view
                texto, // texto
                Snackbar.LENGTH_LONG // tiempo
            )
            .show()
    }
}