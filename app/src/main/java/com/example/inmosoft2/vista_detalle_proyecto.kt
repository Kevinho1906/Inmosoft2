package com.example.inmosoft2

import ImagePagerAdapter
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.viewpager2.widget.ViewPager2
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.inmosoft2.Modelo.Adaptador_Cliente
import com.example.inmosoft2.Modelo.Proyecto
import com.squareup.picasso.Picasso
import org.json.JSONArray
import org.json.JSONException
import java.util.Timer
import java.util.TimerTask

class vista_detalle_proyecto : AppCompatActivity() {
    private lateinit var imageUrls: MutableList<String>
    private lateinit var viewPager: ViewPager2
    private lateinit var btnCotizar: Button
    private lateinit var txtTituloProyecto:TextView
    private lateinit var txtValorVivienda:TextView
    private lateinit var txtCostoSeparacion:TextView
    private lateinit var txtTipoProyecto: TextView
    private lateinit var txtUbicacion:TextView
    private lateinit var txtDireccion:TextView
    private lateinit var imagenPrincipal:ImageView
    private var currentPage = 0
    private val handler = Handler()
    private lateinit var runnable: Runnable

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_vista_detalle_proyecto)
        viewPager = findViewById(R.id.viewPager)

        btnCotizar = findViewById(R.id.btnCotizar)
        txtTituloProyecto = findViewById(R.id.txtTituloDetalleProyecto)
        imagenPrincipal = findViewById(R.id.imagenPrincipalDelProyecto)
        txtValorVivienda = findViewById(R.id.txtValorDeVivienda)
        txtCostoSeparacion = findViewById(R.id.txtSeparación)
        txtTipoProyecto = findViewById(R.id.txtTipoProyecto)
        txtUbicacion = findViewById(R.id.txtUbicacionDetalleProyecto)
        txtDireccion = findViewById(R.id.txtDireccion)

        // Inicializa la lista de URL de imágenes
        imageUrls = mutableListOf()

        btnCotizar.setOnClickListener { vistaCotizarProyecto() }
        obtenerDetalleCarruselProyecto()
        obtenerDetalleProyecto()
    }

    private fun vistaCotizarProyecto() {
        val idProyecto = intent.getStringExtra("proyectoId")
        val intent = Intent(this, vista_cotizar_proyecto::class.java)
        intent.putExtra("proyectoId", idProyecto)
        startActivity(intent)

    }
    private fun obtenerDetalleProyecto() {
        val idProyecto = intent.getStringExtra("proyectoId")
        val url = "https://inmosoft.pythonanywhere.com/buscarProyecto/$idProyecto"
        val requestQueue = Volley.newRequestQueue(this)
        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.GET, url, null,
            Response.Listener { response ->
                println("!!!!!!!!!!!!!!!!respuesta!!!!!!!"+response)
                var proyecto = response.getJSONObject("proyecto")
                txtTituloProyecto.text = proyecto.getString("nombre")
                var urlProyecto = "https://inmosoft.pythonanywhere.com/media/"+proyecto.getString("foto")
                Picasso.get()
                    .load(urlProyecto)
                    .placeholder(R.drawable.img_1)
                    .error(R.drawable.img_2)
                    .into(imagenPrincipal)
                txtValorVivienda.text = proyecto.getString("precio")
                txtCostoSeparacion.text = proyecto.getString("costoSeparacion")
                txtTipoProyecto.text = proyecto.getString("tipo")
                txtUbicacion.text = proyecto.getString("departamento")+" - "+proyecto.get("municipio")
                txtDireccion.text = proyecto.getString("direccion")
            },
            Response.ErrorListener { error ->
                println("!!!!Error!!!!! ${error.message}")
                Toast.makeText(this,"Error de Conexion", Toast.LENGTH_LONG).show()
            })

        requestQueue.add(jsonObjectRequest)
    }

    private fun obtenerDetalleCarruselProyecto() {
        val idProyecto = intent.getStringExtra("proyectoId")
        val url = "https://inmosoft.pythonanywhere.com/proyectoDetalleCarrusel/$idProyecto"
        val queue = Volley.newRequestQueue(this)
        val jsonListaProyecto = JsonObjectRequest(
            Request.Method.GET, url, null,
            { response ->
                try {
                    val proyectosArray = response.getJSONArray("imagenes")
                    for (i in 0 until proyectosArray.length()) {
                        val jsonObject = proyectosArray.getJSONObject(i)
                        val urlImagen = jsonObject.getString("imagen")
                        println(urlImagen)
                        imageUrls.add("https://inmosoft.pythonanywhere.com/media/$urlImagen")
                    }

                    // Configura el adaptador personalizado para el ViewPager
                    val adapter = ImagePagerAdapter(imageUrls)
                    viewPager.adapter = adapter

                    // Programa el carrusel automático
                    val timer = Timer()
                    val delay = 2000L // 2 segundos (ajusta el intervalo según tus necesidades)

                    runnable = Runnable {
                        if (currentPage == imageUrls.size) {
                            currentPage = 0
                        }
                        viewPager.currentItem = currentPage++
                    }

                    timer.schedule(object : TimerTask() {
                        override fun run() {
                            handler.post(runnable)
                        }
                    }, 0, delay)
                } catch (e: JSONException) {
                    e.printStackTrace()
                }
            },
            { error ->
                Toast.makeText(this, "Error de Conexión", Toast.LENGTH_LONG).show()
                println("Error -----> ${error}")
            }
        )
        queue.add(jsonListaProyecto)
    }
}
