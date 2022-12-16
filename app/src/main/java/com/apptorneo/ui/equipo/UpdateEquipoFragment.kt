package com.apptorneo.ui.equipo

import android.Manifest
import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.apptorneo.R
import com.apptorneo.databinding.FragmentAddEquipoBinding
import com.apptorneo.databinding.FragmentAddTorneoBinding
import com.apptorneo.databinding.FragmentUpdateEquipoBinding
import com.apptorneo.databinding.FragmentUpdateTorneoBinding
import com.apptorneo.model.Equipo
import com.apptorneo.model.Torneo
import com.apptorneo.ui.torneo.UpdateTorneoFragmentArgs
import com.apptorneo.utiles.ImagenUtiles
import com.apptorneo.viewmodel.EquipoViewModel
import com.apptorneo.viewmodel.TorneoViewModel
import com.bumptech.glide.Glide
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.ktx.storage

class UpdateEquipoFragment : Fragment() {
    private var _binding: FragmentUpdateEquipoBinding? = null
    private val binding get() = _binding!!
    private lateinit var equipoViewModel: EquipoViewModel

    private val args by navArgs<UpdateEquipoFragmentArgs>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        equipoViewModel = ViewModelProvider(this).get(EquipoViewModel::class.java)

        _binding = FragmentUpdateEquipoBinding.inflate(inflater, container, false)

        //se pasan los valores a los campos de pantalla
        binding.etNombreE.setText(args.equipo.nombreEquipo)


        binding.btUpdateEquipo.setOnClickListener{ updateEquipo() }
        binding.btDeleteEquipo.setOnClickListener{ deleteEquipo() }


        if(args.equipo.rutaImagen?.isNotEmpty()==true){
            Glide.with(requireContext()).load(args.equipo.rutaImagen)
                .fitCenter()
                .into(binding.imagenE)
        }

        return binding.root
    }



    private fun updateEquipo() {
        val nombre = binding.etNombreE.text.toString()

        if (nombre.isNotEmpty()){
            val equipo= Equipo(
                args.equipo.id,
                nombre, args.equipo.rutaImagen)

            equipoViewModel.saveEquipo(equipo)
            Toast.makeText(requireContext(),getText(R.string.msg_equipo_update),
                Toast.LENGTH_SHORT).show()
            findNavController().navigate(R.id.action_updateEquipoFragment_to_equipoFragment)
        }else {
            Toast.makeText(requireContext(),getText(R.string.msg_datos),
                Toast.LENGTH_LONG).show()

        }
    }
    private fun deleteEquipo() {
        val alerta = AlertDialog.Builder(requireContext())
        alerta.setTitle(R.string.bt_delete_equipo)
        alerta.setMessage(getString(R.string.msg_pregunta_eliminar_equi)+"${args.equipo.nombreEquipo}?")
        alerta.setPositiveButton(getString(R.string.msg_si)){_,_ ->
            equipoViewModel.deleteEquipo(args.equipo)
            Toast.makeText(requireContext(),getString(R.string.msg_equipo_deleted),Toast.LENGTH_LONG).show()
            findNavController().navigate(R.id.action_updateTorneoFragment_to_nav_toneo)
        }
        alerta.setNegativeButton(getString(R.string.msg_no)){_,_ ->}
        alerta.create().show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}