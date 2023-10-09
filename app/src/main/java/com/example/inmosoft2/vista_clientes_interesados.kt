package com.example.inmosoft2

import android.app.Dialog
import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ListView
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.customview.customView
import com.android.volley.Request
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley
import com.example.inmosoft2.Modelo.Adaptador_Cliente_Interesado
import com.example.inmosoft2.Modelo.Cliente
import org.json.JSONException

class vista_clientes_interesados : AppCompatActivity() {
    lateinit var txtView: TextView
    private lateinit var listaClientesInteresados:MutableList<Cliente>
    private lateinit var listViewCliente: ListView
    private var progressDialog: ProgressDialog? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_vista_clientes_interesados)
        obtenerClientesInteresados()
        listaClientesInteresados = mutableListOf()
        listViewCliente = findViewById(R.id.listaClientesInteresados)
        txtView = findViewById(R.id.txtTitulo)
        txtView.setText("Clientes interesados en el proyecto: "+intent.getStringExtra("nombreProyecto"))
    }
    private fun AbrirVistaPrincipal() {
        val intent = Intent(this, vista_principal::class.java)
        startActivity(intent)
        finish()
    }

    private fun obtenerClientesInteresados() {
        val progressDialog = Dialog(this)
        progressDialog.setContentView(R.layout.custom_progress_dialog)
        progressDialog.setCancelable(false)
        val progressBar = progressDialog.findViewById<ProgressBar>(R.id.progressBar)
        val messageTextView = progressDialog.findViewById<TextView>(R.id.messageTextView)
        messageTextView.text = "" // Puedes cambiar el mensaje aquí

        progressDialog.show()

        var idProyecto = intent.getStringExtra("proyectoId")
        val url = "https://inmosoft.pythonanywhere.com/clienteInteresado/$idProyecto"
        val queue = Volley.newRequestQueue(this)
        val jsonListaProyecto = JsonArrayRequest(
            Request.Method.GET,url, null,
            { response ->
                try {
                    if(response.isNull(0)){
                        progressDialog.dismiss()
                        val customDialog = MaterialDialog(this).show {
                            customView(R.layout.custom_dialog_layout)

                            // Acceder a las vistas dentro del diseño personalizado
                            val customDialogTitle = view.findViewById<TextView>(R.id.customDialogTitle)
                            val customDialogMessage = view.findViewById<TextView>(R.id.customDialogMessage)
                            val customDialogPositiveButton = view.findViewById<Button>(R.id.customDialogPositiveButton)


                            customDialogTitle.text = "Proyecto "+intent.getStringExtra("nombreProyecto")
                            customDialogMessage.text = "No hay Clientes interesados en este Proyecto"
                            customDialogPositiveButton.text = "Aceptar"

                            customDialogPositiveButton.setOnClickListener {
                                AbrirVistaPrincipal()
                                dismiss()
                            }
                        }
                        customDialog.show()
                    }else{
                        progressDialog.dismiss()
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
                    }
                } catch (e: JSONException) {
                    e.printStackTrace()
                }
            }, { error->
                progressDialog.dismiss()
                Toast.makeText(this, "Error de Conexion", Toast.LENGTH_LONG).show()
                println("Error----{${error}}")
            })
        queue.add(jsonListaProyecto)
    }
}