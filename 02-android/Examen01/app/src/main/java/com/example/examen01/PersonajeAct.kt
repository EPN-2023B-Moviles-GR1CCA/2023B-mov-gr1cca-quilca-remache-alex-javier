package com.example.examen01

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ContextMenu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import android.widget.TextView
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import com.google.android.material.snackbar.Snackbar

class PersonajeAct : AppCompatActivity() {

    private lateinit var adaptador: ArrayAdapter<Personaje>
    private lateinit var listView: ListView
    private var idActor: Int = 0
    private lateinit var startForResult: ActivityResultLauncher<Intent>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_personaje)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        startForResult =
            registerForActivityResult(
                ActivityResultContracts.StartActivityForResult()
            ) { result ->
                if (result.resultCode == Activity.RESULT_OK) {
                    val data: Intent? = result.data
                    val nombre = data?.getStringExtra("nombre")
                    mostrarSnackbar("El personaje \"$nombre\" ha sido modificado")
                }
            }

        idActor = intent.getIntExtra("idActor", 0)
        val nombreActor = intent.getStringExtra("nombreActor")

        val textViewActorPersonaje = findViewById<TextView>(R.id.textView_actor_personaje)
        textViewActorPersonaje.text = nombreActor

        listView = findViewById<ListView>(R.id.lv_list_view_personaje)
        adaptador = ArrayAdapter(this, android.R.layout.simple_list_item_1, mutableListOf())
        listView.adapter = adaptador


        val botonNuevoPersonaje = findViewById<Button>(R.id.btn_nuevo_personaje)
        botonNuevoPersonaje.setOnClickListener{
            val intent = Intent(this, IngresarDatosPersonajeAct::class.java)
            intent.putExtra("idActor",idActor)
            startActivity(intent)
        }




        registerForContextMenu(listView)
    }

    override fun onResume() {
        super.onResume()
        actualizarListView()
    }

    override fun onCreateContextMenu(
        menu: ContextMenu?,
        v: View?,
        menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        super.onCreateContextMenu(menu, v, menuInfo)
        // Llenamos las opciones del menu
        val inflater = menuInflater
        inflater.inflate(R.menu.menu, menu)
        if (menu != null) {
            menu.findItem(R.id.mi_verPersonajes).isVisible = false
        }
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        return when (item.itemId){
            R.id.mi_editar ->{
                val info = item.menuInfo as AdapterView.AdapterContextMenuInfo
                val personaje = listView.adapter.getItem(info.position) as Personaje
                val intent = Intent(this, EditarPersonajeAct::class.java)
                intent.putExtra("id", personaje.id)
                intent.putExtra("nombre", personaje.nombre)
                intent.putExtra("fecha", personaje.fecha)
                intent.putExtra("pelicula", personaje.pelicula)
                intent.putExtra("idActor", personaje.actor_id)
                startForResult.launch(intent)
                return true
            }
            R.id.mi_eliminar ->{
                val info = item.menuInfo as AdapterView.AdapterContextMenuInfo
                val personaje = listView.adapter.getItem(info.position) as Personaje
                personaje.eliminarActor(this);
                mostrarSnackbar("Personaje \"${personaje.nombre}\" con id = ${personaje.id} ha sido eliminado")
                actualizarListView()
                return true
            }
            else -> super.onContextItemSelected(item)
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

    fun mostrarSnackbar(texto:String){
        Snackbar
            .make(
                findViewById(R.id.lv_list_view_personaje), // view
                texto, // texto
                Snackbar.LENGTH_LONG // tiempo
            )
            .show()
    }

    fun actualizarListView(){
        val personajes = Personaje.obtenerPersonajes(this, idActor)
        adaptador.clear()
        if (personajes.isNotEmpty()) {
            adaptador.addAll(personajes)
            adaptador.notifyDataSetChanged()
        } else {
            mostrarSnackbar("No hay personajes para este Actor")
        }
    }

}