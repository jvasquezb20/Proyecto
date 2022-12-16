package com.apptorneo.ui.torneo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.apptorneo.R
import com.apptorneo.adapter.TorneoAdapter
import com.apptorneo.databinding.FragmentTorneoBinding
import com.apptorneo.viewmodel.TorneoViewModel

class TorneoFragment : Fragment() {

    private var _binding: FragmentTorneoBinding? = null
    private val binding get() = _binding!!
    private lateinit var torneoViewModel: TorneoViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        torneoViewModel =ViewModelProvider(this).get(TorneoViewModel::class.java)

        _binding = FragmentTorneoBinding.inflate(inflater, container, false)

        binding.btAdd.setOnClickListener{
            findNavController().navigate(R.id.action_nav_toneo_to_addTorneoFragment)
        }



        val torneoAdapter= TorneoAdapter()
        val reciclador = binding.reciclador
        reciclador.adapter = torneoAdapter
        reciclador.layoutManager = LinearLayoutManager(requireContext())
        torneoViewModel.getTorneos.observe(viewLifecycleOwner){
                torneos -> torneoAdapter.setTorneos(torneos)
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}