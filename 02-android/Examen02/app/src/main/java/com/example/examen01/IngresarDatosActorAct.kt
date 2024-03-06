package com.example.examen01

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import com.google.android.material.snackbar.Snackbar
import java.util.Locale
import kotlinx.coroutines.*
import androidx.lifecycle.lifecycleScope

class IngresarDatosActorAct : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ingresar_datos_actor)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val botonCrearActor = findViewById<Button>(R.id.btn_crear_autor)
        botonCrearActor.setOnClickListener {
            val nombre = findViewById<EditText>(R.id.input_nombre_actor)
            val fecha = findViewById<EditText>(R.id.input_fecha_actor)
            val casado = findViewById<EditText>(R.id.input_casado_actor)
                .text.toString().lowercase(Locale.getDefault())
            val casadoBooleano = casado == "si"
            val altura = findViewById<EditText>(R.id.input_altura_actor)
            val inputAltura = altura.text.toString().toDoubleOrNull()

            if (nombre.text.toString().trim().isEmpty() ||
                fecha.text.toString().trim().isEmpty() ||
                casado.trim().isEmpty() ||
                altura.text.toString().trim().isEmpty()
                ){
                mostrarSnackbar("Por favor, llena  todos los campos")
            } else if (inputAltura == null) {
                mostrarSnackbar("La altura debe ser un número")
            } else {
                val actor = Actor(
                    null,
                    nombre.text.toString(),
                    fecha.text.toString(),
                    casadoBooleano,
                    altura.text.toString().toFloat()
                )
                lifecycleScope.launch {
                    val respuesta = actor.crearActor()
                    withContext(Dispatchers.Main) {
                        if (respuesta) {
                            mostrarSnackbar("Actor: \"${actor.nombre}\" ha sido creado")
                            nombre.setText("")
                            fecha.setText("")
                            findViewById<EditText>(R.id.input_casado_actor).setText("")
                            altura.setText("")
                        } else {
                            mostrarSnackbar("Error al crear el actor")
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
                findViewById(R.id.cl_ingresar_datos_actor), // view
                texto, // texto
                Snackbar.LENGTH_LONG // tiempo
            )
            .show()
    }
}