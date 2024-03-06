package com.example.examen01

import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await

class Personaje(
    val id: String?,
    val nombre: String,
    val fecha: String,
    val pelicula: String,
    val actor_id: String
) {
    companion object {
        private val db = Firebase.firestore

        suspend fun obtenerPersonajes(actor_id: String): List<Personaje> {
            val personajes = mutableListOf<Personaje>()
            val documents = db.collection("personajes")
                .whereEqualTo("actor_id", actor_id)
                .get()
                .await()
            for (document in documents) {
                val id = document.id
                val nombre = document.getString("nombre")!!
                val fecha = document.getString("fecha")!!
                val pelicula = document.getString("pelicula")!!
                val actor_id = document.getString("actor_id")!!
                personajes.add(Personaje(id, nombre, fecha, pelicula, actor_id))
            }
            return personajes
        }
    }

    suspend fun crearPersonaje(): Boolean {
        val character = hashMapOf(
            "nombre" to this.nombre,
            "fecha" to this.fecha,
            "pelicula" to this.pelicula,
            "actor_id" to this.actor_id
        )

        return try {
            db.collection("personajes")
                .add(character)
                .await()
            true
        } catch (e: Exception) {
            false
        }
    }

    suspend fun actualizarPersonaje(): Boolean {
        val character = hashMapOf(
            "nombre" to this.nombre,
            "fecha" to this.fecha,
            "pelicula" to this.pelicula,
            "actor_id" to this.actor_id
        )

        return try {
            db.collection("personajes").document(this.id!!)
                .set(character)
                .await()
            true
        } catch (e: Exception) {
            false
        }
    }

    suspend fun eliminarPersonaje(): Boolean {
        return try {
            db.collection("personajes").document(this.id!!)
                .delete()
                .await()
            true
        } catch (e: Exception) {
            false
        }
    }

    override fun toString(): String {
        return "ID: $id\nNombre: $nombre\nFecha: $fecha\nPelícula: $pelicula"
    }
}