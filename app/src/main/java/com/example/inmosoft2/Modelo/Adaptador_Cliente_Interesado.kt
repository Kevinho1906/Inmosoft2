package com.example.inmosoft2.Modelo

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.example.inmosoft2.R

class Adaptador_Cliente_Interesado : BaseAdapter {

    //Atributos
    var contexto: Context
    var layout:Int = 0
    lateinit var listaClientesInteresados:List<Cliente>

    /**
     * Constructor que inicializa el objeto
     */
    constructor(context: Context, layout: Int, listaClientesInteresados:List<Cliente>) {
        this.contexto = context
        this.layout = layout
        this.listaClientesInteresados = listaClientesInteresados
    }
    /**
     * Obtiene el tamaño de la lista del adaptador
     */
    override fun getCount(): Int {
        return listaClientesInteresados.size
    }

    /**
     * Obtiene el item del elemento de aceurdo o posición
     */
    override fun getItem(posicion: Int): Any {
        return listaClientesInteresados[posicion]
    }

    override fun getItemId(posicion: Int): Long {
        return posicion.toLong()
    }


    override fun getView(posicion: Int, convertView: View?, parent: ViewGroup?): View {
        var v: View
        var inflater: LayoutInflater = LayoutInflater.from(contexto)
        v = inflater.inflate(R.layout.lista_clientes_interesados, null)
        val txtNombreDelCliente: TextView = v.findViewById(R.id.txtNombreDelCliente)
        txtNombreDelCliente.text = "Nombre: " + listaClientesInteresados[posicion].nombre
        val txtApellidoDelCliente: TextView = v.findViewById(R.id.txtApellidoDelCliente)
        txtApellidoDelCliente.text = "Apellido: " + listaClientesInteresados[posicion].apellido
        val txtCorreoDelCliente: TextView = v.findViewById(R.id.txtCorreoDelCLiente)
        txtCorreoDelCliente.text = "Correo: " + listaClientesInteresados[posicion].correo
        val txtTelefonoDelCliente: TextView = v.findViewById(R.id.txtTelefonoDelCLiente)
        txtTelefonoDelCliente.text = "Telefono: " + listaClientesInteresados[posicion].telefono

        return v;

    }
}