package com.example.inmosoft2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.inmosoft2.Modelo.Adaptador
import com.example.inmosoft2.Modelo.Adaptador_Cliente_Interesado
import com.example.inmosoft2.Modelo.Cliente
import com.example.inmosoft2.Modelo.Proyecto
import org.json.JSONException

class vista_detalle_proyecto : AppCompatActivity() {
    lateinit var txtView: TextView
    private lateinit var listaClientesInteresados:MutableList<Cliente>
    private lateinit var listViewCliente: ListView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_vista_detalle_proyecto)
        obtenerClientesInteresados()
        listaClientesInteresados = mutableListOf()
        listViewCliente = findViewById(R.id.listaClientesInteresados)
        txtView = findViewById(R.id.txtTitulo)
        txtView.setText("Clientes interesados en el proyecto: "+intent.getStringExtra("nombreProyecto"))
    }

    private fun obtenerClientesInteresados() {
        var idProyecto = intent.getStringExtra("proyectoId")
        val url = "http://192.168.137.177:8000/clienteInteresado/$idProyecto"
        val queue = Volley.newRequestQueue(this)
        val jsonListaProyecto = JsonArrayRequest(
            Request.Method.GET,url, null,
            { response ->
                try {
                    for (i in 0 until response.length()){
                        val jsonObject = response.getJSONObject(i)
                        val nombre = jsonObject.getString("cliNombre")
                        val apellido = jsonObject.getString("cliApellido")
                        val correo = jsonObject.getString("cliCorreo")
                        val telefono = jsonObject.getString("cliTelefono")
                        val cliente = Cliente(nombre, apellido, correo, telefono)
                        listaClientesInteresados.add(cliente)
                    }
                    /*se crea un objeto de tipo adaptador donde se pasa como
                    parametro al layoutcreado y la liste de productos*/
                    val adaptador = Adaptador_Cliente_Interesado(this,
                        R.layout.lista_clientes_interesados, listaClientesInteresados)
                    listViewCliente.adapter = adaptador

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