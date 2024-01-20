package com.example.examen01

import android.content.ContentValues
import android.content.Context
import java.io.Serializable

class Actor(
    val id: Int?,
    val nombre: String,
    val fecha: String,
    val casado: Boolean,
    val altura: Float,
    //val context: Context?
) {

    companion object {
        fun obtenerActores(context: Context): List<Actor> {
            val db = SQLiteHelper(context).readableDatabase
            val cursor = db.query("ACTOR", null, null, null, null, null, null)

            val actores = mutableListOf<Actor>()
            with(cursor) {
                while (moveToNext()) {
                    val id = getInt(getColumnIndexOrThrow("id"))
                    val nombre = getString(getColumnIndexOrThrow("nombre"))
                    val fecha = getString(getColumnIndexOrThrow("fecha"))
                    val casado =
                        getInt(getColumnIndexOrThrow("casado")) != 0 // Convertir Int a Boolean
                    val altura = getFloat(getColumnIndexOrThrow("altura"))
                    actores.add(Actor(id, nombre, fecha, casado, altura))
                }
            }
            cursor.close()
            db.close()
            return actores
        }
    }

    fun crearActor(context: Context): Boolean {
        val db = SQLiteHelper(context).writableDatabase
        val valores = ContentValues()
        valores.put("id", id)
        valores.put("nombre", this.nombre)
        valores.put("fecha", this.fecha)
        valores.put("casado", if (this.casado) 1 else 0)
        valores.put("altura", this.altura)
        val crear = db.insert(
            "ACTOR",
            null,
            valores
        )
        db.close()
        return crear.toInt() != -1
    }

    fun eliminarActor(context: Context): Boolean {
        val db = SQLiteHelper(context).writableDatabase
        val seleccion = arrayOf(this.id.toString())
        val eliminar = db.delete("ACTOR", "id=?", seleccion)
        db.close()
        return eliminar != -1
    }

    fun actualizarActor(context: Context): Boolean {
        val db = SQLiteHelper(context).writableDatabase
        val seleccion = arrayOf(this.id.toString())
        val valores = ContentValues()
        valores.put("nombre", this.nombre)
        valores.put("fecha", this.fecha)
        valores.put("casado", if (this.casado) 1 else 0)
        valores.put("altura", this.altura)
        val actulizar = db.update("ACTOR", valores, "id=?", seleccion)
        db.close()
        return actulizar != -1

    }

    override fun toString(): String {
        val casado = if(this.casado) "si" else "no"
        return "ID: $id\nNombre: $nombre\nFecha: $fecha" +
                "\nCasado: $casado\nAltura: $altura"
    }


}