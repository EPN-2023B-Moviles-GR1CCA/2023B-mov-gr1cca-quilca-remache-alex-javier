package com.example.examen01

import android.content.ContentValues
import android.content.Context

class Actor(
    val id: Int?,
    val nombre: String,
    val fecha: String,
    val casado: Boolean,
    val altura: Float,
    //val context: Context?
) {

    companion object{
        fun obtenerActores(context: Context): List<Actor> {
            val db = SQLiteHelper(context).readableDatabase
            val cursor = db.query("ACTOR", null, null, null, null, null, null)

            val actores = mutableListOf<Actor>()
            with(cursor) {
                while (moveToNext()) {
                    val id = getInt(getColumnIndexOrThrow("id"))
                    val nombre = getString(getColumnIndexOrThrow("nombre"))
                    val fecha = getString(getColumnIndexOrThrow("fecha"))
                    val casado = getInt(getColumnIndexOrThrow("casado")) != 0 // Convertir Int a Boolean
                    val altura = getFloat(getColumnIndexOrThrow("altura"))
                    actores.add(Actor(id, nombre, fecha, casado, altura))
                }
            }
            cursor.close()
            db.close()
            return actores
        }
    }
    fun crearActor(context: Context) : Boolean{
        val db = SQLiteHelper(context).writableDatabase
        val valores = ContentValues()
        valores.put("id",id);
        valores.put("nombre",this.nombre);
        valores.put("fecha",this.fecha);
        valores.put("casado",if (this.casado) 1 else 0);
        valores.put("altura",this.altura);
        val crear = db.insert(
            "ACTOR",
            null,
            valores
        )
        db.close()
        return if (crear.toInt() == -1) false else true
    }

    override fun toString(): String {
        return "ID: $id\nNombre: $nombre\nFecha: $fecha" +
                "\nCasado: $casado\nAltura: $altura"
    }


}