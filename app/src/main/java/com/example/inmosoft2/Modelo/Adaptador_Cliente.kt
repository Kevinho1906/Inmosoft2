package com.example.inmosoft2.Modelo

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.example.inmosoft2.R
import com.example.inmosoft2.vista_clientes_interesados
import com.example.inmosoft2.vista_detalle_proyecto
import com.squareup.picasso.Picasso

class Adaptador_Cliente : BaseAdapter {

    //Atributos
    var contexto: Context
    var layout:Int = 0
    lateinit var listaProyectosCliente:List<Proyecto>

    /**
     * Constructor que inicializa el objeto
     */
    constructor(context: Context, layout: Int, listaProyectosCliente:List<Proyecto>) {
        this.contexto = context
        this.layout = layout
        this.listaProyectosCliente = listaProyectosCliente
    }
    /**
     * Obtiene el tamaño de la lista del adaptador
     */
    override fun getCount(): Int {
        return listaProyectosCliente.size
    }

    /**
     * Obtiene el item del elemento de aceurdo o posición
     */
    override fun getItem(posicion: Int): Any {
        return listaProyectosCliente[posicion]
    }

    override fun getItemId(posicion: Int): Long {
        return posicion.toLong()
    }


    override fun getView(posicion: Int, convertView: View?, parent: ViewGroup?): View {
        var v: View
        var inflater: LayoutInflater = LayoutInflater.from(contexto)
        v = inflater.inflate(R.layout.listar_proyectos_cliente, null)
        val txtNombreCliente: TextView = v.findViewById(R.id.txtNombreDelProyectoCliente)
        txtNombreCliente.text = listaProyectosCliente[posicion].nombre
        val txtUbicacionCliente: TextView = v.findViewById(R.id.txtUbicacionCliente)
        txtUbicacionCliente.text = "Ubicacion: ${listaProyectosCliente[posicion].ubicacion}"
        val txtPrecioCliente: TextView = v.findViewById(R.id.txtPrecioCliente)
        txtPrecioCliente.text = "Desde: $ ${listaProyectosCliente[posicion].precio}"
        val imgFoto: ImageView = v.findViewById(R.id.imgPerfilInicioSesionCliente)
        var url = "https://inmosoft.pythonanywhere.com/media/"+listaProyectosCliente[posicion].urlImagen
        Picasso.get()
            .load(url)
            .placeholder(R.drawable.img_1)
            .error(R.drawable.img_2)
            .into(imgFoto)
        v.findViewById<Button>(R.id.btnVerMasCliente).setOnClickListener {
            val intent = Intent(contexto, vista_detalle_proyecto::class.java)
            intent.putExtra("proyectoId", listaProyectosCliente[posicion].idProyecto)
            contexto.startActivity(intent)
        }
        return v;

    }
}