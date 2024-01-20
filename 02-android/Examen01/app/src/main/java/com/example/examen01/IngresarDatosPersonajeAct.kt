package com.example.examen01

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import com.google.android.material.snackbar.Snackbar
import java.util.Locale

class IngresarDatosPersonajeAct : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ingresar_datos_personaje)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val idActor = intent.getIntExtra("idActor", 0)

        val botonCrearPersonaje = findViewById<Button>(R.id.btn_crear_personaje)
        botonCrearPersonaje.setOnClickListener {
            val nombre = findViewById<EditText>(R.id.input_nombre_personaje)
            val fecha = findViewById<EditText>(R.id.input_fecha_personaje)
            val pelicula = findViewById<EditText>(R.id.input_pelicula_personaje)

            if(nombre.text.toString().trim().isEmpty() ||
                fecha.text.toString().trim().isEmpty() ||
                pelicula.text.toString().trim().isEmpty()
                ){
                mostrarSnackbar("Por favor, llena  todos los campos")
            } else {
                val personaje = Personaje(
                    null,
                    nombre.text.toString(),
                    fecha.text.toString(),
                    pelicula.text.toString(),
                    idActor
                )
                val respuesta = personaje.crearPersonaje(this)
                if (respuesta) {
                    mostrarSnackbar("Personaje: \"${personaje.nombre}\" ha sido creado")
                    nombre.setText("")
                    fecha.setText("")
                    pelicula.setText("")
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
                findViewById(R.id.cl_ingresar_datos_personaje), // view
                texto, // texto
                Snackbar.LENGTH_LONG // tiempo
            )
            .show()
    }
}