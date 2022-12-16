package com.apptorneo.data

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.apptorneo.model.Equipo
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreSettings
import com.google.firebase.ktx.Firebase

class EquipoDao  {


    private val coleccion1 = "TorneosApp"
    private val usuario = Firebase.auth.currentUser?.email.toString()
    private val coleccion2 = "MisEquipos"



    private val firestore: FirebaseFirestore = FirebaseFirestore.getInstance()

    init {

        firestore.firestoreSettings = FirebaseFirestoreSettings.Builder().build()
    }


    fun saveEquipo(equipo: Equipo) {
        val documento: DocumentReference
        if (equipo.id.isEmpty()) {
            documento = firestore
                .collection(coleccion1)
                .document(usuario)
                .collection(coleccion2)
                .document()
            equipo.id = documento.id
        } else {

            documento = firestore
                .collection(coleccion1)
                .document(usuario)
                .collection(coleccion2)
                .document(equipo.id)

        }

        documento.set(equipo)
            .addOnSuccessListener {
                Log.d("saveEquipo", "Equipo agregado/actualizado")
            }
            .addOnCanceledListener {
                Log.e("saveEquipo", "Equipo NO agregado/actualizado")
            }
    }


    fun deleteEquipo(equipo: Equipo) {
        if (equipo.id.isEmpty()) {
            firestore
                .collection(coleccion1)
                .document(usuario)
                .collection(coleccion2)
                .document(equipo.id)
                .delete()
                .addOnSuccessListener {
                    Log.d("daleteEquipo", "Equipo eliminado")
                }
                .addOnCanceledListener {
                    Log.e("daleteEquipo", "Equipo NO eliminado")
                }
        }
    }


   fun getEquipo(): MutableLiveData<List<Equipo>> {
        val listaEquipos = MutableLiveData<List<Equipo>>()
        firestore
            .collection(coleccion1)
            .document(usuario)
            .collection(coleccion2)
            .addSnapshotListener { instantanea, error ->
                if (error != null) {
                    return@addSnapshotListener
                }

                if (instantanea != null) {
                    val lista= ArrayList<Equipo>()

                    instantanea.documents.forEach{
                        val equipo = it.toObject(Equipo::class.java)
                        if(equipo!=null){
                            lista.add(equipo)
                        }
                    }
                    listaEquipos.value= lista
                }
            }
        return listaEquipos
    }
}
