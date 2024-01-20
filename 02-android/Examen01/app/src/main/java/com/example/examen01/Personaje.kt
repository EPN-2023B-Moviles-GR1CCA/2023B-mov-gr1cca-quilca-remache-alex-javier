package com.example.examen01

import android.content.ContentValues
import android.content.Context

class Personaje(
    val id: Int?,
    val nombre: String,
    val fecha: String,
    val pelicula: String,
    val actor_id: Int
) {
    companion object{
        fun obtenerPersonajes(context: Context, actor_id: Int): List<Personaje> {
            val db = SQLiteHelper(context).readableDatabase
            val cursor = db.query("PERSONAJE", null, "actor_id = ?", arrayOf(actor_id.toString()), null, null, null)

            val personajes = mutableListOf<Personaje>()
            with(cursor) {
                while (moveToNext()) {
                    val id = getInt(getColumnIndexOrThrow("id"))
                    val nombre = getString(getColumnIndexOrThrow("nombre"))
                    val fecha = getString(getColumnIndexOrThrow("fecha"))
                    val pelicula = getString(getColumnIndexOrThrow("pelicula"))
                    val idActor = getInt(getColumnIndexOrThrow("actor_id"))
                    personajes.add(Personaje(id, nombre, fecha, pelicula, idActor))
                }
            }
            cursor.close()
            db.close()
            return personajes
        }
    }

    fun crearPersonaje(context: Context): Boolean {
        val db = SQLiteHelper(context).writableDatabase
        val valores = ContentValues()
        valores.put("id", id)
        valores.put("nombre", this.nombre)
        valores.put("fecha", this.fecha)
        valores.put("pelicula", this.pelicula)
        valores.put("actor_id", this.actor_id)
        val crear = db.insert(
            "PERSONAJE",
            null,
            valores
        )
        db.close()
        return crear.toInt() != -1
    }

    fun actualizarPersonaje(context: Context): Boolean {
        val db = SQLiteHelper(context).writableDatabase
        val seleccion = arrayOf(this.id.toString())
        val valores = ContentValues()
        valores.put("nombre", this.nombre)
        valores.put("fecha", this.fecha)
        valores.put("pelicula", this.pelicula )
        valores.put("actor_id", this.actor_id)
        val actulizar = db.update("PERSONAJE", valores, "id=?", seleccion)
        db.close()
        return actulizar != -1

    }

    fun eliminarActor(context: Context): Boolean {
        val db = SQLiteHelper(context).writableDatabase
        val seleccion = arrayOf(this.id.toString())
        val eliminar = db.delete("PERSONAJE", "id=?", seleccion)
        db.close()
        return eliminar != -1
    }

    override fun toString(): String {
        return "ID: $id\nNombre: $nombre\nFecha: $fecha\nPelícula: $pelicula"
    }
}