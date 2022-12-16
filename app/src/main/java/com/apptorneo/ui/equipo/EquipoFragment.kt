package com.apptorneo.ui.equipo

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.apptorneo.R
import com.apptorneo.adapter.EquipoAdapter
import com.apptorneo.adapter.TorneoAdapter
import com.apptorneo.databinding.FragmentEquipoBinding
import com.apptorneo.databinding.FragmentTorneoBinding
import com.apptorneo.viewmodel.EquipoViewModel
import com.apptorneo.viewmodel.TorneoViewModel


class EquipoFragment : Fragment() {
    private var _binding: FragmentEquipoBinding? = null
    private val binding get() = _binding!!
    private lateinit var equipoViewModel: EquipoViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        equipoViewModel = ViewModelProvider(this).get(EquipoViewModel::class.java)

        _binding = FragmentEquipoBinding.inflate(inflater, container, false)

        binding.btAddE.setOnClickListener{
            findNavController().navigate(R.id.action_addTorneoFragment_to_equipoFragment)
        }
        binding.btFinaliazar.setOnClickListener{
            findNavController().navigate(R.id.action_equipoFragment_to_nav_toneo)
        }


        val equipoAdapter= EquipoAdapter()
        val reciclador2 = binding.reciclador2
        reciclador2.adapter = equipoAdapter
        reciclador2.layoutManager = LinearLayoutManager(requireContext())
        equipoViewModel.getEquipos.observe(viewLifecycleOwner){
                equipos -> equipoAdapter.setEquipos(equipos)
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}