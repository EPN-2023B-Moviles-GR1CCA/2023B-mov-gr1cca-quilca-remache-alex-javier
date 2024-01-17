package com.example.examen01

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class SQLiteHelper(
    contexto: Context?
    ) : SQLiteOpenHelper(
        contexto,
        "Examen01",
        null,
        1
    ){
    override fun onCreate(db: SQLiteDatabase?) {
        val scriptSQLCrearTablaActor =
            """
               CREATE TABLE ACTOR(
               id INTEGER PRIMARY KEY AUTOINCREMENT,
               nombre TEXT,
               fecha TEXT,
               casado INTEGER,
               altura REAL
               ) 
            """.trimIndent()
        db?.execSQL(scriptSQLCrearTablaActor)

        val scriptSQLCrearTablaPersonaje =
            """
               CREATE TABLE PERSONAJE(
               id INTEGER PRIMARY KEY AUTOINCREMENT,
               nombre TEXT,
               fecha TEXT,
               pelicula TEXT,
               famoso INTEGER,
               actor_id INTEGER,
               FOREIGN KEY(actor_id) REFERENCES ACTOR(id)
               ) 
            """.trimIndent()
        db?.execSQL(scriptSQLCrearTablaPersonaje)
    }

    override fun onUpgrade(p0: SQLiteDatabase?,
                           p1: Int,
                           p2: Int) {}

}