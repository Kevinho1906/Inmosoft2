package com.example.inmosoft2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class vista_cotizar_proyecto : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_vista_cotizar_proyecto)
        val idProyecto = intent.getStringExtra("proyectoId")
        println("!!!!!!!!!!!!!!!!!!!!!!!!id!!!!!!!!!!!!!!!!!!!!"+idProyecto)
    }
}