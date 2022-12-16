package com.apptorneo.repository

import androidx.lifecycle.MutableLiveData
import com.apptorneo.data.EquipoDao
import com.apptorneo.model.Equipo


class EquipoRepository(private val equipoDao: EquipoDao) {
    fun saveEquipo(equipo: Equipo) {
        equipoDao.saveEquipo(equipo)
    }

    fun deleteEquipo(equipo: Equipo) {
        equipoDao.deleteEquipo(equipo)
    }

    fun getEquipo(): MutableLiveData<List<Equipo>> = equipoDao.getEquipo()

}