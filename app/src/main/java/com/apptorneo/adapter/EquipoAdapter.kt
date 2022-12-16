package com.apptorneo.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.apptorneo.databinding.EquipoFilaBinding
import com.apptorneo.databinding.TorneoFilaBinding
import com.apptorneo.model.Equipo
import com.apptorneo.model.Torneo
import com.apptorneo.ui.torneo.TorneoFragmentDirections
import com.bumptech.glide.Glide

class EquipoAdapter : RecyclerView.Adapter<EquipoAdapter.EquipoViewHolder>() {


    private var listaEquipos = emptyList<Equipo>()

    //contenedor de vistas "Cajjitas" en memoria
    inner class EquipoViewHolder(private val itemBinding: EquipoFilaBinding)
        : RecyclerView.ViewHolder(itemBinding.root){
        fun dibuja(equipo: Equipo){
            itemBinding.tvNombreE.text = equipo.nombreEquipo
            Glide.with(itemBinding.root.context).load(equipo.rutaImagen)
                .circleCrop().into(itemBinding.imagen)

            /*itemBinding.vistaEquipoFila.setOnClickListener{
                val action = EquipoFragmentDirections
                    .actionNavToneoToUpdateTorneoFragment(equipo)
                itemView.findNavController().navigate(action)
            }*/


        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EquipoViewHolder {
        val itemBinding = EquipoFilaBinding.
        inflate(
            LayoutInflater.from(parent.context)
            ,parent
            ,false)

        return EquipoViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: EquipoViewHolder, position: Int) {
        val equipoActual = listaEquipos[position]
        holder.dibuja(equipoActual)
    }

    override fun getItemCount(): Int {
        return listaEquipos.size
    }

     fun setEquipos(equipo: List<Equipo>){
         listaEquipos = equipo
         notifyDataSetChanged()
     }
}