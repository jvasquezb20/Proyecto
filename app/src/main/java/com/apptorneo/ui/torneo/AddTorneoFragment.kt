package com.apptorneo.ui.torneo


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.apptorneo.R
import com.apptorneo.databinding.FragmentAddTorneoBinding
import com.apptorneo.model.Torneo
import com.apptorneo.viewmodel.TorneoViewModel


class AddTorneoFragment : Fragment() {
    private var _binding: FragmentAddTorneoBinding? = null
    private val binding get() = _binding!!
    private lateinit var torneoViewModel: TorneoViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        torneoViewModel = ViewModelProvider(this).get(TorneoViewModel::class.java)

        _binding = FragmentAddTorneoBinding.inflate(inflater, container, false)

        binding.btAddTorneo.setOnClickListener{ addTorneo() }
        return binding.root
    }

    private fun addTorneo() {
        val nombre = binding.etNombreT.text.toString()
        val Ubicacion = binding.etUbicacion.text.toString()
        val telefono = binding.etTelefono.text.toString()
        val cantiEqui = binding.etCantiEqui.text.toString()


        if (nombre.isNotEmpty()){
            val torneo= Torneo("",nombre,Ubicacion,telefono,cantiEqui)

            torneoViewModel.saveTorneo(torneo)
            Toast.makeText(requireContext(),getText(R.string.msg_torneo_added),
                Toast.LENGTH_SHORT).show()
            findNavController().navigate(R.id.action_addTorneoFragment_to_equipoFragment)
        }else {
            Toast.makeText(requireContext(),getText(R.string.msg_datos),
                Toast.LENGTH_LONG).show()

        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}