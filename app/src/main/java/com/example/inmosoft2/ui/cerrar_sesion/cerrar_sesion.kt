package com.example.inmosoft2.ui.cerrar_sesion

import android.content.Intent
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.inmosoft2.R
import com.example.inmosoft2.inicio_sesion
import com.example.inmosoft2.vista_detalle_proyecto

class cerrar_sesion : Fragment() {

    companion object {
        fun newInstance() = cerrar_sesion()
    }

    private lateinit var viewModel: CerrarSesioniViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_home, container, false)

        // Coloca el intent para iniciar la actividad aquí
        val intent = Intent(requireContext(), inicio_sesion::class.java)
        startActivity(intent)
        Toast.makeText(requireContext(), "Sesión Cerrada", Toast.LENGTH_LONG).show()
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(CerrarSesioniViewModel::class.java)
        // TODO: Use the ViewModel
    }
}
