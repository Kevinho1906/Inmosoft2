package com.example.inmosoft2.ui.Vista_Detalle_Proyecto

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.inmosoft2.R

class vista_detalle_proyecto : Fragment() {

    companion object {
        fun newInstance() = vista_detalle_proyecto()
    }

    private lateinit var viewModel: VistaDetalleProyectoViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_vista_detalle_proyecto, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(VistaDetalleProyectoViewModel::class.java)
        // TODO: Use the ViewModel
    }

}