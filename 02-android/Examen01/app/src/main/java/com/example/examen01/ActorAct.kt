package com.example.examen01

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import com.google.android.material.snackbar.Snackbar

class ActorAct() : AppCompatActivity() {

    private lateinit var adaptador: ArrayAdapter<Actor>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_actor)

        /*val actores = Actor.obtenerActores(this)
        val adaptador = ArrayAdapter(this, android.R.layout.simple_list_item_1, actores)
        val listView = findViewById<ListView>(R.id.lv_list_view_actor)
        listView.adapter = adaptador
        adaptador.notifyDataSetChanged()*/

        val listView = findViewById<ListView>(R.id.lv_list_view_actor)
        adaptador = ArrayAdapter(this, android.R.layout.simple_list_item_1, mutableListOf())
        listView.adapter = adaptador

        val botonNuevoActor = findViewById<Button>(R.id.btn_nuevo_actor)
        botonNuevoActor.setOnClickListener {
            irActividad(IngresarDatosActorAct::class.java)
        }
    }

    override fun onResume() {
        super.onResume()

        val actores = Actor.obtenerActores(this)
        adaptador.clear()
        adaptador.addAll(actores)
        adaptador.notifyDataSetChanged()
    }

    fun irActividad(
        clase: Class<*>
    ){
        val intent = Intent(this, clase)
        startActivity(intent)
    }

}