package com.example.inmosoft2.ui.modificar_usuario

//import com.google.firebase.firestore.auth.User
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.android.volley.Request
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.inmosoft2.R
import org.json.JSONException
import org.json.JSONObject

class modificar_usuario : Fragment() {

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

        return view
    }
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(ModificarUsuarioViewModel::class.java)
        // TODO: Use the ViewModel
        val idUser = arguments?.getString("idUser")
        println("AQUIIIIIIIIIIIIIIIIIIIIIIIIIII X4 " + idUser)
    }

    private fun obtenerUsuario() {
        val url = "http://192.168.137.177:8000/user"
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
        val url = "http://192.168.20.25:8000/user/$id" // Reemplaza con la URL correcta de tu API

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