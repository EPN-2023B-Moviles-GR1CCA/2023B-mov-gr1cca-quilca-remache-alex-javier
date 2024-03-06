package com.example.examen01

import android.content.ContentValues
import android.content.Context
import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import java.io.Serializable
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await

class Actor(
    val id: String? = null,
    val nombre: String = "",
    val fecha: String = "",
    val casado: Boolean = false,
    val altura: Float = 0f,
) {
    companion object {
        suspend fun obtenerActores(): List<Actor> {
            val actores = mutableListOf<Actor>()
            return try {
                val db = Firebase.firestore
                val documents = db.collection("actores").get().await()
                for (document in documents) {
                    val id = document.id
                    val nombre = document.getString("nombre")!!
                    val fecha = document.getString("fecha")!!
                    val casado = document.getBoolean("casado")!!
                    val altura = document.getDouble("altura")!!.toFloat()
                    actores.add(Actor(id, nombre, fecha, casado, altura))
                }
                actores
            } catch (e: Exception) {
                Log.e("Actor", "Error al obtener los actores", e)
                emptyList()
            }
        }
    }

    suspend fun crearActor(): Boolean {
        val db = Firebase.firestore
        val actorMap = hashMapOf(
            "nombre" to this.nombre,
            "fecha" to this.fecha,
            "casado" to this.casado,
            "altura" to this.altura
        )
        return try {
            db.collection("actores").add(actorMap).await()
            true
        } catch (e: Exception) {
            false
        }
    }

    suspend fun eliminarActor(): Boolean {
        val db = Firebase.firestore
        return try {
            val documents = db.collection("personajes")
                .whereEqualTo("actor_id", this.id!!)
                .get()
                .await()

            for (document in documents) {
                db.collection("personajes").document(document.id).delete().await()
            }

            // Delete the actor
            db.collection("actores").document(this.id!!).delete().await()
            true
        } catch (e: Exception) {
            false
        }
    }

    suspend fun actualizarActor(): Boolean {
        val db = Firebase.firestore
        val actorMap = hashMapOf(
            "nombre" to this.nombre,
            "fecha" to this.fecha,
            "casado" to this.casado,
            "altura" to this.altura
        )
        return try {
            db.collection("actores").document(this.id!!).set(actorMap).await()
            true
        } catch (e: Exception) {
            false
        }
    }

    override fun toString(): String {
        val casado = if(this.casado) "si" else "no"
        return "ID: $id\nNombre: $nombre\nFecha: $fecha" +
                "\nCasado: $casado\nAltura: $altura"
    }
}