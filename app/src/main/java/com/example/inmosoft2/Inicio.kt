package com.example.inmosoft2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class Inicio : AppCompatActivity() {

    lateinit var btnInicio: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.inicio)

        btnInicio = findViewById(R.id.btnInicio)

        btnInicio.setOnClickListener { inicioSesion() }

    }

    private fun inicioSesion() {

        val intent = Intent(this, inicio_sesion::class.java)
        startActivity(intent)

    }

}