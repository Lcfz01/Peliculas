package com.example.peliculas

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast

class CrearCuenta : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_crear_cuenta)

        var regresar = findViewById<Button>(R.id.btnRegresar)
        var parametro = intent.extras

        Toast.makeText(this, parametro.toString(), Toast.LENGTH_SHORT).show()
        regresar.setOnClickListener {
            startActivity(Intent(this,Login::class.java))

        }
    }
}