package com.apptorneo.ui.equipo

import android.app.Activity
import android.content.Intent
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
import com.apptorneo.R
import com.apptorneo.databinding.FragmentAddEquipoBinding
import com.apptorneo.databinding.FragmentAddTorneoBinding
import com.apptorneo.model.Equipo
import com.apptorneo.model.Torneo
import com.apptorneo.utiles.ImagenUtiles
import com.apptorneo.viewmodel.EquipoViewModel
import com.apptorneo.viewmodel.TorneoViewModel
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.ktx.storage

class AddEquipoFragment : Fragment() {
    private var _binding: FragmentAddEquipoBinding? = null
    private val binding get() = _binding!!
    private lateinit var equipoViewModel: EquipoViewModel
    private lateinit var tomarFotoActivity: ActivityResultLauncher<Intent>
    private lateinit var imagenUtiles: ImagenUtiles

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        equipoViewModel = ViewModelProvider(this).get(EquipoViewModel::class.java)

        _binding = FragmentAddEquipoBinding.inflate(inflater, container, false)
        binding.btAddEquipo.setOnClickListener{
            binding.msgMensaje.visibility = TextView.VISIBLE

        }
        binding.btAddEquipo.setOnClickListener{ addEquipo("") }

        tomarFotoActivity = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()){
                result ->
            if(result.resultCode== Activity.RESULT_OK){
                imagenUtiles.actualizaFoto()
            }

        }

        imagenUtiles = ImagenUtiles(
            requireContext(),
            binding.btPhoto,
            binding.btRotaL,
            binding.btRotaR,
            binding.imagenE,
            tomarFotoActivity)

        return binding.root


    }

    private fun addEquipo(rutaImagen:String) {
        val nombre = binding.etNombreE.text.toString()

        if (nombre.isNotEmpty()){ //al menos tenemos un nombre
            val equipo= Equipo("",nombre,rutaImagen)

            equipoViewModel.saveEquipo(equipo)
            Toast.makeText(requireContext(),getText(R.string.msg_equipo_added),
                Toast.LENGTH_SHORT).show()
            findNavController().navigate(R.id.action_addTorneoFragment_to_equipoFragment)
        }else {
            Toast.makeText(requireContext(),getText(R.string.msg_datos),
                Toast.LENGTH_LONG).show()

        }
    }

    private fun subeImagen() {
        binding.msgMensaje.text = getString(R.string.msg_subiendo_imagen)
        val imagenFile = imagenUtiles.imagenFile
        if (imagenFile.exists() && imagenFile.isFile && imagenFile.canRead()){
            val ruta = Uri.fromFile(imagenFile) //la ruta del archivo local...
            val rutaNube = "lugaresApp/${Firebase.auth.currentUser?.email}/imagenes/${imagenFile.name}"

            val referencia: StorageReference = Firebase.storage.reference.child(rutaNube)

            referencia.putFile(ruta).addOnSuccessListener{
                referencia.downloadUrl.addOnSuccessListener {
                    val rutaPublicaImagen = it.toString()
                    addEquipo("")
                }
            }
                .addOnFailureListener{
                    addEquipo("")
                }
        }else{
            addEquipo("")
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}