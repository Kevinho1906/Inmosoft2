package com.example.inmosoft2.ui.modificar_usuario

import android.content.Context
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.drawerlayout.widget.DrawerLayout
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.inmosoft2.R
//import com.google.firebase.firestore.auth.User
import com.example.inmosoft2.Modelo.User
import com.google.android.material.navigation.NavigationView
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import android.content.SharedPreferences

class modificar_usuario : Fragment() {

    private lateinit var listaUser:MutableList<User>
    //lateinit var imagenPerfil: ImageView
    //lateinit var txtUserName: EditText
    //lateinit var txtCedula: EditText
    //lateinit var txtNombre: EditText
    //lateinit var txtApellido: EditText
    //lateinit var txtCorreoPerfil: EditText
    //lateinit var txtTelefono: EditText
    //lateinit var btnGuardarCambios: Button

    companion object {
        fun newInstance() = modificar_usuario()
    }

    private lateinit var viewModel: ModificarUsuarioViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_modificar_usuario, container, false)
        val view = inflater.inflate(R.layout.fragment_modificar_usuario, container, false)

        val txtUserName = view.findViewById<EditText>(R.id.txtUserName)
        val txtCedula = view.findViewById<EditText>(R.id.txtCedula)
        val txtNombre = view.findViewById<EditText>(R.id.txtNombre)
        val txtApellido = view.findViewById<EditText>(R.id.txtApellido)
        val txtCorreoPerfil = view.findViewById<EditText>(R.id.txtCorreoPerfil)
        val txtTelefono = view.findViewById<EditText>(R.id.txtTelefono)
        val btnGuardarCambios = view.findViewById<Button>(R.id.btnGuardarCambios)



        btnGuardarCambios.setOnClickListener {
            val nombre = txtNombre.text.toString()
            val apellido = txtApellido.text.toString()
            val correo = txtCorreoPerfil.text.toString()
            val telefono = txtTelefono.text.toString()

            val idUsuario = obtenerIdUsuario() // Debes implementar esta función para obtener el ID del usuario actual

            modificarUsuario(idUsuario, nombre, apellido, correo, telefono)
        }

        return view
    }

    private fun obtenerIdUsuario(): Int {
        val sharedPreferences = requireContext().getSharedPreferences("MiAppPref", Context.MODE_PRIVATE)
        return sharedPreferences.getInt("idUser", -1) // -1 es un valor predeterminado en caso de que no se encuentre el ID
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(ModificarUsuarioViewModel::class.java)
        // TODO: Use the ViewModel



        listaUser = mutableListOf()
        obtenerUsuarios()
    }

    private fun obtenerUsuarios() {
        val url = "http://192.168.137.46:8000/user"
        val queue = Volley.newRequestQueue(requireContext())
        val jsonUsuario = JsonArrayRequest(Request.Method.GET,url, null,
            { response ->
                try {
                    for (i in 0 until response.length()){
                        val jsonObject = response.getJSONObject(i)
                        val id = jsonObject.getInt("id")
                        val imagenPerfil = jsonObject.getString("userFoto")
                        val userName = jsonObject.getString("username")
                        val cedula = jsonObject.getString("userCedula")
                        val nombre = jsonObject.getString("first_name")
                        val apellido = jsonObject.getString("last_name")
                        val correo = jsonObject.getString("email")
                        val telefono = jsonObject.getString("userTelefono")
                        val user = User(id, imagenPerfil, userName, cedula, nombre, apellido, correo, telefono)
                        listaUser.add(user)
                    }

                } catch (e: JSONException) {
                    e.printStackTrace()
                }
            }, { error->
                Toast.makeText(requireContext(), error.message, Toast.LENGTH_LONG).show()
            })
        queue.add(jsonUsuario)
    }

    private fun modificarUsuario(id: Int, nombre: String, apellido: String, correo: String, telefono: String) {
        val url = "http://192.168.137.46:8000/user/$id" // Reemplaza con la URL correcta de tu API

        // Crear un objeto JSON que contenga los datos actualizados del usuario
        val jsonRequest = JSONObject()
        jsonRequest.put("first_name", nombre)
        jsonRequest.put("last_name", apellido)
        jsonRequest.put("email", correo)
        jsonRequest.put("userTelefono", telefono)

        val queue = Volley.newRequestQueue(requireContext())

        val jsonObjectRequest = JsonObjectRequest(Request.Method.PUT, url, jsonRequest,
            { response ->
                // La modificación del usuario fue exitosa
                Toast.makeText(requireContext(), "Usuario modificado exitosamente", Toast.LENGTH_LONG).show()
            },
            { error ->
                // Manejar errores de conexión o respuesta del servidor
                Toast.makeText(requireContext(), "Error al modificar usuario: ${error.message}", Toast.LENGTH_LONG).show()
            })

        queue.add(jsonObjectRequest)
    }

}