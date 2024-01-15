package com.example.gr1accajqr2023b

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.ComponentActivity


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val botonACicloVida = findViewById<Button>(R.id.btn_ciclo_vida)
        botonACicloVida
            .setOnClickListener {
                irActividad(ACicloVida::class.java)
            }

        val botonListView = findViewById<Button>(R.id.btn_ir_list_view)
        botonListView
            .setOnClickListener {
                irActividad(BListView::class.java)
            }
    }
    
    fun irActividad(
        clase: Class<*>
    ){
        val intent = Intent(this, clase)
        startActivity(intent)
    }
}
