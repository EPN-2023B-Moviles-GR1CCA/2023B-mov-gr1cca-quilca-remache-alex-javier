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
    val id: String?,
    val nombre: String,
    val fecha: String,
    val casado: Boolean,
    val altura: Float,
) {
    companion object {
        suspend fun obtenerActores(): List<Actor> {
            return try {
                val db = FirebaseFirestore.getInstance()
                val snapshot = db.collection("actores").get().await()
                snapshot.documents.mapNotNull { it.toObject(Actor::class.java) }
            } catch (e: Exception) {
                Log.e("Actor", "Error getting actors", e)
                emptyList()
            }
        }
    }

    suspend fun crearActor(): Boolean {
        val db = FirebaseFirestore.getInstance()
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
        val db = FirebaseFirestore.getInstance()
        return try {
            db.collection("actores").document(this.id!!).delete().await()
            true
        } catch (e: Exception) {
            false
        }
    }

    suspend fun actualizarActor(): Boolean {
        val db = FirebaseFirestore.getInstance()
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
}