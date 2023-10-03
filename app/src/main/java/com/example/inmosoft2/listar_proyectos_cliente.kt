package com.example.inmosoft2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ListView
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.inmosoft2.Modelo.Adaptador
import com.example.inmosoft2.Modelo.Adaptador_Cliente
import com.example.inmosoft2.Modelo.Proyecto
import org.json.JSONException

class listar_proyectos_cliente : AppCompatActivity() {

    private lateinit var listaProyectosCliente:MutableList<Proyecto>
    lateinit var listViewProyectos: ListView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_listar_proyectos_cliente)

        listaProyectosCliente = mutableListOf()
        listViewProyectos = findViewById(R.id.listaProyectosCliente)
        obtenerProyectos()
    }

    private fun obtenerProyectos() {
        val url = "http://192.168.137.177:8000/listarProyectos/"
        val queue = Volley.newRequestQueue(this)
        val jsonListaProyecto = JsonObjectRequest(
            Request.Method.GET,url, null,
            { response ->
                try {
                    val proyectosArray = response.getJSONArray("proyectos")
                    Log.d("Proyectos", "Número de proyectos recibidos: ${proyectosArray.length()}")
                    for (i in 0 until proyectosArray.length()){
                        val jsonObject = proyectosArray.getJSONObject(i)
                        val urlImagen = jsonObject.getString("foto")
                        val nombre = jsonObject.getString("nombre")
                        val ubicacion = jsonObject.getString("ubicacion")
                        val precio = jsonObject.getString("precio")
                        val idProyecto = jsonObject.getString("id")
                        val producto = Proyecto(urlImagen, nombre, ubicacion, precio,idProyecto)
                        listaProyectosCliente.add(producto)
                    }
                    /*se crea un objeto de tipo adaptador donde se pasa como
                    parametro al layoutcreado y la liste de productos*/
                    val adaptador = Adaptador_Cliente(this,
                        R.layout.listar_proyectos_cliente, listaProyectosCliente)
                    listViewProyectos.adapter = adaptador

                } catch (e: JSONException) {
                    e.printStackTrace()
                }
            }, { error->
                Toast.makeText(this, "Error de Conexion", Toast.LENGTH_LONG).show()
                println("Error----{${error}}")
            })
        queue.add(jsonListaProyecto)
    }
}