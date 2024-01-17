package com.example.examen01

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import com.google.android.material.snackbar.Snackbar
import java.util.Locale

class IngresarDatosActorAct : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ingresar_datos_actor)

        val botonCrearActor = findViewById<Button>(R.id.btn_crear_autor)
        botonCrearActor.setOnClickListener {
            val nombre = findViewById<EditText>(R.id.input_nombre_actor)
            val fecha = findViewById<EditText>(R.id.input_fecha_actor)
            val casado = findViewById<EditText>(R.id.input_casado_actor)
                .text.toString().lowercase(Locale.getDefault())
            val casadoBooleano = casado == "si"
            val altura = findViewById<EditText>(R.id.input_altura_actor)
            val actor = Actor(
                null,
                nombre.text.toString(),
                fecha.text.toString(),
                casadoBooleano,
                altura.text.toString().toFloat()
            )
            val respuesta = actor.crearActor(this)
            if (respuesta) {
                mostrarSnackbar("Actor: ${nombre.text.toString()} ha sido creado")
                nombre.setText("")
                fecha.setText("")
                findViewById<EditText>(R.id.input_casado_actor).setText("")
                altura.setText("")
            }

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