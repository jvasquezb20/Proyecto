package com.apptorneo.ui.torneo


import android.app.AlertDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.apptorneo.R
import com.apptorneo.databinding.FragmentUpdateTorneoBinding
import com.apptorneo.model.Torneo
import com.apptorneo.viewmodel.TorneoViewModel
import android.Manifest


class UpdateTorneoFragment : Fragment() {
    private var _binding: FragmentUpdateTorneoBinding? = null
    private val binding get() = _binding!!
    private lateinit var torneoViewModel: TorneoViewModel

    private val args by navArgs<UpdateTorneoFragmentArgs>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        torneoViewModel = ViewModelProvider(this).get(TorneoViewModel::class.java)

        _binding = FragmentUpdateTorneoBinding.inflate(inflater, container, false)

        //se pasan los valores a los campos de pantalla
        binding.etNombreT.setText(args.torneo.nombre)
        binding.etUbicacion.setText(args.torneo.ubicacion)
        binding.etTelefono.setText(args.torneo.telefono)
        binding.etCantiEqui.setText(args.torneo.cantiEquipos)

        binding.btUpdateTorneo.setOnClickListener{ updateTorneo() }
        binding.btDeleteTorneo.setOnClickListener{ deleteTorneo() }
        binding.btPhone.setOnClickListener{llamarUbicacion()}
        binding.btWhatsapp.setOnClickListener{enviarWhastApp()}



        return binding.root
    }

    private fun enviarWhastApp() {
        val valor = binding.etTelefono.text.toString()
        if (valor.isNotEmpty()){
            val intent = Intent(Intent.ACTION_VIEW)
            val uri = "whatsapp://send?phone=506$valor&text="+getString(R.string.msg_saludos)
            intent.setPackage("com.whatsapp")
            intent.data = Uri.parse(uri)

            startActivity(intent)

        }else{
            Toast.makeText(requireContext(),
                getString(R.string.msg_datos),Toast.LENGTH_LONG)
        }
    }

    private fun llamarUbicacion() {
        val valor = binding.etTelefono.text.toString()
        if (valor.isNotEmpty()){
            val intent = Intent(Intent.ACTION_CALL)
            intent.data = Uri.parse( "tel:$valor")
            if (requireActivity().
                checkSelfPermission(Manifest.permission.CALL_PHONE)
                != PackageManager.PERMISSION_GRANTED){
                requireActivity().requestPermissions(arrayOf(Manifest.permission.CALL_PHONE),105)
            }
        }else{
            Toast.makeText(requireContext(),
                getString(R.string.msg_datos),Toast.LENGTH_LONG)
        }
    }

    private fun updateTorneo() {
        val nombre = binding.etNombreT.text.toString()
        val Ubicacion = binding.etUbicacion.text.toString()
        val telefono = binding.etTelefono.text.toString()
        val cantiEqui = binding.etCantiEqui.text.toString()


        if (nombre.isNotEmpty()){
            val torneo= Torneo(
                args.torneo.id,
                nombre,
                Ubicacion,
                telefono,
                cantiEqui)

            torneoViewModel.saveTorneo(torneo)
            Toast.makeText(requireContext(),getText(R.string.msg_torneo_update),
                Toast.LENGTH_SHORT).show()
            findNavController().navigate(R.id.action_updateTorneoFragment_to_nav_toneo)
        }else {
            Toast.makeText(requireContext(),getText(R.string.msg_datos),
                Toast.LENGTH_LONG).show()

        }
    }
    private fun deleteTorneo() {
        val alerta = AlertDialog.Builder(requireContext())
        alerta.setTitle(R.string.bt_delete_Torneo)
        alerta.setMessage(getString(R.string.msg_pregunta_eliminar)+"${args.torneo.nombre}?")
        alerta.setPositiveButton(getString(R.string.msg_si)){_,_ ->
            torneoViewModel.deleteLugar(args.torneo)
            Toast.makeText(requireContext(),getString(R.string.msg_Torneo_deleted),Toast.LENGTH_LONG).show()
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