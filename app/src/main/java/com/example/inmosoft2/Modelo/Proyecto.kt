package com.example.inmosoft2.Modelo

class Proyecto constructor(urlImagen: String, nombre: String, ubicacion: String, precio: String,idProyecto:String) {

    var urlImagen = urlImagen
    var nombre = nombre
    var ubicacion = ubicacion
    var precio = precio
    var idProyecto = idProyecto

    //sobrescribir metodo toString
    override fun toString(): String {
        return  nombre
    }

}