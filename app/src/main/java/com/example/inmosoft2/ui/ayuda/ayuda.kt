package com.example.inmosoft2.ui.ayuda

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.inmosoft2.R

class ayuda : Fragment() {

    companion object {
        fun newInstance() = ayuda()
    }

    private lateinit var viewModel: AyudaViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_ayuda, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(AyudaViewModel::class.java)
        // TODO: Use the ViewModel
    }

}