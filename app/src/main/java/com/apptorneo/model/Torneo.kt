package com.apptorneo.model

import android.os.Parcelable

import kotlinx.parcelize.Parcelize

@Parcelize

data class Torneo(

    var id: String,

    val nombre:String,

    val ubicacion:String?,

    val telefono:String?,

    val cantiEquipos:String,


) : Parcelable{
    constructor():
            this(",","","",
                "","")
}