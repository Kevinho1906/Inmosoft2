package com.example.inmosoft2.ui.home


import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkInfo
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat.getSystemService
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import androidx.lifecycle.ViewModelProvider
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.inmosoft2.Modelo.Adaptador
import com.example.inmosoft2.Modelo.Proyecto
import com.example.inmosoft2.R
import com.example.inmosoft2.databinding.FragmentHomeBinding
import org.json.JSONException


class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private lateinit var listaProyectos:MutableList<Proyecto>
    private lateinit var listViewProyectos: ListView
    //private lateinit var btnVerDetalle: Button
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        val homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        listaProyectos = mutableListOf()
        listViewProyectos = binding.listaProyectos
        obtenerProyectos()
        Log.e("sds","listando...")


        return root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        checkInternetConnection()

    }


    private fun checkInternetConnection() {
        val connectivityManager =
            requireActivity().getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo: NetworkInfo? = connectivityManager.activeNetworkInfo

        val imageView = view?.findViewById<ImageView>(R.id.internetImageView)
        val textView = view?.findViewById<TextView>(R.id.internetTextView)

        if (networkInfo == null || !networkInfo.isConnected) {
            // No hay conexión a Internet
            imageView?.visibility = View.VISIBLE
            textView?.visibility = View.VISIBLE
        } else {
            // Hay conexión a Internet
            imageView?.visibility = View.GONE
            textView?.visibility = View.GONE
        }
    }

    private fun obtenerProyectos() {
        val url = "http://192.168.137.177:8000/listarProyectosModificar/"
        val queue = Volley.newRequestQueue(requireContext())
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
                        listaProyectos.add(producto)
                    }
                    /*se crea un objeto de tipo adaptador donde se pasa como
                    parametro al layoutcreado y la liste de productos*/
                    val adaptador = Adaptador(requireContext(),
                        R.layout.activity_listar_proyectos, listaProyectos)
                    listViewProyectos.adapter = adaptador

                } catch (e: JSONException) {
                    e.printStackTrace()
                }
            }, { error->
                Toast.makeText(requireContext(), "Error de Conexion", Toast.LENGTH_LONG).show()
                println("Error----{${error}}")
            })
        queue.add(jsonListaProyecto)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}