package com.example.inmosoft2.Modelo

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.inmosoft2.R
import com.squareup.picasso.Picasso

class Adaptador : BaseAdapter {

    //Atributos
    var contexto: Context
    var layout:Int = 0
    lateinit var listaProyectos:List<Proyecto>

    /**
     * Constructor que inicializa el objeto
     */
    constructor(context: Context, layout: Int, listaProyectos:List<Proyecto>) {
        this.contexto = context
        this.layout = layout
        this.listaProyectos = listaProyectos
    }
    /**
     * Obtiene el tamaño de la lista del adaptador
     */
    override fun getCount(): Int {
        return listaProyectos.size
    }

    /**
     * Obtiene el item del elemento de aceurdo o posición
     */
    override fun getItem(posicion: Int): Any {
        return listaProyectos[posicion]
    }

    override fun getItemId(posicion: Int): Long {
        return posicion.toLong()
    }


    override fun getView(posicion: Int, convertView: View?, parent: ViewGroup?): View {
        var v: View
        var inflater: LayoutInflater = LayoutInflater.from(contexto)
        v = inflater.inflate(R.layout.activity_listar_proyectos, null)
        val txtNombre: TextView = v.findViewById(R.id.txtNombreDelProyecto)
        txtNombre.text = listaProyectos[posicion].nombre
        val txtUbicacion: TextView = v.findViewById(R.id.txtUbicacion)
        txtUbicacion.text = "Ubicacion: ${listaProyectos[posicion].ubicacion}"
        val txtPrecio: TextView = v.findViewById(R.id.txtPrecio)
        txtPrecio.text = "Desde: $ ${listaProyectos[posicion].precio}"
        val imgFoto: ImageView = v.findViewById(R.id.imgPerfilInicioSesion)
        var url = "https://inmosoft.pythonanywhere.com/media/"+listaProyectos[posicion].urlImagen
        Picasso.get()
            .load(url)
            .placeholder(R.drawable.img_1)
            .error(R.drawable.img_2)
            .into(imgFoto)
        return v;

    }

}

