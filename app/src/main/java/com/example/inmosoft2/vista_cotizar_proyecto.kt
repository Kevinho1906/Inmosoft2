package com.example.inmosoft2

import android.app.Dialog
import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.core.text.set
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import org.json.JSONObject
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.customview.customView

class vista_cotizar_proyecto : AppCompatActivity() {
    private lateinit var txtCedula:EditText
    private lateinit var txtNombre:EditText
    private lateinit var txtApellido:EditText
    private lateinit var txtTelefono:EditText
    private lateinit var txtCorreo:EditText
    private lateinit var btnEnviar:Button
    private var progressDialog: ProgressDialog? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_vista_cotizar_proyecto)
        txtCedula  = findViewById(R.id.txtCedulaCotizarProyecto)
        txtNombre = findViewById(R.id.txtNombreCotizarProyecto)
        txtApellido = findViewById(R.id.txtApellidoCotizarProyecto)
        txtTelefono = findViewById(R.id.txtTelefonoCotizarProyecto)
        txtCorreo = findViewById(R.id.txtCorreoCotizarProyecto)
        btnEnviar = findViewById(R.id.btnEnviarCotizacion)
        btnEnviar.setOnClickListener { enviarCotizacion() }
    }
    private fun validarDatos():Boolean{
        return txtCedula.text.isEmpty() || txtNombre.text.isEmpty() || txtApellido.text.isEmpty() ||
                txtTelefono.text.isEmpty() || txtCorreo.text.isEmpty()
    }

    private fun limpar(){
        txtCedula.setText("")
        txtNombre.setText("")
        txtApellido.setText("")
        txtTelefono.setText("")
        txtCorreo.setText("")
    }
    private fun inicioCliente() {
        val intent = Intent(this, listar_proyectos_cliente::class.java)
        startActivity(intent)
        finish()
    }

    private fun enviarCotizacion() {
        if (validarDatos()){
            Toast.makeText(this, "Faltan Datos", Toast.LENGTH_LONG).show()
        }else{
            val progressDialog = Dialog(this)
            progressDialog.setContentView(R.layout.custom_progress_dialog)
            progressDialog.setCancelable(false)
            val progressBar = progressDialog.findViewById<ProgressBar>(R.id.progressBar)
            val messageTextView = progressDialog.findViewById<TextView>(R.id.messageTextView)
            messageTextView.text = "Enviando..." // Puedes cambiar el mensaje aquí

            progressDialog.show()

            val idProyecto = intent.getStringExtra("proyectoId")
            val url = "http://192.168.100.11:8000/Api/CotizarProyecto/$idProyecto"
            val requestQueue = Volley.newRequestQueue(this)
            // Crear un objeto JSON para enviar en la solicitud POST
            val requestBody = JSONObject()
            requestBody.put("txtCedula",txtCedula.text.toString())
            requestBody.put("txtNombre",txtNombre.text.toString())
            requestBody.put("txtApellido",txtApellido.text.toString())
            requestBody.put("txtCelular",txtTelefono.text.toString())
            requestBody.put("txtCorreo",txtCorreo.text.toString())
            // Agrega cualquier dato necesario al cuerpo de la solicitud JSON
            // Ejemplo: requestBody.put("clave", "valor")

            val jsonObjectRequest = JsonObjectRequest(
                Request.Method.POST, url, requestBody,
                Response.Listener { response ->
                    progressDialog.dismiss() // Oculta el ProgressDialog
                    println("!!!!!!!!exito!!!!!!!!!==$response")
                    if (response.getBoolean("estado")) {
                        val customDialog = MaterialDialog(this).show {
                            customView(R.layout.custom_dialog_layout)

                            // Acceder a las vistas dentro del diseño personalizado
                            val customDialogTitle = view.findViewById<TextView>(R.id.customDialogTitle)
                            val customDialogMessage = view.findViewById<TextView>(R.id.customDialogMessage)
                            val customDialogPositiveButton = view.findViewById<Button>(R.id.customDialogPositiveButton)


                            customDialogTitle.text = "Cotizar Proyecto"
                            customDialogMessage.text = response.getString("mensaje")
                            customDialogPositiveButton.text = "Aceptar"


                            customDialogPositiveButton.setOnClickListener {
                                // Acción cuando se hace clic en Aceptar
                                inicioCliente()
                                limpar()
                                dismiss()
                            }
                        }
                        customDialog.show()
                    }else{
                        Toast.makeText(this, "Problemas la hacer la cotizacion", Toast.LENGTH_LONG).show()
                    }
                },
                Response.ErrorListener { error ->
                    progressDialog.dismiss() // Oculta el ProgressDialog
                    println("!!!!Error!!!!! ${error.message}")
                    Toast.makeText(this, "${error.message}", Toast.LENGTH_LONG).show()
                })

            requestQueue.add(jsonObjectRequest)
        }
    }

}