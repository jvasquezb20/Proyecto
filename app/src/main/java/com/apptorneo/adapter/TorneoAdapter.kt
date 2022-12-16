package com.apptorneo.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.apptorneo.databinding.TorneoFilaBinding
import com.apptorneo.model.Torneo
import com.apptorneo.ui.torneo.TorneoFragmentDirections

class TorneoAdapter : RecyclerView.Adapter<TorneoAdapter.TorneoViewHolder>() {


    private var listaTorneos = emptyList<Torneo>()

    //contenedor de vistas "Cajjitas" en memoria
    inner class TorneoViewHolder(private val itemBinding: TorneoFilaBinding)
        : RecyclerView.ViewHolder(itemBinding.root){
        fun dibuja(torneo: Torneo){
            itemBinding.tvNombreT.text = torneo.nombre
            itemBinding.tvUbicacion.text = torneo.ubicacion
            itemBinding.tvNumero.text = torneo.telefono
            itemBinding.tvCantEqui.text = torneo.cantiEquipos

            itemBinding.vistaTorneoFila.setOnClickListener{
                val action = TorneoFragmentDirections
                    .actionNavToneoToUpdateTorneoFragment(torneo)
                itemView.findNavController().navigate(action)
            }


        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TorneoViewHolder {
        val itemBinding = TorneoFilaBinding.
        inflate(
            LayoutInflater.from(parent.context)
            ,parent
            ,false)

        return TorneoViewHolder(itemBinding)
    }


    override fun onBindViewHolder(holder: TorneoViewHolder, position: Int) {
        val torneoActual = listaTorneos[position]
        holder.dibuja(torneoActual)
    }

    override fun getItemCount(): Int {
        return listaTorneos.size
    }


    fun setTorneos(torneos: List<Torneo>){
        listaTorneos = torneos
        notifyDataSetChanged()
    }
}