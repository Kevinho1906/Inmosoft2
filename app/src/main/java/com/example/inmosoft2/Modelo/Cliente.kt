package com.example.inmosoft2.Modelo

class Cliente constructor(nombre: String, apellido: String, correo: String, telefono: String)  {


    var nombre = nombre
    var apellido = apellido
    var correo = correo
    var telefono = telefono

    //sobrescribir metodo toString
    override fun toString(): String {
        return nombre
    }
}