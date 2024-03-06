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
import android.widget.EditText
import android.widget.ListView
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.*
import androidx.lifecycle.lifecycleScope
class ActorAct() : AppCompatActivity() {

    private lateinit var adaptador: ArrayAdapter<Actor>
    private lateinit var listView: ListView
    private lateinit var startForResult: ActivityResultLauncher<Intent>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_actor)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        startForResult =
            registerForActivityResult(
                ActivityResultContracts.StartActivityForResult()
            ) { result ->
                if (result.resultCode == Activity.RESULT_OK) {
                    val data: Intent? = result.data
                    val nombre = data?.getStringExtra("nombre")
                    mostrarSnackbar("El actor $nombre ha sido modificado")
                }
            }

        listView = findViewById<ListView>(R.id.lv_list_view_actor)
        adaptador = ArrayAdapter(this, android.R.layout.simple_list_item_1, mutableListOf())
        listView.adapter = adaptador

        val botonNuevoActor = findViewById<Button>(R.id.btn_nuevo_actor)
        botonNuevoActor.setOnClickListener {
            irActividad(IngresarDatosActorAct::class.java)
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
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        return when (item.itemId){
            R.id.mi_editar ->{
                val info = item.menuInfo as AdapterView.AdapterContextMenuInfo
                val actor = listView.adapter.getItem(info.position) as Actor
                val intent = Intent(this, EditarActorAct::class.java)
                intent.putExtra("id", actor.id)
                intent.putExtra("nombre", actor.nombre)
                intent.putExtra("fecha", actor.fecha)
                intent.putExtra("casado", actor.casado)
                intent.putExtra("altura", actor.altura)
                startForResult.launch(intent)
                return true
            }
            R.id.mi_eliminar ->{
                val info = item.menuInfo as AdapterView.AdapterContextMenuInfo
                val actor = listView.adapter.getItem(info.position) as Actor
                lifecycleScope.launch {
                    val respuesta = actor.eliminarActor()
                    withContext(Dispatchers.Main) {
                        if (respuesta) {
                            mostrarSnackbar("Actor \"${actor.nombre}\" con id = ${actor.id} ha sido eliminado")
                            actualizarListView()
                        } else {
                            mostrarSnackbar("Error al eliminar el actor")
                        }
                    }
                }
                return true
            }
            R.id.mi_verPersonajes ->{
                val info = item.menuInfo as AdapterView.AdapterContextMenuInfo
                val actor = listView.adapter.getItem(info.position) as Actor
                val intent = Intent(this, PersonajeAct::class.java)
                intent.putExtra("idActor", actor.id)
                intent.putExtra("nombreActor", actor.nombre)
                startActivity(intent)
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

    fun irActividad(
        clase: Class<*>
    ){
        val intent = Intent(this, clase)
        startActivity(intent)
    }

    fun mostrarSnackbar(texto:String){
        Snackbar
            .make(
                findViewById(R.id.lv_list_view_actor), // view
                texto, // texto
                Snackbar.LENGTH_LONG // tiempo
            )
            .show()
    }

    fun actualizarListView(){
        lifecycleScope.launch {
            val actores = Actor.obtenerActores()
            withContext(Dispatchers.Main) {
                adaptador.clear()
                if (actores.isNotEmpty()) {
                    adaptador.addAll(actores)
                    adaptador.notifyDataSetChanged()
                } else {
                    mostrarSnackbar("No hay actores disponibles")
                }
            }
        }
    }

}