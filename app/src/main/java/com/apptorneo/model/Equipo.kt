package com.apptorneo.model

import android.os.Parcelable

import kotlinx.parcelize.Parcelize

@Parcelize

data class Equipo(

    var id: String,

    val nombreEquipo:String,

    val rutaImagen:String?,


    ) : Parcelable{
    constructor():
            this(",","","")
}