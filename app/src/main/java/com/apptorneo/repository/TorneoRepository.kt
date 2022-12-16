package com.apptorneo.repository

import androidx.lifecycle.MutableLiveData
import com.apptorneo.data.TorneoDao
import com.apptorneo.model.Torneo

class TorneoRepository(private val torneoDao: TorneoDao) {
    fun saveTorneo(torneo: Torneo){
        torneoDao.saveTorneo(torneo)
    }

    fun deleteTorneo(torneo: Torneo){
        torneoDao.deleteTorneo(torneo)
    }

    fun getTorneos() : MutableLiveData<List<Torneo>> = torneoDao.getTorneos()

}