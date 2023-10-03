package com.example.inmosoft2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class Inicio : AppCompatActivity() {

    lateinit var btnInicioUsuario: Button
    lateinit var btnInicioCliente: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.inicio)

        btnInicioCliente = findViewById(R.id.btnInicioCliente)
        btnInicioUsuario = findViewById(R.id.btnInicioUsuario)

        btnInicioCliente.setOnClickListener { inicioCliente() }
        btnInicioUsuario.setOnClickListener { inicioUsuario() }

    }

    private fun inicioUsuario() {

        val intent = Intent(this, inicio_sesion::class.java)
        startActivity(intent)

    }

    private fun inicioCliente() {

        val intent = Intent(this, listar_proyectos_cliente::class.java)
        startActivity(intent)

    }

}