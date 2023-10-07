package com.example.inmosoft2

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley

class inicio_sesion : AppCompatActivity() {

    lateinit var txtUsuario: EditText
    lateinit var txtContraseña: EditText
    lateinit var btnIniciarSesion: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_inicio_sesion)



        txtUsuario = findViewById(R.id.txtUsuario)
        txtContraseña = findViewById(R.id.txtContraseña)

        btnIniciarSesion = findViewById(R.id.btnIniciarSesion)

        //Eventos de los botones
        btnIniciarSesion.setOnClickListener { iniciarSesion() }
    }
    private fun validarDatos(): Boolean {
        return txtContraseña.text.isEmpty() || txtUsuario.text.isEmpty()
    }

    private fun iniciarSesion() {
        if (validarDatos()){
            Toast.makeText(this, "Faltan Datos", Toast.LENGTH_LONG).show()
        }else{
            val usuario = txtUsuario.text.toString()
            val contraseña = txtContraseña.text.toString()
            val url = "http://192.168.137.177:8000/Api/inicioSesion/${usuario}/${contraseña}"
            val requestQueue = Volley.newRequestQueue(this)
            val jsonObjectRequest = JsonObjectRequest(
                Request.Method.GET, url, null,
                Response.Listener { response ->
                    val idUsuario = response.getInt("idUser").toString()
                    val mensaje = response.getString("mensaje")
                    val estado = response.getBoolean("estado")
                    val nombre = response.getString("nombre")+" "+response.getString("apellidos")
                    val correo = response.getString("correo")
                    val foto = response.getString("foto")
                    if (estado) {
                        // El inicio de sesión fue exitoso
                        Toast.makeText(this, mensaje, Toast.LENGTH_LONG).show()
                        AbrirVistaPrincipal(nombre,correo,foto,idUsuario)
                    } else {
                        Toast.makeText(this, mensaje, Toast.LENGTH_LONG).show()
                    }
                },
                Response.ErrorListener { error ->
                    println("!!!!Error!!!!! ${error.message}")
                    Toast.makeText(this,"Error de Conexion", Toast.LENGTH_LONG).show()
                })

            requestQueue.add(jsonObjectRequest)
        }
    }

    private fun AbrirVistaPrincipal(nombre:String,correo:String,foto:String,idUser:String) {

        val intent = Intent(this, vista_principal::class.java)
        intent.putExtra("idUser",idUser)
        intent.putExtra("nombre", nombre)
        intent.putExtra("correo", correo)
        intent.putExtra("foto", foto)
        startActivity(intent)
    }

}