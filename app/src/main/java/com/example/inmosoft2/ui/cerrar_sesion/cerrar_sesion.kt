package com.example.inmosoft2.ui.cerrar_sesion

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.inmosoft2.R

class cerrar_sesion : Fragment() {

    companion object {
        fun newInstance() = cerrar_sesion()
    }

    private lateinit var viewModel: CerrarSesioniViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_cerrar_sesion, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(CerrarSesioniViewModel::class.java)
        // TODO: Use the ViewModel
    }

}