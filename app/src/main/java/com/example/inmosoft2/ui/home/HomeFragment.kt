package com.example.inmosoft2.ui.home


import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ListView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.inmosoft2.Modelo.Adaptador
import com.example.inmosoft2.Modelo.Proyecto
import com.example.inmosoft2.R
import com.example.inmosoft2.databinding.FragmentHomeBinding
import com.example.inmosoft2.ui.Vista_Detalle_Proyecto.vista_detalle_proyecto
import org.json.JSONException


class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private lateinit var listaProyectos:MutableList<Proyecto>
    private lateinit var listViewProyectos: ListView
    private lateinit var btnVerDetalle: Button
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


    /*
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return super.onCreateView(inflater, container, savedInstanceState)

        Log.e("sds","Aquí")
        listaProyectos = mutableListOf()
        obtenerProyectos()
        return view
    }*/



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        listViewProyectos.setOnItemClickListener { parent, _, position, _ ->
            val nuevoFragmento = vista_detalle_proyecto.newInstance()
            val fragmentManager = parentFragmentManager
            Log.e("sds","Aquí")
            fragmentManager.beginTransaction()
                .replace(R.id.elementoProyecto, nuevoFragmento) // Reemplaza 'fragment_container' con el ID de tu contenedor de fragmentos
                .addToBackStack(null)
                .commit()
        }
    }


    private fun obtenerProyectos() {
        val url = "https://inmosoft.pythonanywhere.com/listarProyectos/"
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
                        val producto = Proyecto(urlImagen, nombre, ubicacion, precio)
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