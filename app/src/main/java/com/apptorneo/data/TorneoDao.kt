package com.apptorneo.data

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.apptorneo.model.Torneo
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreSettings
import com.google.firebase.ktx.Firebase

class TorneoDao {


    private val coleccion1 = "TorneosApp"
    private val usuario = Firebase.auth.currentUser?.email.toString()
    private val coleccion2 = "MisTorneos"



    private val firestore: FirebaseFirestore = FirebaseFirestore.getInstance()

    init {

        firestore.firestoreSettings = FirebaseFirestoreSettings.Builder().build()
    }


    fun saveTorneo(torneo: Torneo) {
        val documento: DocumentReference
        if (torneo.id.isEmpty()) {
            documento = firestore
                .collection(coleccion1)
                .document(usuario)
                .collection(coleccion2)
                .document()
            torneo.id = documento.id
        } else {

            documento = firestore
                .collection(coleccion1)
                .document(usuario)
                .collection(coleccion2)
                .document(torneo.id)

        }

        documento.set(torneo)
            .addOnSuccessListener {
                Log.d("saveTorneo", "Torneo agregado/actualizado")
            }
            .addOnCanceledListener {
                Log.e("saveTorneo", "Torneo NO agregado/actualizado")
            }
    }


    fun deleteTorneo(torneo: Torneo) {
        if (torneo.id.isEmpty()) {
            firestore
                .collection(coleccion1)
                .document(usuario)
                .collection(coleccion2)
                .document(torneo.id)
                .delete()
                .addOnSuccessListener {
                    Log.d("daleteTorneo", "Torneo eliminado")
                }
                .addOnCanceledListener {
                    Log.e("daleteTorneo", "Torneo NO eliminado")
                }
        }
    }


    fun getTorneos(): MutableLiveData<List<Torneo>> {
        val listaTorneos = MutableLiveData<List<Torneo>>()
        firestore
            .collection(coleccion1)
            .document(usuario)
            .collection(coleccion2)
            .addSnapshotListener { instantanea, error ->
                if (error != null) {
                    return@addSnapshotListener
                }

                if (instantanea != null) {
                    val lista= ArrayList<Torneo>()

                    instantanea.documents.forEach{
                        val torneo = it.toObject(Torneo::class.java)
                        if(torneo!=null){
                            lista.add(torneo)
                        }
                    }
                    listaTorneos.value= lista
                }
            }
        return listaTorneos
    }
}