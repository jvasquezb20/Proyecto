package com.apptorneo.viewmodel

import android.app.Application
import androidx.lifecycle.*
import com.apptorneo.data.TorneoDao
import com.apptorneo.model.Torneo
import com.apptorneo.repository.TorneoRepository
import kotlinx.coroutines.launch

class TorneoViewModel(application: Application) : AndroidViewModel(application) {
    private val repository : TorneoRepository = TorneoRepository(TorneoDao())
    val getTorneos = repository.getTorneos()


    fun saveTorneo(torneo: Torneo){
        viewModelScope.launch { repository.saveTorneo(torneo) }
    }

    fun deleteLugar(torneo: Torneo){
        viewModelScope.launch { repository.deleteTorneo(torneo)}
    }

}