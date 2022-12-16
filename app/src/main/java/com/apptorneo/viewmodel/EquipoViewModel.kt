package com.apptorneo.viewmodel

import android.app.Application
import androidx.lifecycle.*
import com.apptorneo.data.EquipoDao
import com.apptorneo.data.TorneoDao
import com.apptorneo.model.Equipo
import com.apptorneo.model.Torneo
import com.apptorneo.repository.EquipoRepository
import com.apptorneo.repository.TorneoRepository
import kotlinx.coroutines.launch

class EquipoViewModel(application: Application) : AndroidViewModel(application) {
    private val repository : EquipoRepository = EquipoRepository(EquipoDao())
    val getEquipos = repository.getEquipo()


    fun saveEquipo(equipo: Equipo){
        viewModelScope.launch { repository.saveEquipo(equipo) }
    }

    fun deleteEquipo(equipo: Equipo){
        viewModelScope.launch { repository.deleteEquipo(equipo)}
    }

}
