package com.example.inmosoft2.Modelo

class User constructor(id: Int, imagenPerfil: String, userName: String, cedula: String, nombre: String, apellido: String, correo: String, telefono: String)  {

    var id = id
    var imagenPerfil = imagenPerfil
    var userName = userName
    var cedula = cedula
    var nombre = nombre
    var apellido = apellido
    var correo = correo
    var telefono = telefono

    //sobrescribir metodo toString
    override fun toString(): String {
        return nombre
    }
}